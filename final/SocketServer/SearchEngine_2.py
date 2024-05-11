import pandas as pd
import numpy as np
import sys
from sklearn.feature_extraction.text import TfidfVectorizer
from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity



class SearchEngine:
    def __init__(self, data_path):
        self.data = pd.read_csv(data_path)
        self.model = SentenceTransformer('paraphrase-MiniLM-L6-v2')
        self.tfidf_vectorizer = TfidfVectorizer()
        self.tfidf_matrix = self._calculate_tfidf_matrix()

    def _calculate_tfidf_matrix(self):
        tfidf_matrix = self.tfidf_vectorizer.fit_transform(self.data['content'].values)
        return tfidf_matrix

    def search(self, query, top_k=5):
        query_embedding = self.model.encode([query])[0]
        query_tfidf = self.tfidf_vectorizer.transform([query])
        tfidf_similarity = cosine_similarity(query_tfidf, self.tfidf_matrix)[0]
        semantic_similarity = cosine_similarity([query_embedding], self.model.encode(self.data['content'].values))[0]
        combined_similarity = 0.5 * tfidf_similarity + 0.5 * semantic_similarity
        indices = np.argsort(combined_similarity)[-top_k:][::-1]
        results = [self.data.iloc[idx]['id'] for idx in indices]
        return results

# Load data from CSV file
data_path = 'ngu.csv'

# Create search engine instance
search_engine = SearchEngine(data_path)

# Perform search
# query = " ".join(sys.argv[1:])

x = 0

# query = sys.stdin.readline().strip()

# results = search_engine.search(query)
# for idx, text, similarity in results:
#     print(f"ID: {idx}, Similarity: {similarity:.4f}, Text: {text}")

# print(results)

# print(query)

# sys.stdout.flush()

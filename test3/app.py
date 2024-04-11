import pandas as pd
import nltk
import string
from collections import defaultdict
from nltk.corpus import stopwords
from nltk.stem import PorterStemmer
from sklearn.feature_extraction.text import TfidfVectorizer

# Tải tập stopwords và punkt từ NLTK
import ssl
ssl._create_default_https_context = ssl._create_unverified_context
nltk.download('stopwords')
nltk.download('punkt')

# class SearchEngine:
#     def __init__(self):
#         self.inverted_index = defaultdict(set)
#         self.documents = []
#         self.stop_words = set(stopwords.words('english'))
#         self.stemmer = PorterStemmer()
#         self.tfidf_vectorizer = TfidfVectorizer()

#     def preprocess_text(self, text):
#         text = text.translate(str.maketrans('', '', string.punctuation)).lower()
#         tokens = nltk.word_tokenize(text)
#         tokens = [self.stemmer.stem(token) for token in tokens if token not in self.stop_words]
#         return ' '.join(tokens)

#     def index_document(self, document_id, text):
#         tokens = self.preprocess_text(text)
#         self.documents.append((document_id, text))
#         for token in tokens.split():
#             self.inverted_index[token].add(document_id)

#     def search(self, query):
#         query = self.preprocess_text(query)
#         relevant_docs = set()
#         for token in query.split():
#             relevant_docs.update(self.inverted_index.get(token, set()))
#         relevant_docs = list(relevant_docs)

#         # TF-IDF
#         corpus = [doc[1] for doc in self.documents]
#         X = self.tfidf_vectorizer.fit_transform(corpus)
#         query_vector = self.tfidf_vectorizer.transform([query])
#         cosine_similarities = X.dot(query_vector.T).toarray().flatten()
        
#         # Sort và trả về kết quả
#         sorted_results = sorted(zip(relevant_docs, cosine_similarities), key=lambda x: x[1], reverse=True)
#         return sorted_results

# # Load dữ liệu từ file CSV
# df = pd.read_csv('test3/data.csv')

# # Tạo một search engine và index dữ liệu
# search_engine = SearchEngine()
# for idx, row in df.iterrows():
#     search_engine.index_document(idx, row['text'])

# # Kết hợp với HTML
# from flask import Flask, request, render_template
# app = Flask(__name__)

# @app.route('/')
# def home():
#     return render_template('search.html')

# @app.route('/search', methods=['POST'])
# def search():
#     query = request.form['query']
#     results = search_engine.search(query)
#     return render_template('results.html', results=results, query=query)

# if __name__ == '__main__':
#     app.run(debug=True)


# import pandas as pd
# import numpy as np
# import nltk
# import string
from sklearn.metrics.pairwise import cosine_similarity
from sentence_transformers import SentenceTransformer

# # Tải tập stopwords và punkt từ NLTK
# nltk.download('stopwords')
# nltk.download('punkt')

class SearchEngine:
    def __init__(self):
        self.inverted_index = defaultdict(set)
        self.documents = []
        self.stop_words = set(stopwords.words('english'))
        self.model = SentenceTransformer('bert-base-nli-mean-tokens')

    def preprocess_text(self, text):
        text = text.translate(str.maketrans('', '', string.punctuation)).lower()
        tokens = nltk.word_tokenize(text)
        tokens = [token for token in tokens if token not in self.stop_words]
        return ' '.join(tokens)

    def index_document(self, document_id, text):
        tokens = self.preprocess_text(text)
        self.documents.append((document_id, text))
        for token in tokens.split():
            self.inverted_index[token].add(document_id)

    def search(self, query):
        query = self.preprocess_text(query)
        query_embedding = self.model.encode([query])
        
        relevant_docs = set()
        for token in query.split():
            relevant_docs.update(self.inverted_index.get(token, set()))
        relevant_docs = list(relevant_docs)

        # Tính cosine similarity giữa truy vấn và văn bản
        corpus_embeddings = self.model.encode([doc[1] for doc in self.documents])
        cosine_similarities = cosine_similarity(query_embedding, corpus_embeddings)[0]

        # Sắp xếp và trả về kết quả
        sorted_results = sorted(zip(relevant_docs, cosine_similarities), key=lambda x: x[1], reverse=True)

        results_with_info = []
        for idx, score in sorted_results:
            results_with_info.append(self.documents[idx])
        print(results_with_info)

        return results_with_info
        # return sorted_results

# Load dữ liệu từ file CSV
df = pd.read_csv('test3/data.csv')

# Tạo một search engine và index dữ liệu
search_engine = SearchEngine()
for idx, row in df.iterrows():
    search_engine.index_document(idx, row['text'])

# Kết hợp với HTML
from flask import Flask, request, render_template
app = Flask(__name__)

@app.route('/')
def home():
    return render_template('search.html')

@app.route('/search', methods=['POST'])
def search():
    query = request.form['query']
    results = search_engine.search(query)
    return render_template('results.html', results=results, query=query)

if __name__ == '__main__':
    app.run(debug=True)

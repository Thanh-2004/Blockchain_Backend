import pandas as pd
import nltk
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
from sentence_transformers import SentenceTransformer
from sklearn.metrics.pairwise import cosine_similarity

# # Tải dữ liệu từ NLTK (chỉ cần thực hiện lần đầu tiên)
# nltk.download('stopwords')
# nltk.download('punkt')

# Load mô hình NLP
model = SentenceTransformer('paraphrase-MiniLM-L6-v2')

# Tạo class SearchEngine
class SearchEngine:
    def __init__(self, data_path):
        self.data = pd.read_csv(data_path)
        self.corpus_embeddings = self._encode_corpus()

    def _encode_corpus(self):
        corpus = self.data['text'].tolist()
        return model.encode(corpus)

    def search(self, query, top_k=5):
        query_embedding = model.encode([query])[0]
        similarities = cosine_similarity([query_embedding], self.corpus_embeddings)[0]
        indices = similarities.argsort()[-top_k:][::-1]
        results = [(self.data.iloc[idx]['id'], self.data.iloc[idx]['text'], similarities[idx]) for idx in indices]
        return results

# Tạo bộ dữ liệu và lưu vào file CSV
# data = {
#     'id': [1, 2, 3, 4, 5,6,7],
#     'text': [
#         "This is an example document.",
#         "Another document for testing.",
#         "A document with relevant information.",
#         "Information about search engines.",
#         "A document containing useful data.",
#         "I want to buy a new pair of sneakers.",
#         "My Puma sneakers is about to broken."
#     ]
# }
# df = pd.DataFrame(data)
# df.to_csv('data.csv', index=False)
    
# df = pd.read_csv('data.csv')

# Tạo search engine
search_engine = SearchEngine('data.csv')

# Thực hiện tìm kiếm
query = "shoes"
results = search_engine.search(query)
for idx, text, similarity in results:
    print(f"ID: {idx}, Similarity: {similarity:.4f}, Text: {text}")

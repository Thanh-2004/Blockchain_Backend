import nltk
import string
from collections import defaultdict
from nltk.corpus import stopwords
from nltk.stem import PorterStemmer

# Download stopwords và punkt từ nltk

import ssl
ssl._create_default_https_context = ssl._create_unverified_context
nltk.download('stopwords')
nltk.download('punkt')

class SearchEngine:
    def __init__(self):
        self.inverted_index = defaultdict(set)
        self.documents = []
        self.stop_words = set(stopwords.words('english'))
        self.stemmer = PorterStemmer()

    def preprocess_text(self, text):
        # Loại bỏ dấu câu và chuyển đổi thành chữ thường
        text = text.translate(str.maketrans('', '', string.punctuation)).lower()
        # Tách từ
        tokens = nltk.word_tokenize(text)
        # Loại bỏ stop words và stemming
        tokens = [self.stemmer.stem(token) for token in tokens if token not in self.stop_words]
        return tokens

    def index_document(self, document_id, text):
        tokens = self.preprocess_text(text)
        self.documents.append((document_id, text))
        for token in tokens:
            self.inverted_index[token].add(document_id)

    def search(self, query):
        tokens = self.preprocess_text(query)
        relevant_docs = set()
        for token in tokens:
            relevant_docs.update(self.inverted_index.get(token, set()))
        relevant_docs = list(relevant_docs)

        # TF-IDF
        tfidf_scores = defaultdict(float)
        total_docs = len(self.documents)
        for doc_id, doc_text in self.documents:
            tokens = self.preprocess_text(doc_text)
            for token in tokens:
                if doc_id in relevant_docs:
                    tfidf_scores[doc_id] += tokens.count(token) * (1 + 
                        (total_docs / (1 + len(self.inverted_index[token]))))
        
        # Sort và trả về kết quả
        sorted_results = sorted(tfidf_scores.items(), key=lambda x: x[1], reverse=True)
        return sorted_results


# Sử dụng search engine
search_engine = SearchEngine()

# Index các tài liệu
search_engine.index_document(1, "This is a sample document about natural language processing.")
search_engine.index_document(2, "Another document discussing the importance of NLP.")
search_engine.index_document(3, "The third document is about text processing techniques.")
search_engine.index_document(4, "hello, this is not about text processing")

# Tìm kiếm
query = "text processing"
results = search_engine.search(query)
for doc_id, score in results:
    print(f"Document ID: {doc_id}, Score: {score}")

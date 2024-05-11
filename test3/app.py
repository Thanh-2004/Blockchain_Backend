import pandas as pd
import nltk
import string
from collections import defaultdict
from nltk.corpus import stopwords
from nltk.stem import PorterStemmer
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from sentence_transformers import SentenceTransformer

# Tải tập stopwords và punkt từ NLTK
import ssl
ssl._create_default_https_context = ssl._create_unverified_context
nltk.download('stopwords')
nltk.download('punkt')

class SearchEngine:
    def __init__(self):
        self.inverted_index = defaultdict(set)
        self.documents = []
        self.stop_words = set(stopwords.words('english'))
        self.model = SentenceTransformer('paraphrase-MiniLM-L6-v2')

    def Print(self):
        print(self.inverted_index, self.documents)

    # def __init__(self, documents):
    #     self.inverted_index = defaultdict(set)
    #     self.documents = documents
    #     self.vectorizer = TfidfVectorizer(stop_words='english')
    #     self.model = SentenceTransformer('bert-base-nli-mean-tokens')
    #     self.corpus_embeddings = self.model.encode(documents)
    #     self.document_vectors = self.vectorizer.fit_transform(documents)

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

    # def search_by_keyword(self, keyword):
    #     keyword_vector = self.vectorizer.transform([keyword])
    #     cosine_similarities = cosine_similarity(keyword_vector, self.document_vectors).flatten()
    #     sorted_results = sorted(zip(range(len(cosine_similarities)), cosine_similarities), key=lambda x: x[1], reverse=True)
    #     relevant_docs = [(self.documents[idx], score) for idx, score in sorted_results]
    #     return relevant_docs

    # def search_by_sentence(self, sentence):
    #     sentence_embedding = self.model.encode([sentence])
    #     cosine_similarities = cosine_similarity(sentence_embedding, self.corpus_embeddings).flatten()
    #     sorted_results = sorted(zip(range(len(cosine_similarities)), cosine_similarities), key=lambda x: x[1], reverse=True)
    #     relevant_docs = [(self.documents[idx], score) for idx, score in sorted_results]
    #     return relevant_docs

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
            results_with_info.append([self.documents[idx], score])
        print(results_with_info)

        return results_with_info
        # return sorted_results

# Load dữ liệu từ file CSV
df = pd.read_csv('data_2.csv')

# Tạo một search engine và index dữ liệu
search_engine = SearchEngine()
for idx, row in df.iterrows():
    # search_engine.index_document(idx, row['Description'] + row['ProductName'] + row['ProductBrand'] + row['Gender'])
    search_engine.index_document(idx, row['text'])

def Testing(query):
    results = search_engine.search(query)
    for result in results:
        print(result)
    
    (search_engine.Print())

Testing("shoes")


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

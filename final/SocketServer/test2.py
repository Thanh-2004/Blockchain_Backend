from flask import Flask, request, jsonify
import pandas as pd

app = Flask(__name__)

# Đọc dữ liệu từ tệp CSV vào bộ nhớ
data = pd.read_csv('data_2.csv')

@app.route('/search', methods=['GET'])
def search():
    query = request.args.get('query')
    if query:
        # Tìm kiếm trong dữ liệu
        results = data[data['text'].str.contains(query, case=False)]['text'].tolist()
        return jsonify(results)
    else:
        return 'No query provided'

if __name__ == '__main__':
    app.run(debug=True)

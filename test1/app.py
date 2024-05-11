from flask import Flask, render_template, request
import pandas as pd
import numpy as np

app = Flask(__name__)

data = pd.read_csv('data2.csv', nrows=9)
print(data)

# # Load CSV data
data = pd.read_csv('data2.csv' , na_values=['NA', 'NaN'])
data.fillna(np.nan, inplace=True)


# Đặt tên cho các cột
column_names = ['index', 'link', 'source', 'type', 'title', 'summary', 'content', 'publish_date']  # Đặt tên cho các cột theo số lượng cột trong file CSV
data.columns = column_names

# In ra DataFrame đã có tên các cột
data.to_csv('data2.csv', index = False)


# @app.route('/')
# def index():
#     return render_template('index.html')

# @app.route('/search', methods=['POST'])
# def search():
#     query = request.form['query']
#     # Perform search in the CSV data
#     results = data[(data['name']+ data['age'] + data['city']).str.match(query, case=False)]
#     results = data[data[['name', 'age', 'city']].apply(lambda x: x.str.contains(fr'\b{query}\b', case=False)).any(axis=1)]

#     print(results)
#     return render_template('results.html', results=results)

# if __name__ == '__main__':
#     app.run(debug=True)


# import pandas as pd

# Tạo một Series (cột dữ liệu)
# s = pd.Series(['apple', 'banana', 'orange', 'pineapple', 'applepie'])

# Lọc các phần tử chỉ chứa chuỗi 'apple'
# results = s[s.str.fullmatch('apple')]

# results = data[(data['name']+ data['age'] + data['city'])].str.contains('')
# results = data[data[['name', 'age', 'city']].apply(lambda x: ' '.join(x), axis=1).str.match('Jo', case = False)]




# results = data[(data['name']+ data['age'] + data['city']).str.contains('an', case=False)]
# print(results)

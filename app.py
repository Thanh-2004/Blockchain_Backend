from flask import Flask, render_template, request
import pandas as pd

app = Flask(__name__)

# Load CSV data
data = pd.read_csv('data.csv')

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/search', methods=['POST'])
def search():
    query = request.form['query']
    # Perform search in the CSV data
    results = data[data['name'].str.contains(query, case=False)]
    return render_template('results.html', results=results)

if __name__ == '__main__':
    app.run(debug=True)

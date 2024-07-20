## Setup

1. Set up a virtual environment
2. Activate the virtual environment
3. Install the requirements
4. Run the server

### 1. Set up a virtual environment

```bash
python -m venv venv
```

### 2. Activate the virtual environment

```bash
.\env\Scripts\Activate
```

### 3. Install the requirements

```bash
pip install -r requirements.txt
```

### 4. Run the server

```bash
uvicorn main:app --reload
```

## About Backend

FastAPI is used to create the backend of the application. It is a modern, fast (high-performance), web framework for building APIs with Python 3.6+ based on standard Python type hints.

model.ipynb is a jupyter notebook that contains the code to train the model and save it as a pickle file.

main.py is the main file that contains the code to create the API endpoints.
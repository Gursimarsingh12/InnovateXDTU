## Setup

1. Set up a virtual environment
2. Activate the virtual environment
3. Install the requirements
4. Run the server

### 1. Set up a virtual environment

```bash
python3 -m venv venv
```

### 2. Activate the virtual environment

```bash
source venv/bin/activate
```

### 3. Install the requirements

```bash
pip install -r requirements.txt
```

### 4. Run the server

```bash
uvicorn main:app --reload
```


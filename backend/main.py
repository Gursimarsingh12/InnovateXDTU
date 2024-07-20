from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from routes.machineLearning import router as ml_router
from routes.User import router as user_router
from databaseSchema import Base
from databaseConnection import engine
import os
import uvicorn

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

Base.metadata.create_all(bind=engine) # Create the tables in the database

app.include_router(ml_router)
app.include_router(user_router)

if __name__ == "__main__":
    uvicorn.run(app, host="localhost", port=int(os.getenv("PORT")))
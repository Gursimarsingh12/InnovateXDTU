from fastapi import APIRouter
from objectSchema import MachineLearningModel
import pickle

router = APIRouter(
    prefix="/machine-learning",
    tags=["machine-learning"],
    responses={404: {"description": "Not found"}},
)

pickle_in = open("model.pkl", "rb")
model = pickle.load(pickle_in)

@router.post("/predict")
async def predict(data: MachineLearningModel):
    data = data.model_dump()
    building_id = data["building_id"]
    meter = data["meter"]
    site_id = data["site_id"]
    square_feet = data["square_feet"]
    year_built = data["year_built"]
    floor_count = data["floor_count"]
    air_temperature = data["air_temperature"]
    cloud_coverage = data["cloud_coverage"]
    dew_temperature = data["dew_temperature"]
    sea_level_pressure = data["sea_level_pressure"]
    primary_use_Education = data["primary_use_Education"]
    primary_use_Entertainment_public_assembly = data["primary_use_Entertainment_public_assembly"]
    primary_use_Food_sales_and_service = data["primary_use_Food_sales_and_service"]
    primary_use_Healthcare = data["primary_use_Healthcare"]
    primary_use_Lodging_residential = data["primary_use_Lodging_residential"]
    primary_use_Manufacturing_industrial = data["primary_use_Manufacturing_industrial"]
    primary_use_Office = data["primary_use_Office"]
    primary_use_Other = data["primary_use_Other"]
    primary_use_Parking = data["primary_use_Parking"]
    primary_use_Public_services = data["primary_use_Public_services"]
    primary_use_Religious_worship = data["primary_use_Religious_worship"]
    primary_use_Retail = data["primary_use_Retail"]
    primary_use_Technology_science = data["primary_use_Technology_science"]
    primary_use_Utility = data["primary_use_Utility"]
    primary_use_Warehouse_storage = data["primary_use_Warehouse_storage"]

    prediction = model.predict([[
    building_id, 
    meter, 
    site_id, 
    square_feet, 
    year_built, 
    floor_count, 
    air_temperature, 
    cloud_coverage, 
    dew_temperature, 
    sea_level_pressure, 
    primary_use_Education, 
    primary_use_Entertainment_public_assembly, 
    primary_use_Food_sales_and_service, 
    primary_use_Healthcare, 
    primary_use_Lodging_residential, 
    primary_use_Manufacturing_industrial, 
    primary_use_Office, 
    primary_use_Other, 
    primary_use_Parking, 
    primary_use_Public_services, 
    primary_use_Religious_worship, 
    primary_use_Retail, 
    primary_use_Technology_science, 
    primary_use_Utility, 
    primary_use_Warehouse_storage
]])
    return {
    "prediction": prediction[0]
}

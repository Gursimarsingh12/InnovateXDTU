from typing import List
from pydantic import BaseModel, EmailStr, Field

class MachineLearningModel(BaseModel):
    building_id: int
    meter: int
    site_id: int
    square_feet: float
    year_built: float
    floor_count: float
    air_temperature: float
    cloud_coverage: float
    dew_temperature: float
    sea_level_pressure: float
    primary_use_Education: float = Field(default=0.0)
    primary_use_Entertainment_public_assembly: float = Field(default=0.0)
    primary_use_Food_sales_and_service: float = Field(default=0.0)
    primary_use_Healthcare: float = Field(default=0.0)
    primary_use_Lodging_residential: float = Field(default=0.0)
    primary_use_Manufacturing_industrial: float = Field(default=0.0)
    primary_use_Office: float = Field(default=0.0)
    primary_use_Other: float = Field(default=0.0)
    primary_use_Parking: float = Field(default=0.0)
    primary_use_Public_services: float = Field(default=0.0)
    primary_use_Religious_worship: float = Field(default=0.0)
    primary_use_Retail: float = Field(default=0.0)
    primary_use_Technology_science: float = Field(default=0.0)
    primary_use_Utility: float = Field(default=0.0)
    primary_use_Warehouse_storage: float = Field(default=0.0)

class Device(BaseModel):
    deviceName: str
    deviceType: str
    deviceModel: str
    deviceLocation: str
    deviceBuilding: str
    deviceFloor: int
    deviceRoom: str
    deviceHourlyConsumption: int
    deviceUsageTime: int



class User(BaseModel):
    userEmail: EmailStr
    userDevices: List[Device] = []



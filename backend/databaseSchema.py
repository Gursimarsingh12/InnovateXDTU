from sqlalchemy import Column, Integer, String, ForeignKey, DateTime, Boolean
from sqlalchemy.orm import relationship
from databaseConnection import Base

class User(Base):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True, index=True)
    userEmail = Column(String, unique=True, index=True)
    userDevices = relationship("Device", back_populates="deviceOwner")

class Device(Base):
    __tablename__ = "devices"

    id = Column(Integer, primary_key=True, index=True)
    deviceName = Column(String, index=True)
    deviceType = Column(String, index=True)
    deviceModel = Column(String, index=True)
    deviceLocation = Column(String, index=True)
    deviceBuilding = Column(String, index=True)
    deviceFloor = Column(Integer, index=True)
    deviceRoom = Column(String, index=True)
    deviceHourlyConsumption = Column(Integer, index=True)
    deviceUsageTime = Column(Integer, index=True)
    deviceOwner = relationship("User", back_populates="userDevices")
    owner_id = Column(Integer, ForeignKey("users.id"))
from fastapi import APIRouter, Depends, HTTPException
from objectSchema import User, Device
from databaseConnection import SessionLocal
from databaseSchema import User as DBUser
from databaseSchema import Device as DBDevice
from sqlalchemy.orm import Session

router = APIRouter(
    prefix="/user",
    tags=["user"],
    responses={404: {"description": "Not found"}},
)

# Dependency
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

@router.post("/create-user")
def create_user(user: User, db: Session = Depends(get_db)):
    db_user = DBUser(userEmail=user.userEmail, userDevices=[])
    db.add(db_user)
    db.commit()
    db.refresh(db_user)
    return db_user

@router.post("/add-device")
def add_device(userEmail: str, device: Device, db: Session = Depends(get_db)):
    db_user = db.query(DBUser).filter(DBUser.userEmail == userEmail).first()
    if not db_user:
        raise HTTPException(status_code=404, detail="User not found")
    db_device = DBDevice(
        deviceName=device.deviceName,
        deviceType=device.deviceType,
        deviceLocation=device.deviceLocation,
        deviceFloor=device.deviceFloor,
        deviceHourlyConsumption=device.deviceHourlyConsumption,
        owner_id=db_user.id,
        deviceModel=device.deviceModel,
        deviceBuilding=device.deviceBuilding,
        deviceRoom=device.deviceRoom,
        deviceUsageTime=device.deviceUsageTime
    )
    db_user.userDevices.append(db_device)
    db.commit()
    db.refresh(db_user)
    return db_user

@router.get("/get-user")
def get_user(userEmail: str, db: Session = Depends(get_db)):
    db_user = db.query(DBUser).filter(DBUser.userEmail == userEmail).first()
    if not db_user:
        raise HTTPException(status_code=404, detail="User not found")
    return db_user

@router.get("/get-devices")
def get_devices(userEmail: str, db: Session = Depends(get_db)):
    db_user = db.query(DBUser).filter(DBUser.userEmail == userEmail).first()
    if not db_user:
        raise HTTPException(status_code=404, detail="User not found")
    db_devices = db_user.userDevices
    return db_devices

@router.post("/update-device")
def update_device(userEmail: str, device: Device, db: Session = Depends(get_db)):
    db_user = db.query(DBUser).filter(DBUser.userEmail == userEmail).first()
    if not db_user:
        raise HTTPException(status_code=404, detail="User not found")
    db_device = db.query(DBDevice).filter(DBDevice.deviceName == device.deviceName, DBDevice.owner_id == db_user.id).first()
    if not db_device:
        raise HTTPException(status_code=404, detail="Device not found")
    db_device.deviceType = device.deviceType
    db_device.deviceLocation = device.deviceLocation
    db_device.deviceFloor = device.deviceFloor
    db_device.deviceHourlyConsumption = device.deviceHourlyConsumption
    db_device.deviceModel = device.deviceModel
    db_device.deviceBuilding = device.deviceBuilding
    db_device.deviceRoom = device.deviceRoom
    db_device.deviceUsageTime = device.deviceUsageTime
    db.commit()
    db.refresh(db_device)
    return db_device

@router.post("/delete-device")
def delete_device(userEmail: str, deviceName: str, db: Session = Depends(get_db)):
    db_user = db.query(DBUser).filter(DBUser.userEmail == userEmail).first()
    if not db_user:
        raise HTTPException(status_code=404, detail="User not found")
    db_device = db.query(DBDevice).filter(DBDevice.deviceName == deviceName, DBDevice.owner_id == db_user.id).first()
    if not db_device:
        raise HTTPException(status_code=404, detail="Device not found")
    db.delete(db_device)
    db.commit()
    return {"detail": "Device deleted successfully"}

@router.post("/delete-user")
def delete_user(userEmail: str, db: Session = Depends(get_db)):
    db_user = db.query(DBUser).filter(DBUser.userEmail == userEmail).first()
    if not db_user:
        raise HTTPException(status_code=404, detail="User not found")
    db.delete(db_user)
    db.commit()
    return {"detail": "User deleted successfully"}

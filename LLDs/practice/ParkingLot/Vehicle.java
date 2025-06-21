package Placementprep.LLDs.practice.ParkingLot;

import Placementprep.LLDs.practice.ParkingLot.enums.VehicleType;

public abstract class Vehicle {
    String licensePlate;
    VehicleType type;

    Vehicle(String licensePlate, VehicleType type){
        this.licensePlate = licensePlate;
        this.type = type;
    }
}

class BikeVehicle extends Vehicle{
    BikeVehicle(String licensePlate){
        super(licensePlate, VehicleType.BIKE);
    }
}

class CompactVehicle extends Vehicle{
    CompactVehicle(String licensePlate){
        super(licensePlate, VehicleType.COMPACT);
    }
}

class TruckVehicle extends Vehicle{
    TruckVehicle(String licensePlate){
        super(licensePlate, VehicleType.TRUCK);
    }
}

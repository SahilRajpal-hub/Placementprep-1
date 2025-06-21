package Placementprep.LLDs.practice.ParkingLot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import Placementprep.LLDs.practice.ParkingLot.enums.ParkingSpotType;
import Placementprep.LLDs.practice.ParkingLot.enums.VehicleType;



public class ParkingFloors {
    int floor;
    HashMap<ParkingSpotType,ArrayList<ParkingSpot>> spots;


    public Optional<ParkingSpot> getAvailableSpot(Vehicle vehicle){
        ParkingSpotType parkingSpotType = getParkingSpotTypeFromVehicle(vehicle);
        return spots.get(parkingSpotType).stream().filter(parkingSpot -> !parkingSpot.isOccupied()).findFirst();

    }

    public ParkingSpotType getParkingSpotTypeFromVehicle(Vehicle vehicle){
        if(vehicle.type==VehicleType.BIKE) return ParkingSpotType.BIKE;
        if(vehicle.type==VehicleType.COMPACT) return ParkingSpotType.COMPACT;
        return ParkingSpotType.TRUCK;
    }

}

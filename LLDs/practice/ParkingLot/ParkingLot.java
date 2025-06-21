package Placementprep.LLDs.practice.ParkingLot;

import java.util.*;


public class ParkingLot {
    private static ParkingLot instance;
    private List<ParkingFloors> parkingFloors;

    private ParkingLot(){
        this.parkingFloors = new ArrayList<>();
    }

    public ParkingLot getInstance(){
        if(instance==null){
            instance=new ParkingLot();
        }
        return instance;
    }

    public ParkingSpot getParkingSpot(Vehicle vehicle){
        for(ParkingFloors parkingFloor : parkingFloors){
            ParkingSpot parkingSpot = parkingFloor.getAvailableSpot(vehicle).orElse(null);
            if(parkingSpot!=null) return parkingSpot;
        }
        // handle no parking spot found
        return null;
    }


    
}

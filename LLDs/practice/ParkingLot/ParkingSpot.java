package Placementprep.LLDs.practice.ParkingLot;

import Placementprep.LLDs.practice.ParkingLot.enums.ParkingSpotType;

public abstract class ParkingSpot {
    private String id;
    private boolean isOccupied;
    private ParkingSpotType type;

    ParkingSpot(String id,ParkingSpotType type){
        this.id = id;
        this.type = type;
    }

    public boolean isOccupied(){
        return this.isOccupied;
    }

    public void setOccupied(boolean isOccupied){
        this.isOccupied = isOccupied;
    }

}

class BikeParkingSpot extends ParkingSpot{
    BikeParkingSpot(String id){
        super(id, ParkingSpotType.BIKE);
    }
}

class CompactParkingSpot extends ParkingSpot{
    CompactParkingSpot(String id){
        super(id, ParkingSpotType.COMPACT);
    }
}

class TruckParkingSpot extends ParkingSpot{
    TruckParkingSpot(String id){
        super(id, ParkingSpotType.TRUCK);
    }
}

package Placementprep.LLDs.ParkingLot;

import Placementprep.LLDs.ParkingLot.enums.ParkingSpotType;

abstract class ParkingSpot {
    private final String id;
    private final ParkingSpotType type;
    private boolean isOccupied;
    private Vehicle vehicle;

    public ParkingSpot(String id,ParkingSpotType type) {
        this.id = id;
        this.type = type;
    }

    public boolean isAvailable() {
        return !isOccupied;
    }

    public void assignVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.isOccupied = true;
    }

    public void removeVehicle() {
        this.vehicle = null;
        this.isOccupied = false;
    }
}

class CompactSpot extends ParkingSpot {
    public CompactSpot(String id) { super(id,ParkingSpotType.COMPACT); }
}

class BikeSpot extends ParkingSpot {
    public BikeSpot(String id) { super(id,ParkingSpotType.BIKE); }
}

class LargeSpot extends ParkingSpot {
    public LargeSpot(String id) { super(id,ParkingSpotType.LARGE); }
}


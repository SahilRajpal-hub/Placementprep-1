package Placementprep.LLDs.ParkingLot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import Placementprep.LLDs.ParkingLot.enums.ParkingSpotType;

public class ParkingFloor {
    private int floorNumber;
    private Map<ParkingSpotType, List<ParkingSpot>> spots;


    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.spots = new HashMap<>();
        this.spots.put(ParkingSpotType.COMPACT, new ArrayList<>());
        this.spots.put(ParkingSpotType.BIKE, new ArrayList<>());
        this.spots.put(ParkingSpotType.LARGE, new ArrayList<>());
        // add more as needed
    }

    public Optional<ParkingSpot> getAvailableSpot(Vehicle vehicle) {
        // logic based on vehicle type
        ParkingSpotType type = getRequiredSpotType(vehicle);
        for (ParkingSpot spot : spots.get(type)) {
            if (spot.isAvailable()) {
                return Optional.of(spot);
            }
        }
        System.out.println("No slots found");
        return Optional.empty();
    }

    private ParkingSpotType getRequiredSpotType(Vehicle vehicle) {
        switch (vehicle.getType()) {
            case CAR: return ParkingSpotType.COMPACT;
            case BIKE: return ParkingSpotType.BIKE;
            default: return ParkingSpotType.LARGE;
        }
    }

    public Map<ParkingSpotType, List<ParkingSpot>> getSpots(){
        return this.spots;
    }
}


package Placementprep.LLDs.ParkingLot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParkingLot {
    private static ParkingLot INSTANCE;
    private List<ParkingFloor> floors;

    private ParkingLot() {
        this.floors = new ArrayList<>();
    }

    public static ParkingLot getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ParkingLot();
        }
        return INSTANCE;
    }

    public Optional<ParkingSpot> findSpotForVehicle(Vehicle vehicle) {
        for (ParkingFloor floor : floors) {
            Optional<ParkingSpot> spot = floor.getAvailableSpot(vehicle);
            if (spot.isPresent()) return spot;
        }
        return Optional.empty();
    }

    public List<ParkingFloor> getFloors(){
        return this.floors;
    }
}

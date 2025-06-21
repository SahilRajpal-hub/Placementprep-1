package Placementprep.LLDs.ParkingLot;

import java.util.Optional;
import java.util.UUID;


public class EntryGate {
    public Ticket generateTicket(Vehicle vehicle) {
        ParkingLot lot = ParkingLot.getInstance();
        Optional<ParkingSpot> optionalSpot = lot.findSpotForVehicle(vehicle);
        if (optionalSpot.isEmpty()) throw new RuntimeException("No spot available");
        
        ParkingSpot spot = optionalSpot.get();
        spot.assignVehicle(vehicle);
        return new Ticket(UUID.randomUUID().toString(), vehicle, spot);
    }
}

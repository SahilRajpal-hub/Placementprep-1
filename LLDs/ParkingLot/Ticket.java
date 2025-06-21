package Placementprep.LLDs.ParkingLot;

import java.time.LocalDateTime;

class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final LocalDateTime entryTime;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSpot spot) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();
    }

    public ParkingSpot getSpot(){
        return this.spot;
    }

    public Vehicle getVehicle(){
        return this.vehicle;
    }

    public String getTicketId(){
        return this.ticketId;
    }
}

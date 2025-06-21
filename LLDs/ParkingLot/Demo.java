package Placementprep.LLDs.ParkingLot;

import java.util.UUID;

import Placementprep.LLDs.ParkingLot.enums.ParkingSpotType;

public class Demo {
    public static void main(String[] args) {
        // Initialize parking lot with 1 floor and a few spots
        ParkingLot parkingLot = ParkingLot.getInstance();
        ParkingFloor floor1 = new ParkingFloor(1);

        // Add a few parking spots
        floor1.getSpots().get(ParkingSpotType.COMPACT).add(new CompactSpot("C1"));
        floor1.getSpots().get(ParkingSpotType.BIKE).add(new BikeSpot("B1"));

        parkingLot.getFloors().add(floor1);



        // Create a vehicle
        Vehicle myCar = new Car("DL01AB1234");

        // Simulate entry
        EntryGate entryGate = new EntryGate();
        Ticket ticket = entryGate.generateTicket(myCar);

        // would give error : no slots available
        // Ticket ticket2 = entryGate.generateTicket(myCar);

        System.out.println("Ticket Issued:");
        System.out.println("Ticket ID: " + ticket.getTicketId());
        System.out.println("Vehicle: " + ticket.getVehicle().getType());
        System.out.println("Spot Assigned: " + ticket.getSpot().getClass().getSimpleName());

        // Simulate exit
        ExitGate exitGate = new ExitGate();
        Payment payment = exitGate.processPayment(ticket);

        System.out.println("\nPayment Processed:");
        System.out.println("Transaction ID: " + payment.getTransactionId());
        System.out.println("Status: " + payment.getStatus());
        System.out.println("Amount: " + payment.getAmount());
    }
}

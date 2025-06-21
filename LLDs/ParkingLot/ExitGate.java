package Placementprep.LLDs.ParkingLot;

import java.util.UUID;

import Placementprep.LLDs.ParkingLot.enums.PaymentStatus;

public class ExitGate {
    public Payment processPayment(Ticket ticket) {
        // simple flat rate for now
        double amount = 20.0;
        ticket.getSpot().removeVehicle();
        return new Payment(UUID.randomUUID().toString(), PaymentStatus.SUCCESS, amount);
    }
}
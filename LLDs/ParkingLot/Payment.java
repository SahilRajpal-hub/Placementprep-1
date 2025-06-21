package Placementprep.LLDs.ParkingLot;

import java.time.LocalDateTime;

import Placementprep.LLDs.ParkingLot.enums.PaymentStatus;

public class Payment {
    private final String transactionId;
    private final PaymentStatus status;
    private final double amount;
    private final LocalDateTime time;

    public Payment(String transactionId, PaymentStatus status, double amount) {
        this.transactionId = transactionId;
        this.status = status;
        this.amount = amount;
        this.time = LocalDateTime.now();
    }

    // Getters
    public String getTransactionId() {
        return transactionId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTime() {
        return time;
    }
}

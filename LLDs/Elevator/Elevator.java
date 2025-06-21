package Placementprep.LLDs.Elevator;

import java.util.*;

import Placementprep.LLDs.Elevator.enums.Direction;
import Placementprep.LLDs.Elevator.enums.ElevatorState;

public class Elevator {
    private int currentFloor;
    private Direction direction;
    private ElevatorState state;

    private TreeSet<Integer> upStops;
    private TreeSet<Integer> downStops;
    private Queue<Request> pendingRequests;

    public Elevator() {
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
        this.state = ElevatorState.IDLE;
        this.upStops = new TreeSet<>();
        this.downStops = new TreeSet<>(Comparator.reverseOrder());
        this.pendingRequests = new LinkedList<>();
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public ElevatorState getState() {
        return state;
    }

    public void addNewRequest(Request request) {
        System.out.println("Received request from floor " + request.getSourceFloor() + " to floor " + request.getDestinationFloor());
        pendingRequests.offer(request);
    }

    public void move() {
        // If idle, check and process next request
        if (state == ElevatorState.IDLE && !pendingRequests.isEmpty()) {
            Request req = pendingRequests.poll();
            int source = req.getSourceFloor();
            int dest = req.getDestinationFloor();

            if (currentFloor != source) {
                if (currentFloor < source) {
                    upStops.add(source);
                    direction = Direction.UP;
                } else {
                    downStops.add(source);
                    direction = Direction.DOWN;
                }
                pendingRequests.add(req); // re-queue to process destination later
            } else {
                if (currentFloor < dest) {
                    upStops.add(dest);
                    direction = Direction.UP;
                } else {
                    downStops.add(dest);
                    direction = Direction.DOWN;
                }
            }
        }

        boolean moved = false;

        if (direction == Direction.UP && !upStops.isEmpty()) {
            int next = upStops.first();
            if (currentFloor < next) {
                currentFloor++;
                state = ElevatorState.MOVING;
                moved = true;
            } else if (currentFloor == next) {
                upStops.pollFirst();
                state = ElevatorState.STOPPED;
            }
        } else if (direction == Direction.DOWN && !downStops.isEmpty()) {
            int next = downStops.first();
            if (currentFloor > next) {
                currentFloor--;
                state = ElevatorState.MOVING;
                moved = true;
            } else if (currentFloor == next) {
                downStops.pollFirst();
                state = ElevatorState.STOPPED;
            }
        }

        if (upStops.isEmpty() && downStops.isEmpty()) {
            if (pendingRequests.isEmpty()) {
                direction = Direction.IDLE;
                state = ElevatorState.IDLE;
            } else {
                state = ElevatorState.IDLE; // allow picking up new requests
            }
        }
    }

    public void startElevator(){
        while (true) {
            this.move();
            System.out.println("Elevator at floor: " + this.getCurrentFloor()
                    + " Direction: " + this.getDirection()
                    + " State: " + this.getState());
            try {
                // Optional delay to observe simulation
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            } 
        }
    }
}

package Placementprep.LLDs.Elevator;

public class demo {
    public static void main(String[] args) throws InterruptedException {
        Elevator elevator = new Elevator();

        elevator.addNewRequest(new Request(0, 5));
        elevator.addNewRequest(new Request(3, 1));

        elevator.startElevator();
    }
}

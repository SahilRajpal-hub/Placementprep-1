package Placementprep.LLDs.Elevator;


public class Request {
    private int sourceFloor;
    private int destinationFloor;

    public Request(int sourceFloor, int destinationFloor) {
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    @Override
    public String toString() {
        return "Request{" +
                "from=" + sourceFloor +
                ", to=" + destinationFloor +
                '}';
    }
}

import java.util.HashMap;
import java.util.Map;

public class Start {
    public static void main(String[] args) {
        ElevatorHardware h1 = new ElevatorHardware();
        ElevatorHardware h2 = new ElevatorHardware();
        Map<Integer, Floor> e1Floors = new HashMap<>();
        Map<Integer, Floor> e2Floors = new HashMap<>();
        e1Floors.put(0, new Floor(0));
        e1Floors.put(2, new Floor(2));
        e1Floors.put(4, new Floor(4));
        e1Floors.put(6, new Floor(6));
        e1Floors.put(8, new Floor(8));
        e1Floors.put(10, new Floor(10));
        e2Floors.put(1, new Floor(1));
        e2Floors.put(3, new Floor(3));
        e2Floors.put(5, new Floor(5));
        e2Floors.put(7, new Floor(7));
        e2Floors.put(9, new Floor(9));

        Elevator e1 = new Elevator(h1, 1000, e1Floors);
        Elevator e2 = new Elevator(h2, 1000, e2Floors);

        int currentFloor = 2;
        int desiredFloor = 8;
        Request upOutsideRequest = new RequestBuilder()
                .setCurrentFloor(currentFloor)
                .setDirection(Direction.UP)
                .setLocation(Location.OUTSIDE)
                .getRequest();
        Request downOutsideRequest = new RequestBuilder()
                .setCurrentFloor(currentFloor)
                .setDirection(Direction.DOWN)
                .setLocation(Location.OUTSIDE)
                .getRequest();
        Request insideRequest = new RequestBuilder()
                .setDesiredFloor(desiredFloor)
                .setLocation(Location.INSIDE)
                .getRequest();

        e1.sendRequest(upOutsideRequest);
        e1.sendRequest(downOutsideRequest);
        e1.sendRequest(insideRequest);

        e1.run();
        e2.run();
    }
}

import java.util.*;

public class Elevator implements Observer {
    private ElevatorDoorState doorState = ElevatorDoorState.OPEN;
    private Direction direction = Direction.IDLE;
    private int currentFloor = 0;
    private int maxWeight;
    private Map<Integer, Floor> floors;
    private PriorityQueue<Request> upQueue;
    private PriorityQueue<Request> downQueue;
    private ElevatorHardware hardware;

    Elevator(ElevatorHardware hardware, int maxWeight, Map<Integer, Floor> floors) {
        this.hardware = hardware;
        this.maxWeight = maxWeight;
        this.floors = floors;

        // use default, which is a min heap
        upQueue = new PriorityQueue<>(Comparator.comparingInt(Request::getDesiredFloor));

        // use a max heap
        downQueue = new PriorityQueue<>((a, b) -> b.getDesiredFloor() - a.getDesiredFloor());

        //observe elevator moves and floor changes
        this.hardware.addObserver(this);
    }

    void sendRequest(Request request) {
        int targetFloor = request.getLocation() == Location.INSIDE ? request.getDesiredFloor() : request.getCurrentFloor();
        if (!floors.containsKey(targetFloor)) {
            return;
        }
        System.out.println("Append request going to floor " + targetFloor + ".");
        Request requestForQueue = new RequestBuilder().setDesiredFloor(targetFloor).getRequest();
        if (request.getLocation() == Location.OUTSIDE) {
            if (request.getDirection() == Direction.UP) {
                if (!upQueue.contains(requestForQueue)) {
                    upQueue.offer(requestForQueue);
                }
            } else {
                if (!downQueue.contains(requestForQueue)) {
                    downQueue.offer(requestForQueue);
                }
            }
        } else if (request.getLocation() == Location.INSIDE) {
            if (targetFloor > this.currentFloor) {
                if (!upQueue.contains(requestForQueue)) {
                    upQueue.offer(requestForQueue);
                }
            } else {
                if (!downQueue.contains(requestForQueue)) {
                    downQueue.offer(requestForQueue);
                }
            }
        }
        if (direction == Direction.IDLE) {
            processNextRequest();
        }
    }

    void run() {
        processNextRequest();
    }

    private void processNextRequest() {
        while (hardware.getLoadedWeight() > maxWeight) {
            System.out.println("Loaded weight is more than capacity!");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!upQueue.isEmpty() && direction != Direction.DOWN) {
            direction = Direction.UP;
            Request upRequest = upQueue.poll();
            System.out.println("Processing up request to floor " + upRequest.getDesiredFloor() + ".");
            floors.get(currentFloor).setDoorState(FloorDoorState.LOCKED);
            doorState = ElevatorDoorState.CLOSE;
            hardware.goToFloor(upRequest.getDesiredFloor());
            return;
        }
        if (!downQueue.isEmpty() && direction != Direction.UP) {
            direction = Direction.DOWN;
            Request downRequest = downQueue.poll();
            System.out.println("Processing down request to floor " + downRequest.getDesiredFloor() + ".");
            floors.get(currentFloor).setDoorState(FloorDoorState.LOCKED);
            doorState = ElevatorDoorState.CLOSE;
            hardware.goToFloor(downRequest.getDesiredFloor());
            return;
        }
        if (upQueue.isEmpty() && downQueue.isEmpty()) {
            System.out.println("Finished all requests.");
            direction = Direction.IDLE;
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o instanceof FloorChanged) {
            currentFloor = ((FloorChanged) o).getFloor();
        }
        if (o instanceof ElevatorMoveDone) {
            currentFloor = ((ElevatorMoveDone) o).getFloor();
            System.out.println("Elevator stopped at floor " + currentFloor + ".");

            floors.get(currentFloor).setDoorState(FloorDoorState.OPEN);
            doorState = ElevatorDoorState.OPEN;

            processNextRequest();
        }
    }
}

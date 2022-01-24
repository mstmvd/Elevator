class Request {
    private int currentFloor;
    private int desiredFloor;
    private Direction direction;
    private Location location;

    Request() {
    }

    int getCurrentFloor() {
        return currentFloor;
    }

    void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    int getDesiredFloor() {
        return desiredFloor;
    }

    void setDesiredFloor(int desiredFloor) {
        this.desiredFloor = desiredFloor;
    }

    Direction getDirection() {
        return direction;
    }

    void setDirection(Direction direction) {
        this.direction = direction;
    }

    Location getLocation() {
        return location;
    }

    void setLocation(Location location) {
        this.location = location;
    }
}

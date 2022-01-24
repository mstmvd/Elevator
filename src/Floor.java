public class Floor {
    private FloorDoorState doorState = FloorDoorState.OPEN;
    private int floor;

    Floor(int floor) {
        this.floor = floor;
    }

    public int getFloor() {
        return floor;
    }

    public FloorDoorState getDoorState() {
        return doorState;
    }

    void setDoorState(FloorDoorState doorState) {
        this.doorState = doorState;
    }
}

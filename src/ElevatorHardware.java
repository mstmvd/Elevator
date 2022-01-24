import java.util.Observable;

class ElevatorHardware extends Observable {

    private float loadedWeight = 0;
    private void floorChanged(int floor) {
        this.notifyObservers(new FloorChanged(floor));
    }

    /**
     * @param floor
     * controls hardware to go to desired floor and notify observers when command done
     */
    void goToFloor(int floor) {
        //todo: implement hardware communication
        this.notifyObservers(new ElevatorMoveDone(floor));
    }

    float getLoadedWeight() {
        return loadedWeight;
    }
}

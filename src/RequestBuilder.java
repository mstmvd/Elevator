public class RequestBuilder {
    private Request request;

    RequestBuilder() {
        this.request = new Request();
    }

    RequestBuilder setCurrentFloor(int floor) {
        this.request.setCurrentFloor(floor);
        return this;
    }

    RequestBuilder setDesiredFloor(int floor) {
        this.request.setDesiredFloor(floor);
        return this;
    }

    RequestBuilder setDirection(Direction direction) {
        this.request.setDirection(direction);
        return this;
    }

    RequestBuilder setLocation(Location location) {
        this.request.setLocation(location);
        return this;
    }

    public RequestBuilder reset() {
        this.request = new Request();
        return this;
    }

    Request getRequest() {
        return this.request;
    }
}

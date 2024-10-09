package seedu.address.model.event;

public class Name {
    private String eventName;

    public Name(String eventName) {
        this.eventName = eventName;
    }

    public String getName() {
        return eventName;
    }

    @Override
    public String toString() {
        return eventName;
    }
}

package seedu.address.model.event;

public class Time {
    private String eventTime;

    public Time(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getTime() {
        return eventTime;
    }

    @Override
    public String toString() {
        return eventTime;
    }
}

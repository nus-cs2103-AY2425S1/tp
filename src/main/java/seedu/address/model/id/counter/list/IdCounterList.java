package seedu.address.model.id.counter.list;

/**
 * Represents the ID counter for both persons and events in the address book.
 * Guarantees: ID counter for persons/events is more than or equal to the number of persons/events in the address book.
 */
public class IdCounterList {
    private int personIdCounter;
    private int eventIdCounter;

    /**
     * Constructs an {@code IdCounterList} with the given arguments.
     */
    public IdCounterList(int personIdCounter, int eventIdCounter) {
        this.personIdCounter = personIdCounter;
        this.eventIdCounter = eventIdCounter;
    }

    /**
     * Constructs an {@code IdCounterList} with both ID counters initialised to 0.
     */
    public IdCounterList() {
        personIdCounter = 0;
        eventIdCounter = 0;
    }

    public int getPersonIdCounter() {
        return personIdCounter;
    }

    public int getEventIdCounter() {
        return eventIdCounter;
    }

    public void setPersonIdCounter(int personIdCounter) {
        this.personIdCounter = personIdCounter;
    }

    public void setEventIdCounter(int eventIdCounter) {
        this.eventIdCounter = eventIdCounter;
    }

    /**
     * Checks if the person ID counter in this {@code IdCounterList} object is valid.
     */
    public boolean isValidPersonIdCounter(int largestPersonId) {
        return this.personIdCounter >= largestPersonId;
    }

    /**
     * Checks if the event ID counter in this {@code IdCounterList} object is valid.
     */
    public boolean isValidEventIdCounter(int largestEventId) {
        return this.eventIdCounter >= largestEventId;
    }

    /**
     * Generates a new unique person ID.
     */
    public int generatePersonId() {
        personIdCounter++;
        return personIdCounter;
    }

    /**
     * Generates a new unique event ID.
     */
    public int generateEventId() {
        eventIdCounter++;
        return eventIdCounter;
    }
}

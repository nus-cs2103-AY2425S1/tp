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

    /**
     * Increment person ID counter by 1.
     */
    public void incrementPersonIdCounter() {
        personIdCounter++;
    }

    /**
     * Increment event ID counter by 1.
     */
    public void incrementEventIdCounter() {
        eventIdCounter++;
    }
}

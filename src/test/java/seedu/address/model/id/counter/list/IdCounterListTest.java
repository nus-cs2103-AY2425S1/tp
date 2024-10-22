package seedu.address.model.id.counter.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IdCounterListTest {
    private static final int DEFAULT_PERSON_ID_COUNTER = 5;
    private static final int DEFAULT_EVENT_ID_COUNTER = 5;

    @Test
    public void constructor_validInputs_success() {
        IdCounterList idCounterList = new IdCounterList(DEFAULT_PERSON_ID_COUNTER, DEFAULT_EVENT_ID_COUNTER);
        assertEquals(idCounterList.getPersonIdCounter(), DEFAULT_PERSON_ID_COUNTER);
        assertEquals(idCounterList.getEventIdCounter(), DEFAULT_EVENT_ID_COUNTER);
    }

    @Test
    public void constructorWithNoArgs_success() {
        IdCounterList idCounterList = new IdCounterList();
        assertEquals(idCounterList.getPersonIdCounter(), 0);
        assertEquals(idCounterList.getEventIdCounter(), 0);
    }

    @Test
    public void isValidPersonIdCounter_largestPersonIdLargerThanPersonIdCounter_returnFalse() {
        IdCounterList idCounterList = new IdCounterList();
        assertFalse(idCounterList.isValidPersonIdCounter(1));
    }

    @Test
    public void isValidEventIdCounter_largestEventIdLargerThanEventIdCounter_returnFalse() {
        IdCounterList idCounterList = new IdCounterList();
        assertFalse(idCounterList.isValidEventIdCounter(1));
    }

    @Test
    public void isValidPersonIdCounter_largestPersonIdEqualToPersonIdCounter_returnTrue() {
        IdCounterList idCounterList = new IdCounterList();
        assertTrue(idCounterList.isValidPersonIdCounter(0));
    }

    @Test
    public void isValidEventIdCounter_largestEventIdEqualToEventIdCounter_returnTrue() {
        IdCounterList idCounterList = new IdCounterList();
        assertTrue(idCounterList.isValidEventIdCounter(0));
    }

    @Test
    public void isValidPersonIdCounter_largestPersonIdSmallerThanPersonIdCounter_returnTrue() {
        IdCounterList idCounterList = new IdCounterList(DEFAULT_PERSON_ID_COUNTER, DEFAULT_EVENT_ID_COUNTER);
        assertTrue(idCounterList.isValidPersonIdCounter(DEFAULT_PERSON_ID_COUNTER - 1));
    }

    @Test
    public void isValidEventIdCounter_largestEventIdSmallerThanEventIdCounter_returnTrue() {
        IdCounterList idCounterList = new IdCounterList(DEFAULT_PERSON_ID_COUNTER, DEFAULT_EVENT_ID_COUNTER);
        assertTrue(idCounterList.isValidEventIdCounter(DEFAULT_EVENT_ID_COUNTER - 1));
    }

    @Test
    public void generatePersonId_success() {
        IdCounterList idCounterList = new IdCounterList(DEFAULT_PERSON_ID_COUNTER, DEFAULT_EVENT_ID_COUNTER);
        assertEquals(idCounterList.generatePersonId(), DEFAULT_PERSON_ID_COUNTER + 1);
    }

    @Test
    public void generateEventId_success() {
        IdCounterList idCounterList = new IdCounterList(DEFAULT_PERSON_ID_COUNTER, DEFAULT_EVENT_ID_COUNTER);
        assertEquals(idCounterList.generateEventId(), DEFAULT_EVENT_ID_COUNTER + 1);
    }
}

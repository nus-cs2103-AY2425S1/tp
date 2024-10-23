package seedu.ddd.model.event.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.TypicalContacts.BOB;
import static seedu.ddd.testutil.TypicalEvents.WEDDING_A;
import static seedu.ddd.testutil.TypicalEvents.WEDDING_B;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.event.exceptions.DuplicateEventException;
import seedu.ddd.model.event.exceptions.EventNotFoundException;
import seedu.ddd.testutil.EventBuilder;

public class UniqueEventListTest {
    private final UniqueEventList uniqueEventList = new UniqueEventList();

    @Test
    public void contains_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.contains(null));
    }

    // Assumption: Use contains on a empty event list should always return false.
    @Test
    public void contains_eventNotInList_returnFalse() {
        assertFalse(uniqueEventList.contains(WEDDING_A));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        uniqueEventList.add(WEDDING_A);
        assertTrue(uniqueEventList.contains(WEDDING_A));
    }

    @Test
    public void contains_contactWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEventList.add(WEDDING_A);
        Event editedWeddingA = new EventBuilder(WEDDING_A).withEventId(1024)
                .withVendors(List.of(BOB))
                .build();
        assertTrue(uniqueEventList.contains(editedWeddingA));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.add(null));
    }

    @Test
    public void add_duplicateEvent_throwsDuplicateEventException() {
        uniqueEventList.add(WEDDING_A);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.add(WEDDING_A));
    }

    @Test
    public void setEvent_nullTargetEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(null, WEDDING_A));
    }

    @Test
    public void setEvent_nullEditedEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(WEDDING_A, null));
    }

    @Test
    public void setEvent_targetEventNotInList_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.setEvent(WEDDING_A, WEDDING_A));
    }

    @Test
    public void setContact_editedEventIsSameEvent_success() {
        uniqueEventList.add(WEDDING_A);
        uniqueEventList.setEvent(WEDDING_A, WEDDING_A);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(WEDDING_A);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasSameIdentity_success() {
        uniqueEventList.add(WEDDING_A);
        Event editedWeddingA = new EventBuilder(WEDDING_A).withVendors(List.of(BOB))
                .build();
        uniqueEventList.setEvent(WEDDING_A, editedWeddingA);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(editedWeddingA);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasDifferentIdentity_success() {
        uniqueEventList.add(WEDDING_A);
        uniqueEventList.setEvent(WEDDING_A, WEDDING_B);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(WEDDING_B);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void remove_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.remove(null));
    }

    @Test
    public void remove_eventDoesNotExist_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.remove(WEDDING_A));
    }

    @Test
    public void remove_existingEvent_removesEvent() {
        uniqueEventList.add(WEDDING_A);
        uniqueEventList.remove(WEDDING_A);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullUniqueEventList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((UniqueEventList) null));
    }

    @Test
    public void setEvents_uniqueEventList_replaceOwnListWithProvidedUniqueEventList() {
        uniqueEventList.add(WEDDING_A);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(WEDDING_B);
        uniqueEventList.setEvents(expectedUniqueEventList);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_list_replacesOwnListWithProvidedList() {
        uniqueEventList.add(WEDDING_A);
        List<Event> eventList = Collections.singletonList(WEDDING_B);
        uniqueEventList.setEvents(eventList);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(WEDDING_B);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_listWithDuplicateEvents_throwsDuplicateEventException() {
        List<Event> listWithDuplicateEvents = Arrays.asList(WEDDING_A, WEDDING_A);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvents(listWithDuplicateEvents));
    }

    @Test
    public void toStringMethod() {
        uniqueEventList.add(WEDDING_A);
        assertEquals(uniqueEventList.asUnmodifiableObservableList().toString(), uniqueEventList.toString());
    }
}

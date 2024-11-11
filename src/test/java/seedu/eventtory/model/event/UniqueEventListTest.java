package seedu.eventtory.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_DATE_BIRTHDAY;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_NAME_BIRTHDAY;
import static seedu.eventtory.testutil.Assert.assertThrows;
import static seedu.eventtory.testutil.TypicalEvents.ALICE;
import static seedu.eventtory.testutil.TypicalEvents.BENSON;
import static seedu.eventtory.testutil.TypicalEvents.BIRTHDAY;
import static seedu.eventtory.testutil.TypicalEvents.WEDDING;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.eventtory.model.event.exceptions.DuplicateEventException;
import seedu.eventtory.model.event.exceptions.EventNotFoundException;
import seedu.eventtory.testutil.EventBuilder;

public class UniqueEventListTest {
    private final UniqueEventList uniqueEventList = new UniqueEventList();

    @Test
    public void contains_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.contains(null));
    }

    @Test
    public void contains_eventNotInList_returnsFalse() {
        assertFalse(uniqueEventList.contains(WEDDING));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        uniqueEventList.add(WEDDING);
        assertTrue(uniqueEventList.contains(WEDDING));
    }

    @Test
    public void contains_eventWithSameIdentityFieldsInList_returnsTrue() {
        Event similarWedding = new EventBuilder(WEDDING).withDate(VALID_DATE_BIRTHDAY).build();
        uniqueEventList.add(WEDDING);
        assertTrue(uniqueEventList.contains(similarWedding));
    }

    @Test
    public void add_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.add(null));
    }

    @Test
    public void add_duplicateEvent_throwsDuplicateEventException() {
        uniqueEventList.add(WEDDING);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.add(WEDDING));
    }

    @Test
    public void setEvent_nullTargetEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(null, ALICE));
    }

    @Test
    public void setEvent_nullEditedEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(ALICE, null));
    }

    @Test
    public void setEvent_targetEventNotInList_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.setEvent(ALICE, ALICE));
    }

    @Test
    public void setEvent_editedEventIsSameEvent_success() {
        uniqueEventList.add(ALICE);
        uniqueEventList.setEvent(ALICE, ALICE);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(ALICE);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasSameIdentity_success() {
        uniqueEventList.add(ALICE);
        Event editedAlice = new EventBuilder(ALICE).withDate(VALID_DATE_BIRTHDAY).withName(VALID_NAME_BIRTHDAY)
                .build();
        uniqueEventList.setEvent(ALICE, editedAlice);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(editedAlice);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasDifferentIdentity_success() {
        uniqueEventList.add(ALICE);
        uniqueEventList.setEvent(ALICE, BENSON);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(BENSON);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasNonUniqueIdentity_throwsDuplicateEventException() {
        uniqueEventList.add(ALICE);
        uniqueEventList.add(BENSON);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvent(ALICE, BENSON));
    }

    @Test
    public void remove_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.remove(null));
    }

    @Test
    public void remove_eventDoesNotExist_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.remove(WEDDING));
    }

    @Test
    public void remove_existingEvent_removesEvent() {
        uniqueEventList.add(WEDDING);
        uniqueEventList.remove(WEDDING);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullUniqueEventList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((UniqueEventList) null));
    }

    @Test
    public void setEvents_uniqueEventList_replacesOwnListWithProvidedUniqueEventList() {
        uniqueEventList.add(WEDDING);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(WEDDING);
        uniqueEventList.setEvents(expectedUniqueEventList);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((List<Event>) null));
    }

    @Test
    public void setEvents_list_replacesOwnListWithProvidedList() {
        uniqueEventList.add(WEDDING);
        List<Event> eventList = List.of(BIRTHDAY);
        uniqueEventList.setEvents(eventList);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(BIRTHDAY);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_listWithDuplicateEvents_throwsDuplicateEventException() {
        List<Event> listWithDuplicateEvents = Arrays.asList(WEDDING, WEDDING);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvents(listWithDuplicateEvents));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueEventList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueEventList.asUnmodifiableObservableList().toString(), uniqueEventList.toString());
    }
}

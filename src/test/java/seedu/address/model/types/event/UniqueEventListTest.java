package seedu.address.model.types.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ART_EXHIBIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_ART_EXHIBIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CULTURE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.ART_EXHIBIT;
import static seedu.address.testutil.TypicalEvents.BOOK_FAIR;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.types.event.exceptions.DuplicateEventException;
import seedu.address.model.types.event.exceptions.EventNotFoundException;
import seedu.address.testutil.EventBuilder;

public class UniqueEventListTest {

    private final UniqueEventList uniqueEventList = new UniqueEventList();

    @Test
    public void contains_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.contains(null));
    }

    @Test
    public void contains_eventNotInList_returnsFalse() {
        assertFalse(uniqueEventList.contains(ART_EXHIBIT));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        uniqueEventList.add(ART_EXHIBIT);
        assertTrue(uniqueEventList.contains(ART_EXHIBIT));
    }

    @Test
    public void contains_eventWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEventList.add(ART_EXHIBIT);
        Event editedArtExhibit = new EventBuilder(ART_EXHIBIT).withAddress(VALID_ADDRESS_ART_EXHIBIT)
                .withStartTime(VALID_START_TIME_ART_EXHIBIT).withTags(VALID_TAG_CULTURE).build();
        assertTrue(uniqueEventList.contains(editedArtExhibit));
    }

    @Test
    public void add_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.add(null));
    }

    @Test
    public void add_duplicateEvent_throwsDuplicateEventException() {
        uniqueEventList.add(ART_EXHIBIT);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.add(ART_EXHIBIT));
    }

    @Test
    public void setEvent_nullTargetEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(null, ART_EXHIBIT));
    }

    @Test
    public void setEvent_nullEditedEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(ART_EXHIBIT, null));
    }

    @Test
    public void setEvent_targetEventNotInList_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.setEvent(ART_EXHIBIT, ART_EXHIBIT));
    }

    @Test
    public void setEvent_editedEventIsSameEvent_success() {
        uniqueEventList.add(ART_EXHIBIT);
        uniqueEventList.setEvent(ART_EXHIBIT, ART_EXHIBIT);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(ART_EXHIBIT);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasSameIdentity_success() {
        uniqueEventList.add(ART_EXHIBIT);
        Event editedArtExhibit = new EventBuilder(ART_EXHIBIT).withAddress(VALID_ADDRESS_ART_EXHIBIT)
                .withStartTime(VALID_START_TIME_ART_EXHIBIT).withTags(VALID_TAG_CULTURE).build();
        uniqueEventList.setEvent(ART_EXHIBIT, editedArtExhibit);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(editedArtExhibit);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasDifferentIdentity_success() {
        uniqueEventList.add(ART_EXHIBIT);
        uniqueEventList.setEvent(ART_EXHIBIT, BOOK_FAIR);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(BOOK_FAIR);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasNonUniqueIdentity_throwsDuplicateEventException() {
        uniqueEventList.add(ART_EXHIBIT);
        uniqueEventList.add(BOOK_FAIR);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvent(ART_EXHIBIT, BOOK_FAIR));
    }

    @Test
    public void remove_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.remove(null));
    }

    @Test
    public void remove_eventDoesNotExist_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.remove(ART_EXHIBIT));
    }

    @Test
    public void remove_existingEvent_removesEvent() {
        uniqueEventList.add(ART_EXHIBIT);
        uniqueEventList.remove(ART_EXHIBIT);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullUniqueEventList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((UniqueEventList) null));
    }

    @Test
    public void setEvents_uniqueEventList_replacesOwnListWithProvidedUniqueEventList() {
        uniqueEventList.add(ART_EXHIBIT);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(BOOK_FAIR);
        uniqueEventList.setEvents(expectedUniqueEventList);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((List<Event>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueEventList.add(ART_EXHIBIT);
        List<Event> eventList = Collections.singletonList(BOOK_FAIR);
        uniqueEventList.setEvents(eventList);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(BOOK_FAIR);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_listWithDuplicateEvents_throwsDuplicateEventException() {
        List<Event> listWithDuplicateEvents = Arrays.asList(ART_EXHIBIT, ART_EXHIBIT);
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

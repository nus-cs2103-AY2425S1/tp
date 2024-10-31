package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_FROM_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TO_DATE_1;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalEvents.WORKSHOP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.testutil.Assert;
import seedu.address.testutil.EventBuilder;

public class UniqueEventListTest {
    private final UniqueEventList uniqueEventList = new UniqueEventList();

    @Test
    public void contains_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.contains(null));
    }

    @Test
    public void contains_eventNotInList_returnsFalse() {
        assertFalse(uniqueEventList.contains(MEETING));
    }

    @Test
    public void contains_eventInList_returnsTrue() {
        uniqueEventList.add(MEETING);
        assertTrue(uniqueEventList.contains(MEETING));
    }

    @Test
    public void contains_eventWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEventList.add(MEETING);
        Event editedMeeting = new EventBuilder(MEETING).withEventDescription(VALID_EVENT_DESCRIPTION_WORKSHOP)
                .withEventDuration(VALID_EVENT_FROM_DATE_1, VALID_EVENT_TO_DATE_1).build();
        assertTrue(uniqueEventList.contains(editedMeeting));
    }

    @Test
    public void containsName_nullEvent_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueEventList.containsName(null));
    }

    @Test
    public void containsName_eventNotInList_returnsFalse() {
        assertFalse(uniqueEventList.containsName(MEETING.getName()));
    }

    @Test
    public void containsName_eventInList_returnsTrue() {
        uniqueEventList.add(MEETING);
        assertTrue(uniqueEventList.containsName(MEETING.getName()));
    }

    @Test
    public void containsName_eventWithLowerCasedNameInList_returnsTrue() {
        uniqueEventList.add(MEETING);
        String nameString = MEETING.getName().toString();
        String lowerCasedNameString = nameString.toLowerCase();
        EventName lowerCasedName = new EventName(lowerCasedNameString);
        assertTrue(uniqueEventList.containsName(lowerCasedName));
    }

    @Test
    public void containsName_eventWithUpperCasedNameInList_returnsTrue() {
        uniqueEventList.add(MEETING);
        String nameString = MEETING.getName().toString();
        String upperCasedNameString = nameString.toUpperCase();
        EventName upperCasedName = new EventName(upperCasedNameString);
        assertTrue(uniqueEventList.containsName(upperCasedName));
    }

    @Test
    public void containsName_eventWithPartOfNameNotInList_returnsFalse() {
        uniqueEventList.add(MEETING);
        String nameString = MEETING.getName().toString();
        String partOfNameString = nameString.substring(0, nameString.length() - 1);
        EventName partOfName = new EventName(partOfNameString);
        assertFalse(uniqueEventList.containsName(partOfName));
    }

    @Test
    public void containsId_eventWithSameIdAsGivenArgument_returnsTrue() {
        uniqueEventList.add(MEETING);
        assertTrue(uniqueEventList.containsId(MEETING.getEventId()));
    }

    @Test
    public void containsId_eventWithDifferentIdAsGivenArgument_returnsFalse() {
        uniqueEventList.add(MEETING);
        assertFalse(uniqueEventList.containsId(MEETING.getEventId() + 1));
    }

    @Test
    public void add_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.add(null));
    }
    @Test
    public void add_duplicateEvent_throwsDuplicateEventException() {
        uniqueEventList.add(MEETING);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.add(MEETING));
    }

    @Test
    public void setEvent_nullTargetEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(null, MEETING));
    }
    @Test
    public void setEvent_nullEditedEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(MEETING, null));
    }
    @Test
    public void setEvent_targetEventNotInList_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.setEvent(MEETING, MEETING));
    }
    @Test
    public void setEvent_editedEventIsSameEvent_success() {
        uniqueEventList.add(MEETING);
        uniqueEventList.setEvent(MEETING, MEETING);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(MEETING);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasSameIdentity_success() {
        uniqueEventList.add(MEETING);
        Event editedMeeting = new EventBuilder(MEETING).withEventDescription(VALID_EVENT_DESCRIPTION_WORKSHOP)
                .withEventDuration(VALID_EVENT_FROM_DATE_1, VALID_EVENT_TO_DATE_1).build();
        uniqueEventList.setEvent(MEETING, editedMeeting);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(editedMeeting);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasDifferentIdentity_success() {
        uniqueEventList.add(MEETING);
        uniqueEventList.setEvent(MEETING, WORKSHOP);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(WORKSHOP);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvent_editedEventHasNonUniqueIdentity_throwsDuplicateEventException() {
        uniqueEventList.add(MEETING);
        uniqueEventList.add(WORKSHOP);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvent(MEETING, WORKSHOP));
    }

    @Test
    public void remove_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.remove(null));
    }

    @Test
    public void remove_eventDoesNotExist_throwsEventNotFoundException() {
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.remove(MEETING));
    }

    @Test
    public void remove_existingEvent_removesEvent() {
        uniqueEventList.add(MEETING);
        uniqueEventList.remove(MEETING);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void getEventsWithName_nullName_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> uniqueEventList.getEventsWithName(null));
    }

    @Test
    public void getEventsWithName_existingEventName_returnsEventList() {
        uniqueEventList.add(MEETING);
        List<Event> eventList = uniqueEventList.getEventsWithName(MEETING.getName());
        List<Event> resultList = new ArrayList<>();
        resultList.add(MEETING);
        assertEquals(eventList, resultList);
    }

    @Test
    public void getEventsWithName_existingEventNameLowerCased_returnsEventList() {
        uniqueEventList.add(MEETING);
        EventName lowerCasedName = new EventName(MEETING.getName().toString().toLowerCase());
        List<Event> eventList = uniqueEventList.getEventsWithName(lowerCasedName);
        List<Event> resultList = new ArrayList<>();
        resultList.add(MEETING);
        assertEquals(eventList, resultList);
    }

    @Test
    public void getEventsWithName_existingEventNameUpperCased_returnsEventList() {
        uniqueEventList.add(MEETING);
        EventName upperCasedName = new EventName(MEETING.getName().toString().toUpperCase());
        List<Event> eventList = uniqueEventList.getEventsWithName(upperCasedName);
        List<Event> resultList = new ArrayList<>();
        resultList.add(MEETING);
        assertEquals(eventList, resultList);
    }

    @Test
    public void getEventsWithName_partOfExistingEventName_returnsEmptyList() {
        uniqueEventList.add(MEETING);
        String nameString = MEETING.getName().toString();
        String partOfNameString = nameString.substring(0, nameString.length() - 1);
        EventName partOfName = new EventName(partOfNameString);
        List<Event> eventList = uniqueEventList.getEventsWithName(partOfName);
        List<Event> resultList = new ArrayList<>();
        assertEquals(eventList, resultList);
    }

    @Test
    public void getEventsWithName_nonExistingEventName_returnsEmptyList() {
        List<Event> eventList = uniqueEventList.getEventsWithName(MEETING.getName());
        List<Event> resultList = new ArrayList<>();
        assertEquals(eventList, resultList);
    }

    @Test
    public void setEvents_nullUniqueEventList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((UniqueEventList) null));
    }

    @Test
    public void setEvents_uniqueEventList_replacesOwnListWithProvidedUniqueEventList() {
        uniqueEventList.add(MEETING);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(WORKSHOP);
        uniqueEventList.setEvents(expectedUniqueEventList);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((UniqueEventList) null));
    }

    @Test
    public void setEvents_list_replacesOwnListWithProvidedList() {
        uniqueEventList.add(MEETING);
        UniqueEventList expectedUniqueEventList = new UniqueEventList();
        expectedUniqueEventList.add(WORKSHOP);
        uniqueEventList.setEvents(expectedUniqueEventList);
        assertEquals(expectedUniqueEventList, uniqueEventList);
    }

    @Test
    public void setEvents_listWithDuplicateEvents_throwsDuplicateEventException() {
        List<Event> listWithDuplicateEvents = Arrays.asList(MEETING, MEETING);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvents(listWithDuplicateEvents));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
            uniqueEventList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueEventList.asUnmodifiableObservableList().toString(), uniqueEventList.toString());
    }
}

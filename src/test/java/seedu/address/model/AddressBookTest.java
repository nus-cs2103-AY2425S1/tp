package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.event.Event;
import seedu.address.model.exceptions.DuplicateAssignException;
import seedu.address.model.exceptions.NotAssignedException;
import seedu.address.model.exceptions.OverlappingAssignException;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.VolunteerBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    private Volunteer volunteerAlice;
    private Volunteer volunteerBob;
    private Event eventMeeting;
    private Event eventWorkshop;

    @BeforeEach
    public void setUp() {
        volunteerAlice = new VolunteerBuilder().withName("Alice").build();
        volunteerBob = new VolunteerBuilder().withName("Bob").build();
        eventMeeting = new EventBuilder().withEventName("Meeting").build();
        eventWorkshop = new EventBuilder().withEventName("Workshop").build();
    }

    @Test
    public void constructor_initializesEmptyManagers() {
        assertEquals(Collections.emptyList(), addressBook.getVolunteerList());
        assertEquals(Collections.emptyList(), addressBook.getEventList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidData_replacesData() {
        AddressBook newAddressBook = new AddressBook();
        newAddressBook.addVolunteer(volunteerAlice);
        newAddressBook.addEvent(eventMeeting);

        addressBook.resetData(newAddressBook);
        assertEquals(newAddressBook, addressBook);
    }

    @Test
    public void hasVolunteer_nullVolunteer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasVolunteer(null));
    }

    @Test
    public void hasVolunteer_volunteerNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasVolunteer(volunteerAlice));
    }

    @Test
    public void hasVolunteer_volunteerInAddressBook_returnsTrue() {
        addressBook.addVolunteer(volunteerAlice);
        assertTrue(addressBook.hasVolunteer(volunteerAlice));
    }

    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasEvent(null));
    }

    @Test
    public void hasEvent_eventNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasEvent(eventMeeting));
    }

    @Test
    public void hasEvent_eventInAddressBook_returnsTrue() {
        addressBook.addEvent(eventMeeting);
        assertTrue(addressBook.hasEvent(eventMeeting));
    }

    @Test
    public void addVolunteer_successfullyAddsVolunteer() {
        addressBook.addVolunteer(volunteerAlice);
        List<Volunteer> volunteerList = addressBook.getVolunteerList();
        assertTrue(volunteerList.contains(volunteerAlice));
    }

    @Test
    public void addEvent_successfullyAddsEvent() {
        addressBook.addEvent(eventMeeting);
        List<Event> eventList = addressBook.getEventList();
        assertTrue(eventList.contains(eventMeeting));
    }

    @Test
    public void assignVolunteerToEvent_successfulAssignment()
            throws DuplicateAssignException, OverlappingAssignException {
        addressBook.addVolunteer(volunteerAlice);
        addressBook.addEvent(eventMeeting);
        addressBook.assignVolunteerToEvent(volunteerAlice, eventMeeting);

        assertTrue(eventMeeting.getVolunteers().contains(volunteerAlice.getName().toString()));
        assertTrue(volunteerAlice.getEvents().contains(eventMeeting.getName().toString()));
    }

    @Test
    public void assignVolunteerToEvent_duplicateAssignment_throwsDuplicateAssignException()
            throws DuplicateAssignException, OverlappingAssignException {
        addressBook.addVolunteer(volunteerAlice);
        addressBook.addEvent(eventMeeting);
        addressBook.assignVolunteerToEvent(volunteerAlice, eventMeeting);

        assertThrows(
                DuplicateAssignException.class, () -> addressBook.assignVolunteerToEvent(volunteerAlice, eventMeeting)
        );
    }

    @Test
    public void unassignVolunteerFromEvent_successfulUnassignment()
            throws DuplicateAssignException, NotAssignedException, OverlappingAssignException {
        addressBook.addVolunteer(volunteerAlice);
        addressBook.addEvent(eventMeeting);
        addressBook.assignVolunteerToEvent(volunteerAlice, eventMeeting);
        addressBook.unassignVolunteerFromEvent(volunteerAlice, eventMeeting);

        assertFalse(eventMeeting.getVolunteers().contains(volunteerAlice.getName().toString()));
        assertFalse(volunteerAlice.getEvents().contains(eventMeeting.getName().toString()));
    }

    @Test
    public void unassignVolunteerFromEvent_notAssigned_throwsNotAssignedException() {
        addressBook.addVolunteer(volunteerAlice);
        addressBook.addEvent(eventMeeting);

        assertThrows(
                NotAssignedException.class, () -> addressBook.unassignVolunteerFromEvent(volunteerAlice, eventMeeting)
        );
    }

    @Test
    public void equals_sameAddressBook_returnsTrue() {
        AddressBook anotherAddressBook = new AddressBook();
        assertEquals(addressBook, anotherAddressBook);
    }

    @Test
    public void equals_differentAddressBooks_returnsFalse() {
        AddressBook differentAddressBook = new AddressBook();
        differentAddressBook.addVolunteer(volunteerAlice);
        assertFalse(addressBook.equals(differentAddressBook));
    }

    @Test
    public void hashCode_consistentWithEquals() {
        AddressBook anotherAddressBook = new AddressBook();
        assertEquals(addressBook.hashCode(), anotherAddressBook.hashCode());

        addressBook.addVolunteer(volunteerAlice);
        assertFalse(addressBook.hashCode() == anotherAddressBook.hashCode());
    }

    @Test
    public void removeEvent_unassignsEventFromVolunteers() {
        // Set up associations
        addressBook.addVolunteer(volunteerAlice);
        addressBook.addVolunteer(volunteerBob);
        addressBook.addEvent(eventMeeting);
        addressBook.assignVolunteerToEvent(volunteerAlice, eventMeeting);
        addressBook.assignVolunteerToEvent(volunteerBob, eventMeeting);

        // Remove the event and check if it is unassigned from volunteers
        addressBook.removeEvent(eventMeeting);

        // Verify that the event was removed from AddressBook
        assertTrue(!addressBook.getEventList().contains(eventMeeting));

        // Verify that volunteers no longer have the event assigned
        assertTrue(!volunteerAlice.getEvents().contains(eventMeeting.getName()));
        assertTrue(!volunteerBob.getEvents().contains(eventMeeting.getName()));
    }

    @Test
    public void removeVolunteer_unassignsVolunteerFromAllEvents() {
        // Set up associations
        addressBook.addVolunteer(volunteerAlice);
        addressBook.addEvent(eventMeeting);
        addressBook.addEvent(eventWorkshop);
        addressBook.assignVolunteerToEvent(volunteerAlice, eventMeeting);

        // Remove volunteer and check if unassigned from events
        addressBook.removeVolunteer(volunteerAlice);

        // Verify that the volunteer was removed from AddressBook
        assertTrue(!addressBook.getVolunteerList().contains(volunteerAlice.getName().toString()));

        // Verify that events no longer have the volunteer assigned
        assertTrue(!eventMeeting.getVolunteers().contains(volunteerAlice.getName().toString()));
    }

    @Test
    public void constructor_withReadOnlyAddressBook_copiesData() {
        // Prepare initial data for ReadOnlyAddressBook
        AddressBook original = new AddressBook();
        original.addVolunteer(volunteerAlice);
        original.addEvent(eventMeeting);
        original.assignVolunteerToEvent(volunteerAlice, eventMeeting);

        // Create new AddressBook using original as input
        AddressBook copiedAddressBook = new AddressBook(original);

        // Verify that data is correctly copied
        assertEquals(original.getVolunteerList(), copiedAddressBook.getVolunteerList());
        assertEquals(original.getEventList(), copiedAddressBook.getEventList());

        // Ensure associations are preserved
        assertTrue(
                copiedAddressBook.getEventList().get(0).getVolunteers().contains(volunteerAlice.getName().toString())
        );
    }
}

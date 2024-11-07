package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.exceptions.DuplicateAssignException;
import seedu.address.model.exceptions.OverlappingAssignException;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.VolunteerBuilder;

public class ModelManagerTest {

    private ModelManager modelManager;

    private Volunteer volunteerAlice;
    private Event eventMeeting;

    @BeforeEach
    public void setUp() {
        modelManager = new ModelManager();
        volunteerAlice = new VolunteerBuilder().withName("Alice").withAvailableDate("2024-12-15").build();
        eventMeeting = new EventBuilder().withEventName("Meeting").build();
    }

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void addVolunteer_addsVolunteer() {
        modelManager.addVolunteer(volunteerAlice);
        assertTrue(modelManager.getAddressBook().getVolunteerList().contains(volunteerAlice));
    }

    @Test
    public void addEvent_addsEvent() {
        modelManager.addEvent(eventMeeting);
        assertTrue(modelManager.getAddressBook().getEventList().contains(eventMeeting));
    }

    @Test
    public void deleteVolunteer_removesVolunteer() {
        modelManager.addVolunteer(volunteerAlice);
        modelManager.deleteVolunteer(volunteerAlice);
        assertTrue(!modelManager.getAddressBook().getVolunteerList().contains(volunteerAlice));
    }

    @Test
    public void deleteEvent_removesEvent() {
        modelManager.addEvent(eventMeeting);
        modelManager.deleteEvent(eventMeeting);
        assertTrue(!modelManager.getAddressBook().getEventList().contains(eventMeeting));
    }

    @Test
    public void assignVolunteerToEvent_successfulAssignment()
            throws DuplicateAssignException, OverlappingAssignException {
        modelManager.addVolunteer(volunteerAlice);
        modelManager.addEvent(eventMeeting);
        modelManager.assignVolunteerToEvent(volunteerAlice, eventMeeting);
        assertTrue(eventMeeting.getVolunteers().contains(volunteerAlice.getName().toString()));
        assertTrue(volunteerAlice.getEvents().contains(eventMeeting.getName().toString()));
    }

    @Test
    public void unassignVolunteerFromEvent_successfulUnassignment()
            throws CommandException, DuplicateAssignException, OverlappingAssignException {
        modelManager.addVolunteer(volunteerAlice);
        modelManager.addEvent(eventMeeting);
        modelManager.assignVolunteerToEvent(volunteerAlice, eventMeeting);
        modelManager.unassignVolunteerFromEvent(volunteerAlice, eventMeeting);
        assertTrue(!eventMeeting.getVolunteers().contains(volunteerAlice.getName()));
        assertTrue(!volunteerAlice.getEvents().contains(eventMeeting.getName()));
    }

    @Test
    public void viewEvent_filtersVolunteerListByEvent() {
        modelManager.addVolunteer(volunteerAlice);
        modelManager.addEvent(eventMeeting);
        modelManager.assignVolunteerToEvent(volunteerAlice, eventMeeting);

        modelManager.viewEvent(eventMeeting);
        ObservableList<Volunteer> filteredVolunteers = modelManager.getFilteredVolunteerList();
        assertEquals(1, filteredVolunteers.size());
        assertEquals(volunteerAlice, filteredVolunteers.get(0));
    }

    @Test
    public void viewVolunteer_filtersEventListByVolunteer() {
        modelManager.addVolunteer(volunteerAlice);
        modelManager.addEvent(eventMeeting);
        modelManager.assignVolunteerToEvent(volunteerAlice, eventMeeting);

        modelManager.viewVolunteer(volunteerAlice);
        ObservableList<Event> filteredEvents = modelManager.getFilteredEventList();
        assertEquals(1, filteredEvents.size());
        assertEquals(eventMeeting, filteredEvents.get(0));
    }

    @Test
    public void resetDisplayLists_resetsFilters() {
        modelManager.addVolunteer(volunteerAlice);
        modelManager.addEvent(eventMeeting);
        modelManager.viewEvent(eventMeeting); // Apply filter
        modelManager.resetDisplayLists(); // Reset filters

        ObservableList<Volunteer> filteredVolunteers = modelManager.getFilteredVolunteerList();
        ObservableList<Event> filteredEvents = modelManager.getFilteredEventList();
        assertTrue(filteredVolunteers.contains(volunteerAlice));
        assertTrue(filteredEvents.contains(eventMeeting));
    }

    @Test
    public void updateFilteredVolunteerList_appliesFilter() {
        modelManager.addVolunteer(volunteerAlice);
        Volunteer volunteerBob = new VolunteerBuilder().withName("Bob").build();
        modelManager.addVolunteer(volunteerBob);

        Predicate<Volunteer> nameIsAlice = v -> v.getName().toString().equals("Alice");
        modelManager.updateFilteredVolunteerList(nameIsAlice);

        ObservableList<Volunteer> filteredVolunteers = modelManager.getFilteredVolunteerList();
        assertEquals(1, filteredVolunteers.size());
        assertEquals(volunteerAlice, filteredVolunteers.get(0));
    }

    @Test
    public void updateFilteredEventList_appliesFilter() {
        modelManager.addEvent(eventMeeting);
        Event eventWorkshop = new EventBuilder().withEventName("Workshop").build();
        modelManager.addEvent(eventWorkshop);

        Predicate<Event> nameIsMeeting = e -> e.getName().toString().equals("Meeting");
        modelManager.updateFilteredEventList(nameIsMeeting);

        ObservableList<Event> filteredEvents = modelManager.getFilteredEventList();
        assertEquals(1, filteredEvents.size());
        assertEquals(eventMeeting, filteredEvents.get(0));
    }

    @Test
    public void setVolunteer_replacesVolunteerInAddressBook() {
        modelManager.addVolunteer(volunteerAlice);
        Volunteer editedAlice = new VolunteerBuilder(volunteerAlice).withName("Alice Edited").build();

        modelManager.setVolunteer(volunteerAlice, editedAlice);

        assertTrue(modelManager.getAddressBook().getVolunteerList().contains(editedAlice));
        assertFalse(modelManager.getAddressBook().getVolunteerList().contains(volunteerAlice));
    }

    @Test
    public void getVolunteer_withExistingId_returnsVolunteer() {
        modelManager.addVolunteer(volunteerAlice);
        int aliceId = volunteerAlice.getId();

        Volunteer retrievedVolunteer = modelManager.getVolunteer(aliceId);

        assertEquals(volunteerAlice, retrievedVolunteer);
    }

    @Test
    public void getVolunteer_withNonExistentId_returnsNull() {
        int nonExistentId = -1;
        Volunteer retrievedVolunteer = modelManager.getVolunteer(nonExistentId);

        assertNull(retrievedVolunteer);
    }

    @Test
    public void setAddressBook_replacesCurrentAddressBook() {
        AddressBook newAddressBook = new AddressBook();
        modelManager.setAddressBook(newAddressBook);
        assertEquals(newAddressBook, modelManager.getAddressBook());
    }

    @Test
    public void hasVolunteer_volunteerInAddressBook_returnsTrue() {
        modelManager.addVolunteer(volunteerAlice);
        assertTrue(modelManager.hasVolunteer(volunteerAlice));
    }

    @Test
    public void hasVolunteer_volunteerNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasVolunteer(volunteerAlice));
    }

    @Test
    public void hasEvent_eventInAddressBook_returnsTrue() {
        modelManager.addEvent(eventMeeting);
        assertTrue(modelManager.hasEvent(eventMeeting));
    }

    @Test
    public void hasEvent_eventNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasEvent(eventMeeting));
    }

    @Test
    public void getEvent_withExistingId_returnsEvent() {
        modelManager.addEvent(eventMeeting);
        int meetingId = eventMeeting.getId();

        Event retrievedEvent = modelManager.getEvent(meetingId);

        assertEquals(eventMeeting, retrievedEvent);
    }

    @Test
    public void getEvent_withNonExistentId_returnsNull() {
        int nonExistentId = -1;
        Event retrievedEvent = modelManager.getEvent(nonExistentId);

        assertNull(retrievedEvent);
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        assertTrue(modelManager.equals(modelManager));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertFalse(modelManager.equals(null));
    }

    @Test
    public void equals_differentObject_returnsFalse() {
        assertFalse(modelManager.equals(new Object()));
    }

    @Test
    public void equals_sameAttributes_returnsTrue() {
        ModelManager modelManagerCopy = new ModelManager(modelManager.getAddressBook(), modelManager.getUserPrefs());
        assertTrue(modelManager.equals(modelManagerCopy));
    }

    @Test
    public void equals_differentUserPrefs_returnsFalse() {
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setGuiSettings(new GuiSettings(2, 2, 2, 2));
        ModelManager differentModelManager = new ModelManager(modelManager.getAddressBook(), differentUserPrefs);
        assertFalse(modelManager.equals(differentModelManager));
    }
}

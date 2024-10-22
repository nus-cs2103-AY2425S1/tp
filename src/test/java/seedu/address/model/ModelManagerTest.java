package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUYERS_ONLY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SELLERS_ONLY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.CARL;
import static seedu.address.testutil.TypicalClients.DANIEL;
import static seedu.address.testutil.TypicalMeetings.MEETING_ADMIRALTY;
import static seedu.address.testutil.TypicalMeetings.MEETING_BEDOK;
import static seedu.address.testutil.TypicalMeetings.MEETING_CLEMENTI;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalProperty.BEDOK;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.client.Buyer;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Seller;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.ClientBookBuilder;
import seedu.address.testutil.MeetingBookBuilder;


public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(new ClientBook(), new ClientBook(modelManager.getClientBook()));
        assertEquals(new PropertyBook(), new PropertyBook(modelManager.getPropertyBook()));
        assertEquals(new MeetingBook(), new MeetingBook(modelManager.getMeetingBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setClientBookFilePath(Paths.get("client/book/file/path"));
        userPrefs.setPropertyBookFilePath(Paths.get("property/book/file/path"));
        userPrefs.setMeetingBookFilePath(Paths.get("meeting/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        userPrefs.setClientBookFilePath(Paths.get("new/client/book/file/path"));
        userPrefs.setPropertyBookFilePath(Paths.get("new/property/book/file/path"));
        userPrefs.setMeetingBookFilePath(Paths.get("new/meeting/book/file/path"));
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
    public void setClientBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setClientBookFilePath(null));
    }

    @Test
    public void setClientBookFilePath_validPath_setsClientBookFilePath() {
        Path path = Paths.get("data/clientbook.json");
        modelManager.setClientBookFilePath(path);
        assertEquals(path, modelManager.getClientBookFilePath());
    }

    @Test
    public void setMeetingBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setMeetingBookFilePath(null));
    }

    @Test
    public void setMeetingBookFilePath_validPath_setsMeetingBookFilePath() {
        Path path = Paths.get("data/meetingbook.json");
        modelManager.setMeetingBookFilePath(path);
        assertEquals(path, modelManager.getMeetingBookFilePath());
    }

    // ==================== AddressBook Related Tests ====================

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    // ==================== ClientBook Related Tests ====================

    @Test
    public void hasClient_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasClient(null));
    }

    @Test
    public void hasClient_clientNotInClientBook_returnsFalse() {
        assertFalse(modelManager.hasClient(CARL));
    }

    @Test
    public void hasClient_clientInClientBook_returnsTrue() {
        modelManager.addClient(CARL);
        assertTrue(modelManager.hasClient(CARL));
    }

    @Test
    public void getFilteredClientList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredClientList().remove(0));
    }

    // ==================== PropertyBook Related Tests ====================
    @Test
    public void setPropertyBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPropertyBookFilePath(null));
    }

    @Test
    public void setPropertyBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("data/propertybook.json");
        modelManager.setPropertyBookFilePath(path);
        assertEquals(path, modelManager.getPropertyBookFilePath());
    }

    @Test
    public void hasProperty_propertyInAddressBook_returnsTrue() {
        modelManager.addProperty(BEDOK);
        assertTrue(modelManager.hasProperty(BEDOK));
    }

    @Test
    public void getFilteredPropertyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPropertyList().remove(0));
    }

    @Test
    public void hasProperty_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasProperty(null));
    }

    @Test
    public void hasProperty_propertyNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasProperty(BEDOK));
    }

    // ==================== MeetingBook Related Tests ====================

    @Test
    public void getFilteredMeetingList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredMeetingList().remove(0));
    }

    @Test
    public void getMeetingBook_returnsCorrectMeetingBook() {
        MeetingBook meetingBook = new MeetingBookBuilder().withMeeting(MEETING_BEDOK)
                .withMeeting(MEETING_CLEMENTI).build();
        modelManager = new ModelManager(new AddressBook(), new UserPrefs(),
                new PropertyBook(), new ClientBook(), meetingBook);
        assertEquals(meetingBook, modelManager.getMeetingBook());
    }

    @Test
    public void deleteMeeting_meetingInMeetingBook_success() {
        MeetingBook meetingBook = new MeetingBookBuilder().withMeeting(MEETING_BEDOK)
                .withMeeting(MEETING_CLEMENTI).build();
        modelManager = new ModelManager(new AddressBook(), new UserPrefs(),
                new PropertyBook(), new ClientBook(), meetingBook);
        modelManager.deleteMeeting(MEETING_BEDOK);

        MeetingBook expectedMeetingBook = new MeetingBookBuilder().withMeeting(MEETING_CLEMENTI).build();
        assertEquals(expectedMeetingBook, modelManager.getMeetingBook());
    }

    @Test
    public void getFilteredMeetingList_returnsCorrectFilteredMeetingList() {
        ObservableList<Meeting> expectedList = FXCollections.observableArrayList(MEETING_BEDOK, MEETING_CLEMENTI);
        MeetingBook meetingBook = new MeetingBookBuilder().withMeeting(MEETING_BEDOK)
                .withMeeting(MEETING_CLEMENTI).build();
        modelManager = new ModelManager(new AddressBook(), new UserPrefs(),
                new PropertyBook(), new ClientBook(), meetingBook);

        assertEquals(expectedList, modelManager.getFilteredMeetingList());
    }

    @Test
    public void updateFilteredMeetingList_predicateUpdatesList() {
        MeetingBook meetingBook = new MeetingBookBuilder().withMeeting(MEETING_BEDOK)
                .withMeeting(MEETING_CLEMENTI).build();
        modelManager = new ModelManager(new AddressBook(), new UserPrefs(),
                new PropertyBook(), new ClientBook(), meetingBook);

        // Apply predicate to only include meetings with the title "Meeting at Bedok"
        Predicate<Meeting> predicate = meeting -> meeting.getMeetingTitle().equals(MEETING_BEDOK.getMeetingTitle());
        modelManager.updateFilteredMeetingList(predicate);

        ObservableList<Meeting> expectedList = FXCollections.observableArrayList(MEETING_BEDOK);
        assertEquals(expectedList, modelManager.getFilteredMeetingList());
    }

    @Test
    public void hasMeeting_nullMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasMeeting(null));
    }

    @Test
    public void hasMeeting_meetingNotInMeetingBook_returnsFalse() {
        assertFalse(modelManager.hasMeeting(MEETING_ADMIRALTY));
    }

    @Test
    public void hasMeeting_meetingInAddressBook_returnsTrue() {
        modelManager.addMeeting(MEETING_ADMIRALTY);
        assertTrue(modelManager.hasMeeting(MEETING_ADMIRALTY));
    }

    // ==================== Equality Tests ====================
    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        ClientBook clientBook = new ClientBookBuilder().withClient(CARL).withClient(DANIEL).build();
        MeetingBook meetingBook = new MeetingBookBuilder().withMeeting(MEETING_BEDOK).withMeeting(MEETING_CLEMENTI)
                .build();

        AddressBook differentAddressBook = new AddressBook();
        PropertyBook propertyBook = new PropertyBook();
        ClientBook differentClientBook = new ClientBook();
        MeetingBook differentMeetingBook = new MeetingBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs, propertyBook, clientBook, meetingBook);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs, propertyBook, clientBook, meetingBook);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs, new PropertyBook(),
                differentClientBook, differentMeetingBook)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs, new PropertyBook(), clientBook,
                meetingBook)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs, new PropertyBook(),
                clientBook, meetingBook)));
    }

    @Test
    public void getDisplayMode_returnsObjectPropertyType() {
        // Call the method
        ObjectProperty<ModelManager.DisplayMode> result = modelManager.getDisplayMode();

        // Assert that the result is an instance of ObjectProperty
        assertTrue(result instanceof ObjectProperty, "Expected result to be an instance of ObjectProperty");
    }

    @Test
    public void getDisplayMode_isObservable() {
        // Call the method
        ObjectProperty<ModelManager.DisplayMode> result = modelManager.getDisplayMode();

        // Assert that the result is an instance of Observable
        assertTrue(result instanceof Observable, "Expected result to be an instance of Observable");
    }

    @Test
    public void setDisplayClients_setsDisplayModeToClients() {
        // Set display mode to CLIENTS
        modelManager.setDisplayClients();
        assertEquals(
                ModelManager.DisplayMode.CLIENTS, modelManager.getDisplayMode().getValue(),
                "Expected display mode to be CLIENTS"
        );
    }

    @Test
    public void setDisplayProperties_setsDisplayModeToProperties() {
        // Set display mode to PROPERTIES
        modelManager.setDisplayProperties();
        assertEquals(
                ModelManager.DisplayMode.PROPERTIES, modelManager.getDisplayMode().getValue(),
                "Expected display mode to be PROPERTIES"
        );
    }

    @Test
    public void setDisplayMeetings_setsDisplayModeToMeetings() {
        // Set display mode to MEETINGS
        modelManager.setDisplayMeetings();
        assertEquals(
                ModelManager.DisplayMode.MEETINGS, modelManager.getDisplayMode().getValue(),
                "Expected display mode to be MEETINGS"
        );
    }


    @Test
    public void testBuyerPredicate() {
        Name nameBuyer = mock(Name.class);
        Phone phoneBuyer = mock(Phone.class);
        Email emailBuyer = mock(Email.class);

        Client mockBuyer = new Buyer(nameBuyer, phoneBuyer, emailBuyer);

        Name nameSeller = mock(Name.class);
        Phone phoneSeller = mock(Phone.class);
        Email emailSeller = mock(Email.class);

        Client mockSeller = new Seller(nameSeller, phoneSeller, emailSeller);

        assertTrue(PREDICATE_SHOW_ALL_BUYERS_ONLY.test(mockBuyer), "Buyer should pass the buyer predicate");

        // Predicate should return false for  (when client is a buyer)
        assertFalse(
                PREDICATE_SHOW_ALL_BUYERS_ONLY.test(mockSeller),
                "Seller should not pass the buyer predicate"
        );
    }

    @Test
    public void testSellerPredicate() {
        // Mocking the buyer and seller details
        Name nameBuyer = mock(Name.class);
        Phone phoneBuyer = mock(Phone.class);
        Email emailBuyer = mock(Email.class);

        Client mockBuyer = new Buyer(nameBuyer, phoneBuyer, emailBuyer);

        Name nameSeller = mock(Name.class);
        Phone phoneSeller = mock(Phone.class);
        Email emailSeller = mock(Email.class);

        Client mockSeller = new Seller(nameSeller, phoneSeller, emailSeller);

        // Predicate should return true for sellers
        assertTrue(PREDICATE_SHOW_ALL_SELLERS_ONLY.test(mockSeller), "Seller should pass the seller predicate");

        // Predicate should return false for buyers (when client is a buyer)
        assertFalse(
                PREDICATE_SHOW_ALL_SELLERS_ONLY.test(mockBuyer),
                "Buyer should not pass the seller predicate"
        );
    }
}

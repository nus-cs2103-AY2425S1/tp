package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BUYERS_ONLY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SELLERS_ONLY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.CARL;
import static seedu.address.testutil.TypicalClients.DANIEL;
import static seedu.address.testutil.TypicalMeetings.MEETING_ADMIRALTY;
import static seedu.address.testutil.TypicalMeetings.MEETING_BEDOK;
import static seedu.address.testutil.TypicalMeetings.MEETING_CLEMENTI;
import static seedu.address.testutil.TypicalProperty.BEDOK;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.WritableObjectValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.client.Buyer;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.NameWithoutNumber;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Seller;
import seedu.address.model.meeting.Meeting;
import seedu.address.testutil.ClientBookBuilder;
import seedu.address.testutil.MeetingBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
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
        userPrefs.setClientBookFilePath(Paths.get("client/book/file/path"));
        userPrefs.setPropertyBookFilePath(Paths.get("property/book/file/path"));
        userPrefs.setMeetingBookFilePath(Paths.get("meeting/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
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
        modelManager = new ModelManager(new UserPrefs(),
                new PropertyBook(), new ClientBook(), meetingBook);
        assertEquals(meetingBook, modelManager.getMeetingBook());
    }

    @Test
    public void deleteMeeting_meetingInMeetingBook_success() {
        MeetingBook meetingBook = new MeetingBookBuilder().withMeeting(MEETING_BEDOK)
                .withMeeting(MEETING_CLEMENTI).build();
        modelManager = new ModelManager(new UserPrefs(),
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
        modelManager = new ModelManager(new UserPrefs(),
                new PropertyBook(), new ClientBook(), meetingBook);

        assertEquals(expectedList, modelManager.getFilteredMeetingList());
    }

    @Test
    public void updateFilteredMeetingList_predicateUpdatesList() {
        MeetingBook meetingBook = new MeetingBookBuilder().withMeeting(MEETING_BEDOK)
                .withMeeting(MEETING_CLEMENTI).build();
        modelManager = new ModelManager(new UserPrefs(),
                new PropertyBook(), new ClientBook(), meetingBook);

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
        ClientBook clientBook = new ClientBookBuilder().withClient(CARL).withClient(DANIEL).build();
        MeetingBook meetingBook = new MeetingBookBuilder().withMeeting(MEETING_BEDOK).withMeeting(MEETING_CLEMENTI)
                .build();

        PropertyBook propertyBook = new PropertyBook();
        ClientBook differentClientBook = new ClientBook();
        MeetingBook differentMeetingBook = new MeetingBook();
        UserPrefs userPrefs = new UserPrefs();

        modelManager = new ModelManager(userPrefs, propertyBook, clientBook, meetingBook);
        ModelManager modelManagerCopy = new ModelManager(userPrefs, propertyBook, clientBook, meetingBook);
        assertTrue(modelManager.equals(modelManagerCopy));

        assertTrue(modelManager.equals(modelManager));

        assertFalse(modelManager.equals(null));

        assertFalse(modelManager.equals(5));

        assertFalse(modelManager.equals(new ModelManager(userPrefs, new PropertyBook(),
                differentClientBook, differentMeetingBook)));

        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setClientBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(differentUserPrefs, new PropertyBook(),
                clientBook, meetingBook)));
    }

    @Test
    public void getReadOnlyDisplayMode_returnsReadOnlyObjectPropertyType() {
        assertTrue(modelManager.getReadOnlyDisplayMode() instanceof ReadOnlyObjectProperty<?>);
    }

    @Test
    public void getReadOnlyDisplayMode_isObservable() {
        assertTrue(modelManager.getReadOnlyDisplayMode() instanceof Observable);
    }

    @Test
    public void getReadOnlyDisplayMode_isImmutable() {
        ReadOnlyObjectProperty<ModelManager.DisplayMode> displayModeProperty = modelManager.getReadOnlyDisplayMode();
        assertFalse(displayModeProperty instanceof WritableObjectValue);
    }

    @Test
    public void setDisplayClients_setsDisplayModeToClients() {
        modelManager.setDisplayClients();
        assertEquals(ModelManager.DisplayMode.CLIENTS, modelManager.getReadOnlyDisplayMode().getValue());
    }

    @Test
    public void setDisplayProperties_setsDisplayModeToProperties() {
        modelManager.setDisplayProperties();
        assertEquals(ModelManager.DisplayMode.PROPERTIES, modelManager.getReadOnlyDisplayMode().getValue());
    }

    @Test
    public void setDisplayMeetings_setsDisplayModeToMeetings() {
        modelManager.setDisplayMeetings();
        assertEquals(ModelManager.DisplayMode.MEETINGS, modelManager.getReadOnlyDisplayMode().getValue());
    }

    @Test
    public void testBuyerPredicate() {
        NameWithoutNumber nameBuyer = mock(NameWithoutNumber.class);
        Phone phoneBuyer = mock(Phone.class);
        Email emailBuyer = mock(Email.class);

        Client mockBuyer = new Buyer(nameBuyer, phoneBuyer, emailBuyer);

        NameWithoutNumber nameSeller = mock(NameWithoutNumber.class);
        Phone phoneSeller = mock(Phone.class);
        Email emailSeller = mock(Email.class);

        Client mockSeller = new Seller(nameSeller, phoneSeller, emailSeller);

        assertTrue(PREDICATE_SHOW_ALL_BUYERS_ONLY.test(mockBuyer));

        assertFalse(PREDICATE_SHOW_ALL_BUYERS_ONLY.test(mockSeller));
    }

    @Test
    public void testSellerPredicate() {
        NameWithoutNumber nameBuyer = mock(NameWithoutNumber.class);
        Phone phoneBuyer = mock(Phone.class);
        Email emailBuyer = mock(Email.class);

        Client mockBuyer = new Buyer(nameBuyer, phoneBuyer, emailBuyer);

        NameWithoutNumber nameSeller = mock(NameWithoutNumber.class);
        Phone phoneSeller = mock(Phone.class);
        Email emailSeller = mock(Email.class);

        Client mockSeller = new Seller(nameSeller, phoneSeller, emailSeller);

        assertTrue(PREDICATE_SHOW_ALL_SELLERS_ONLY.test(mockSeller));

        assertFalse(PREDICATE_SHOW_ALL_SELLERS_ONLY.test(mockBuyer));
    }
}

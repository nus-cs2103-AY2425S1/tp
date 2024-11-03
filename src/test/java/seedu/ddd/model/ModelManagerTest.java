package seedu.ddd.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.model.Model.PREDICATE_SHOW_ALL_CONTACTS;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.ddd.testutil.contact.TypicalContacts.ALICE;
import static seedu.ddd.testutil.contact.TypicalContacts.BENSON;
import static seedu.ddd.testutil.contact.TypicalContacts.CARL;
import static seedu.ddd.testutil.contact.TypicalContacts.DANIEL;
import static seedu.ddd.testutil.contact.TypicalContacts.ELLE;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_CLIENT;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_VENDOR;
import static seedu.ddd.testutil.event.TypicalEvents.VALID_EVENT;
import static seedu.ddd.testutil.event.TypicalEvents.WEDDING_A;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.ddd.commons.core.GuiSettings;
import seedu.ddd.logic.commands.exceptions.CommandException;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.predicate.NameContainsKeywordsPredicate;
import seedu.ddd.testutil.AddressBookBuilder;
import seedu.ddd.testutil.contact.ClientBuilder;

public class ModelManagerTest {

    private ModelManager modelManager;

    @BeforeEach
    public void setUp() {
        modelManager = new ModelManager();
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
    public void hasContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasContact(null));
    }

    @Test
    public void hasContact_contactNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasContact(ALICE));
    }

    @Test
    public void hasContact_contactInAddressBook_returnsTrue() {
        modelManager.addContact(ALICE);
        assertTrue(modelManager.hasContact(ALICE));
    }

    @Test
    public void hasClientId_validClientId_returnsTrue() {
        modelManager.addContact(ALICE);
        assertTrue(modelManager.hasClientId(ALICE.getId()));
    }

    @Test
    public void hasClientId_invalidClientId_returnsFalse() {
        // empty addressbook
        assertFalse(modelManager.hasClientId(ALICE.getId()));

        // wrong client
        modelManager.addContact(CARL);
        assertFalse(modelManager.hasClientId(ALICE.getId()));

        // wrong contact type
        modelManager.addContact(BENSON);
        assertFalse(modelManager.hasClientId(BENSON.getId()));
    }

    @Test
    public void hasVendorId_validVendorId_returnsTrue() {
        modelManager.addContact(BENSON);
        assertTrue(modelManager.hasVendorId(BENSON.getId()));
    }

    @Test
    public void hasVendorId_invalidVendorId_returnsFalse() {
        // empty addressbook
        assertFalse(modelManager.hasVendorId(BENSON.getId()));

        // wrong vendor
        modelManager.addContact(DANIEL);
        assertFalse(modelManager.hasVendorId(BENSON.getId()));

        // wrong contact type
        modelManager.addContact(ALICE);
        assertFalse(modelManager.hasVendorId(ALICE.getId()));
    }

    @Test
    public void hasEvent_validEvent_returnsTrue() {
        modelManager = new ModelManager(getTypicalAddressBook(), modelManager.getUserPrefs());
        assertTrue(modelManager.hasEvent(WEDDING_A));
    }

    @Test
    public void hasEvent_invalidEvent_returnsFalse() {
        // empty addressbook
        assertFalse(modelManager.hasEvent(WEDDING_A));

        modelManager = new ModelManager(getTypicalAddressBook(), modelManager.getUserPrefs());
        assertTrue(modelManager.hasEvent(WEDDING_A));
        assertFalse(modelManager.hasEvent(VALID_EVENT));
    }

    @Test
    public void getFilteredContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredContactList().remove(0));
    }

    @Test
    public void addContact_validContact_modifyDisplayedLists() {
        Model testModelManager = new ModelManager();
        testModelManager.addContact(VALID_CLIENT);
        assertTrue(testModelManager.getDisplayedList().contains(VALID_CLIENT));

        testModelManager.addContact(VALID_VENDOR);
        testModelManager.addEvent(VALID_EVENT); // when an event is added, displayedList should display events
        testModelManager.addContact(ALICE); // when a new contact is added, displayedList should display contacts again
        assertTrue(testModelManager.getDisplayedList().contains(VALID_CLIENT));
        assertTrue(testModelManager.getDisplayedList().contains(ALICE));
    }

    @Test
    public void deleteContact_validContact_modifyDisplayedLists() throws CommandException {
        Model testModelManager = new ModelManager();
        testModelManager.addContact(VALID_CLIENT);
        testModelManager.deleteContact(VALID_CLIENT);
        assertFalse(testModelManager.getDisplayedList().contains(VALID_CLIENT));

        testModelManager.addContact(VALID_CLIENT);
        testModelManager.addContact(VALID_VENDOR);
        Client clientCopy = new ClientBuilder(ALICE).build();
        testModelManager.addContact(clientCopy);
        // when an event is added, displayedList should display events
        testModelManager.addEvent(VALID_EVENT);
        // when a contact is deleted, displayedList should display contacts again
        testModelManager.deleteContact(clientCopy);
        assertFalse(testModelManager.getDisplayedList().contains(ALICE));
        assertTrue(testModelManager.getDisplayedList().contains(VALID_VENDOR));
    }

    @Test
    public void setContact_validContact_modifyDisplayedLists() {
        Model testModelManager = new ModelManager();
        testModelManager.addContact(VALID_CLIENT);
        testModelManager.setContact(VALID_CLIENT, ALICE);
        assertTrue(testModelManager.getDisplayedList().contains(ALICE));
        assertFalse(testModelManager.getDisplayedList().contains(VALID_CLIENT));
    }

    @Test
    public void addEvent_validEvent_modifyDisplayedLists() {
        Model testModelManager = new ModelManager();
        testModelManager.addContact(VALID_CLIENT);
        testModelManager.addContact(VALID_VENDOR);
        testModelManager.addEvent(VALID_EVENT);
        assertTrue(testModelManager.getDisplayedList().contains(VALID_EVENT));

        // when a contact is added, displayedList should display contacts
        testModelManager.addContact(ALICE);
        testModelManager.addContact(BENSON);
        testModelManager.addContact(CARL);
        testModelManager.addContact(DANIEL);
        assertTrue(testModelManager.getDisplayedList().contains(ALICE));

        testModelManager.addEvent(WEDDING_A);
        assertTrue(testModelManager.getDisplayedList().contains(WEDDING_A));
    }

    @Test
    public void deleteEvent_validEvent_modifyDisplayedLists() {
        Model testModelManager = new ModelManager();
        testModelManager.addContact(VALID_CLIENT);
        testModelManager.addContact(VALID_VENDOR);
        testModelManager.addEvent(VALID_EVENT);
        assertTrue(testModelManager.getDisplayedList().contains(VALID_EVENT));

        testModelManager.addContact(ALICE);
        testModelManager.addContact(BENSON);
        testModelManager.addContact(CARL);
        testModelManager.addContact(DANIEL);
        testModelManager.addEvent(WEDDING_A);
        assertTrue(testModelManager.getDisplayedList().contains(WEDDING_A));

        testModelManager.addContact(ELLE); // when a contact is added, displayedList should display contacts
        testModelManager.deleteEvent(VALID_EVENT);
        assertTrue(testModelManager.getDisplayedList().contains(WEDDING_A));
    }

    @Test
    public void setEvent_validEvent_modifyDisplayedLists() {
        // TODO: check that modifying filteredEvents modifies displayedList
        // setEvent is not implemented yet
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withContact(ALICE).withContact(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredContactList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}

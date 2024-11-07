package seedu.eventfulnus.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventfulnus.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.eventfulnus.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.eventfulnus.testutil.Assert.assertThrows;
import static seedu.eventfulnus.testutil.TypicalEvents.CHESS_COM_NUSC;
import static seedu.eventfulnus.testutil.TypicalEvents.SQUASH_FASS_YNC;
import static seedu.eventfulnus.testutil.TypicalEvents.VALORANT_COM_FASS;
import static seedu.eventfulnus.testutil.TypicalPersons.ALICE;
import static seedu.eventfulnus.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.eventfulnus.commons.core.GuiSettings;
import seedu.eventfulnus.model.event.EventContainsKeywordsPredicate;
import seedu.eventfulnus.model.person.PersonContainsKeywordsPredicate;
import seedu.eventfulnus.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

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
    public void hasEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasEvent(null));
    }

    @Test
    public void hasEvent_eventNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasEvent(VALORANT_COM_FASS));
    }

    @Test
    public void hasEvent_eventInAddressBook_returnsTrue() {
        modelManager.addEvent(VALORANT_COM_FASS);
        assertTrue(modelManager.hasEvent(VALORANT_COM_FASS));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON)
                .withEvent(CHESS_COM_NUSC).withEvent(SQUASH_FASS_YNC).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertEquals(modelManager, modelManagerCopy);

        // same object -> returns true
        assertEquals(modelManager, modelManager);

        // null -> returns false
        assertNotEquals(null, modelManager);

        // different types -> returns false
        assertNotEquals(5, modelManager);

        // different addressBook -> returns false
        assertNotEquals(modelManager, new ModelManager(differentAddressBook, userPrefs));

        // different filteredList -> returns false
        String[] keywordsPerson = ALICE.getName().toString().split("\\s+");
        modelManager.updateFilteredPersonList(new PersonContainsKeywordsPredicate(Arrays.asList(keywordsPerson)));
        assertNotEquals(modelManager, new ModelManager(addressBook, userPrefs));
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        String[] keywordsEvent = CHESS_COM_NUSC.getName().toString().split("\\s+");
        modelManager.updateFilteredEventList(new EventContainsKeywordsPredicate(Arrays.asList(keywordsEvent)));
        assertNotEquals(modelManager, new ModelManager(addressBook, userPrefs));
        modelManager.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertNotEquals(modelManager, new ModelManager(addressBook, differentUserPrefs));
    }
}

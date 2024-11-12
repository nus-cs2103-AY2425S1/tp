package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalReminders.REMINDER_ALICE;
import static seedu.address.testutil.TypicalReminders.REMINDER_BENSON;
import static seedu.address.testutil.TypicalReminders.REMINDER_GEORGE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.ReminderAddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(new ReminderAddressBook(), new ReminderAddressBook(modelManager.getReminderAddressBook()));
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
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void setReminderAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setReminderAddressBookFilePath(null));
    }

    @Test
    public void setReminderAddressBookFilePath_validPath_setsReminderAddressBookFilePath() {
        Path path = Paths.get("address/reminderBook/file/path");
        modelManager.setReminderAddressBookFilePath(path);
        assertEquals(path, modelManager.getReminderAddressBookFilePath());
    }

    @Test
    public void hasReminder_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasReminder(null));
    }

    @Test
    public void hasReminder_reminderNotInReminderAddressBook_returnsFalse() {
        assertFalse(modelManager.hasReminder(REMINDER_GEORGE));
    }

    @Test
    public void hasReminder_reminderInReminderAddressBook_returnsTrue() {
        modelManager.addReminderToBook(REMINDER_ALICE);
        assertTrue(modelManager.hasReminder(REMINDER_ALICE));
    }

    @Test
    public void getSortedReminderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getSortedReminderList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();
        ReminderAddressBook reminderAddressBook = new ReminderAddressBook();
        ReminderAddressBook differentReminderAddressBook = new ReminderAddressBookBuilder()
                .withReminder(REMINDER_ALICE)
                .withReminder(REMINDER_BENSON)
                .build();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs, reminderAddressBook);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs, reminderAddressBook);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs, reminderAddressBook)));

        // different reminderaddressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs, differentReminderAddressBook)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs, reminderAddressBook)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));

        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs, reminderAddressBook)));

        // same userPrefs but different sortPreference -> return false
        UserPrefs differentUserPrefs2 = new UserPrefs();
        differentUserPrefs2.setGuiSettings(userPrefs.getGuiSettings());
        differentUserPrefs2.setAddressBookFilePath(userPrefs.getAddressBookFilePath());
        differentUserPrefs2.setSortPreference(new SortPreference("recent"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs2, reminderAddressBook)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs, reminderAddressBook)));
    }
}

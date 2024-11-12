package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @BeforeEach
    public void setUp() {
        modelManager.setArchivedListMode(false);
    }

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
        assertEquals(new ArchivedAddressBook(), new ArchivedAddressBook(modelManager.getArchivedAddressBook()));
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
    public void sortByComparator_sortsAndSaves() {
        modelManager.addPerson(GEORGE);
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BENSON);

        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(ALICE)
                .withPerson(BENSON).withPerson(GEORGE).build();

        Comparator<Person> comparatorByName = Comparator.comparing(person -> person.getName().fullName);

        modelManager.sortByComparator(comparatorByName);

        // Verify that sorting also modifies and saves to the underlying address book
        assertEquals(expectedAddressBook, modelManager.getAddressBook());
    }

    @Test
    public void sortByComparator_afterFindCommand_doesNotDeleteContacts() {
        // Add multiple contacts
        modelManager.addPerson(GEORGE);
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BENSON);
        modelManager.setArchivedListMode(false);
        // Apply a filter to show only contacts with "George" in the name
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Arrays.asList("George"));
        modelManager.updateFilteredPersonList(predicate);

        // Check that the filtered list contains only GEORGE
        assertEquals(1, modelManager.getFilteredPersonList().size());
        assertTrue(modelManager.getFilteredPersonList().contains(GEORGE));

        // Sort the list by name
        Comparator<Person> comparatorByName = Comparator.comparing(person -> person.getName().fullName);
        modelManager.sortByComparator(comparatorByName);

        // After sorting, ensure all contacts are still in the address book
        AddressBook expectedAddressBook = new AddressBookBuilder()
                .withPerson(ALICE)
                .withPerson(BENSON)
                .withPerson(GEORGE)
                .build();
        assertEquals(expectedAddressBook, modelManager.getAddressBook());

        // Restore the full person list after the sorting
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertEquals(3, modelManager.getFilteredPersonList().size()); // Ensure no persons were deleted
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();
        ArchivedAddressBook archivedAddressBook = new ArchivedAddressBook();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs, new ArchivedAddressBook());
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs, archivedAddressBook);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs, archivedAddressBook)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs, archivedAddressBook)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs, archivedAddressBook)));

        // different archivedAddressBook -> returns false
        Person personToArchive = addressBook.getPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        modelManager.addArchivedPerson(personToArchive);
        modelManager.deletePerson(personToArchive);
        assertFalse(modelManager.equals(new ModelManager(modelManager.getAddressBook(),
                userPrefs, archivedAddressBook)));

        // different filteredArchivePersonList -> returns false
        modelManager.setIsArchivedList(true);
        assertFalse(modelManager.equals(new ModelManager(modelManager.getAddressBook(),
                userPrefs, archivedAddressBook)));
    }
}

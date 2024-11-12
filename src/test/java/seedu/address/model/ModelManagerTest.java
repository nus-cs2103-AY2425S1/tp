package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventManager;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

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
    public void hasPhone_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPhone(null));
    }

    @Test
    public void hasPhone_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPhone(ALICE));
    }

    @Test
    public void hasPhone_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPhone(ALICE));
    }

    @Test
    public void hasEmail_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasEmail(null));
    }

    @Test
    public void hasEmail_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPhone(ALICE));
    }

    @Test
    public void hasEmail_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasEmail(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void searchModeProperty() {
        assertEquals(false, modelManager.searchModeProperty().get());
    }

    @Test
    public void getAllPersons() {
        assertEquals(modelManager.getAddressBook().getPersonList(), modelManager.getAllPersons());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();
        EventManager eventManager = new EventManager();
        EventManager differentEventManager = new EventManager();
        differentEventManager.addEvent(new Event("Different Event"));

        // same values -> returns true
        modelManager = new ModelManager(addressBook, eventManager, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, eventManager, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, eventManager, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, eventManager, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, eventManager, differentUserPrefs)));

        // same eventManager -> returns true
        ModelManager sameEventManagerModel = new ModelManager(addressBook, eventManager, userPrefs);
        assertTrue(modelManager.equals(sameEventManagerModel));

        // different eventManager -> returns false
        ModelManager differentEventManagerModel = new ModelManager(addressBook, differentEventManager, userPrefs);
        assertFalse(modelManager.equals(differentEventManagerModel));
    }

    @Test
    public void getExcludedPersons_empty() {
        assertEquals(modelManager.getExcludedPersons(), new HashSet<>());
    }

    @Test
    public void getExcludedPersons_notEmpty() {
        modelManager.addPerson(ALICE);
        modelManager.excludePerson(ALICE);
        assertEquals(modelManager.getExcludedPersons(), new HashSet<>(Arrays.asList(ALICE)));
    }
    @Test
    public void excludePerson_personInAddressBook() {
        modelManager.addPerson(ALICE);
        try {
            modelManager.excludePerson(ALICE);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Test
    public void excludePerson_personNotInAddressBook() {
        assertThrows(PersonNotFoundException.class, () -> modelManager.excludePerson(ALICE));
    }

    @Test
    public void testGetContactListForFindEvent() {
        ArrayList<Person> persons = new ArrayList<Person>();
        persons.add(ALICE);

        modelManager.setContactListForFindEvent(persons);

        ObservableList<Person> expectedList = FXCollections.observableList(persons);
        assertEquals(expectedList, modelManager.getContactListForFindEvent());
    }
}

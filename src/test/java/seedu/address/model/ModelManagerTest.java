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
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.event.Event;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

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
    public void addPerson_personWithInvalidId_throwsAssertionError() {
        boolean assertionsEnabled = false;
        assert assertionsEnabled = true;

        ModelManager modelManager = new ModelManager();
        Person invalidPerson = new PersonBuilder().withId(-1).build();
        assertThrows(AssertionError.class, () -> modelManager.addPerson(invalidPerson));
    }

    @Test
    public void setPerson_personWithInvalidId_throwsAssertionError() {
        boolean assertionsEnabled = false;
        assert assertionsEnabled = true;

        ModelManager modelManager = new ModelManager();
        Person validPerson = new PersonBuilder().withId(1).build();
        Person invalidPerson = new PersonBuilder().withId(-1).build();
        modelManager.addPerson(validPerson);
        assertThrows(AssertionError.class, () -> modelManager.setPerson(validPerson, invalidPerson));
    }

    @Test
    public void findPersonsWithName_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.findPersonsWithName(null));
    }

    @Test
    public void findPersonsWithName_personNotInAddressBook_returnsEmptyList() {
        assertEquals(modelManager.findPersonsWithName(ALICE.getName()), new ArrayList<>());
    }

    @Test
    public void findPersonsWithName_personInAddressBook_returnsPersonList() {
        modelManager.addPerson(ALICE);
        List<Person> resultList = new ArrayList<>();
        resultList.add(ALICE);
        assertEquals(modelManager.findPersonsWithName(ALICE.getName()), resultList);
    }

    @Test
    public void findPersonsWithName_personWithLowerCasedNameInAddressBook_returnsPersonList() {
        modelManager.addPerson(ALICE);
        List<Person> resultList = new ArrayList<>();
        resultList.add(ALICE);
        Name lowerCasedName = new Name(ALICE.getName().toString().toLowerCase());
        assertEquals(modelManager.findPersonsWithName(lowerCasedName), resultList);
    }

    @Test
    public void findPersonsWithName_personWithUpperCasedNameInAddressBook_returnsPersonList() {
        modelManager.addPerson(ALICE);
        List<Person> resultList = new ArrayList<>();
        resultList.add(ALICE);
        Name upperCasedName = new Name(ALICE.getName().toString().toUpperCase());
        assertEquals(modelManager.findPersonsWithName(upperCasedName), resultList);
    }

    @Test
    public void findPersonsWithName_personWithPartOfNameInAddressBook_returnsEmptyList() {
        modelManager.addPerson(ALICE);
        List<Person> resultList = new ArrayList<>();
        String nameString = ALICE.getName().toString();
        Name partOfName = new Name(nameString.substring(0, nameString.length() - 1));
        assertEquals(modelManager.findPersonsWithName(partOfName), resultList);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasEvent(null));
    }

    @Test
    public void hasEvent_eventNotInAddressBook_returnsFalse() {
        Event event = new EventBuilder().withEventId(1).build();
        assertFalse(modelManager.hasEvent(event));
    }

    @Test
    public void hasEvent_eventInAddressBook_returnsTrue() {
        Event event = new EventBuilder().withEventId(1).build();
        modelManager.addEvent(event);
        assertTrue(modelManager.hasEvent(event));
    }

    @Test
    public void addEvent_eventWithInvalidId_throwsAssertionError() {
        boolean assertionsEnabled = false;
        assert assertionsEnabled = true;

        ModelManager modelManager = new ModelManager();
        Event invalidEvent = new EventBuilder().withEventId(-1).build();
        assertThrows(AssertionError.class, () -> modelManager.addEvent(invalidEvent));
    }

    @Test
    public void setEvent_nullEvent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setEvent(null, null));
    }

    @Test
    public void hasEventById_eventNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasEventById(1));
    }

    @Test
    public void hasEventById_eventInAddressBook_returnsTrue() {
        Event event = new EventBuilder().withEventId(1).build();
        modelManager.addEvent(event);
        assertTrue(modelManager.hasEventById(1));
    }

    @Test
    public void getEventById_eventNotInAddressBook_returnsNull() {
        assertEquals(null, modelManager.getEventById(1));
    }

    @Test
    public void getEventById_eventInAddressBook_returnsEvent() {
        Event event = new EventBuilder().withEventId(1).build();
        modelManager.addEvent(event);
        assertEquals(event, modelManager.getEventById(1));
    }

    @Test
    public void getFilteredEventList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredEventList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
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
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}

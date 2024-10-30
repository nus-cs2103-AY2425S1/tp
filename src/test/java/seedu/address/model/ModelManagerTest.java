package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
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
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
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
    @Test
    public void equals_filteredPersonsListSameOrder_returnsTrue() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        ModelManager modelManagerCopy = new ModelManager(addressBook, new UserPrefs());

        // Set both model managers to the same filtered list order
        modelManager.updateFilteredPersonList(person -> person.equals(ALICE) || person.equals(BENSON));
        modelManagerCopy.updateFilteredPersonList(person -> person.equals(ALICE) || person.equals(BENSON));

        // Expect both to be equal
        assertTrue(modelManager.equals(modelManagerCopy));
    }

    @Test
    public void equals_filteredPersonsListDifferentOrder_returnsTrue() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        ModelManager modelManagerCopy = new ModelManager(addressBook, new UserPrefs());

        // Set model managers to have the same persons but in different orders in filtered lists
        modelManager.updateFilteredPersonList(person -> person.equals(ALICE) || person.equals(BENSON));
        modelManagerCopy.updateFilteredPersonList(person -> person.equals(BENSON) || person.equals(ALICE));

        // Expect both to be equal because the contents are the same, regardless of order
        assertTrue(modelManager.equals(modelManagerCopy));
    }

    @Test
    public void equals_filteredPersonsListSubset_returnsFalse() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        ModelManager modelManagerCopy = new ModelManager(addressBook, new UserPrefs());

        // ModelManager has both ALICE and BENSON, ModelManagerCopy only has ALICE
        modelManager.updateFilteredPersonList(person -> person.equals(ALICE) || person.equals(BENSON));
        modelManagerCopy.updateFilteredPersonList(person -> person.equals(ALICE));

        // Expect them to be unequal as one filtered list is a subset of the other
        assertFalse(modelManager.equals(modelManagerCopy));
    }

    @Test
    public void equals_filteredPersonsListDifferentContents_returnsFalse() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).withPerson(CARL)
                .build();
        modelManager = new ModelManager(addressBook, new UserPrefs());
        ModelManager modelManagerCopy = new ModelManager(addressBook, new UserPrefs());

        // ModelManager has ALICE and BENSON in filtered list, ModelManagerCopy has only CARL
        modelManager.updateFilteredPersonList(person -> person.equals(ALICE) || person.equals(BENSON));
        modelManagerCopy.updateFilteredPersonList(person -> person.equals(CARL));

        // Expect them to be unequal as they have different filtered list contents
        assertFalse(modelManager.equals(modelManagerCopy));
    }
}

package spleetwaise.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import spleetwaise.address.model.person.NameContainsKeywordsPredicate;
import spleetwaise.address.testutil.AddressBookBuilder;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.commons.testutil.Assert;

public class ModelManagerTest {

    private AddressBookModelManager modelManager = new AddressBookModelManager();

    @Test
    public void constructor() {
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }


    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(TypicalPersons.ALICE);
        assertTrue(modelManager.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void getPersonById() {
        modelManager.addPerson(TypicalPersons.ALICE);
        assertEquals(Optional.of(TypicalPersons.ALICE), modelManager.getPersonById(TypicalPersons.ALICE.getId()));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(TypicalPersons.ALICE).withPerson(
                TypicalPersons.BENSON).build();
        AddressBook differentAddressBook = new AddressBook();

        // same values -> returns true
        modelManager = new AddressBookModelManager(addressBook);
        AddressBookModelManager modelManagerCopy = new AddressBookModelManager(addressBook);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new AddressBookModelManager(differentAddressBook)));

        // different filteredList -> returns false
        String[] keywords = TypicalPersons.ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new AddressBookModelManager(addressBook)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(AddressBookModel.PREDICATE_SHOW_ALL_PERSONS);
    }
}

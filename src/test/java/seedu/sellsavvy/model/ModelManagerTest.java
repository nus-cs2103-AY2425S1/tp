package seedu.sellsavvy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.personcommands.PersonCommandTestUtil.VALID_NAME_BOB;
import static seedu.sellsavvy.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.sellsavvy.testutil.Assert.assertThrows;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.sellsavvy.testutil.TypicalOrders.ABACUS;
import static seedu.sellsavvy.testutil.TypicalOrders.BOTTLE;
import static seedu.sellsavvy.testutil.TypicalPersons.ALICE;
import static seedu.sellsavvy.testutil.TypicalPersons.BENSON;
import static seedu.sellsavvy.testutil.TypicalPersons.BOB;
import static seedu.sellsavvy.testutil.TypicalPersons.GEORGE;
import static seedu.sellsavvy.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.commons.core.GuiSettings;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.exceptions.OrderNotFoundException;
import seedu.sellsavvy.model.person.NameContainsKeywordsPredicate;
import seedu.sellsavvy.model.person.Person;
import seedu.sellsavvy.model.person.exceptions.PersonNotFoundException;
import seedu.sellsavvy.testutil.AddressBookBuilder;
import seedu.sellsavvy.testutil.OrderBuilder;
import seedu.sellsavvy.testutil.PersonBuilder;

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
    public void getSelectedPerson_innerContent_isNullInitially() {
        assertNotNull(modelManager.getSelectedPerson());
        //ensures that when first initiated no person's order will be displayed
        assertNull(modelManager.getSelectedPerson().get());
        assertNull(modelManager.getSelectedPerson2());
        assertNull(modelManager.getFilteredOrderList());
    }

    @Test
    public void updateSelectedPerson_updateSuccessfully() {
        modelManager.updateSelectedPerson(ALICE);
        assertEquals(modelManager.getSelectedPerson().get(), ALICE);
        assertEquals(modelManager.getSelectedPerson2(), ALICE);
        assertEquals(modelManager.getFilteredOrderList(), ALICE.getFilteredOrderList());
        modelManager.updateSelectedPerson(null);
        assertNull(modelManager.getSelectedPerson().get());
        assertNull(modelManager.getSelectedPerson2());
        assertNull(modelManager.getFilteredOrderList());
    }

    @Test
    public void findEquivalentPerson_returnNullWhenInputIsNull() {
        assertNull(modelManager.findEquivalentPerson(null));
    }

    @Test
    public void findEquivalentPerson_modelContainsEquivalentPerson() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();
        Model modelCopy = model.createCopy();
        Person selectedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person selectedPersonCopy = modelCopy.findEquivalentPerson(selectedPerson);
        assertNotSame(selectedPersonCopy, selectedPerson);
        assertEquals(selectedPersonCopy, selectedPerson);
    }

    @Test
    public void findEquivalentPerson_modelDoesNotContainsEquivalentPerson() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();
        Person selectedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person differentPerson = new PersonBuilder(selectedPerson).withName(VALID_NAME_BOB).build();
        assertThrows(PersonNotFoundException.class, () -> model.findEquivalentPerson(differentPerson));
    }

    @Test
    public void isSelectedPerson_noSelectedPerson() {
        assertTrue(modelManager.isSelectedPerson(null));
    }

    @Test
    public void isSelectedPerson_withSelectedPerson() {
        modelManager.updateSelectedPerson(ALICE);

        // same object -> returns true
        assertTrue(modelManager.isSelectedPerson(ALICE));

        // same values -> returns true
        assertTrue(modelManager.isSelectedPerson(new PersonBuilder(ALICE).build()));

        // different values -> returns false
        assertFalse(modelManager.isSelectedPerson(BOB));

        // null -> returns false
        assertFalse(modelManager.isSelectedPerson(null));
    }

    @Test
    public void setOrder() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();

        // Assertion error thrown when there is no selectedPerson
        assertThrows(AssertionError.class, () -> model.setOrder(ABACUS, BOTTLE));

        Person selectedPerson = model.findEquivalentPerson(GEORGE);
        model.updateSelectedPerson(selectedPerson);

        // OrderNotFoundException are thrown when selectedPerson has no such order
        assertThrows(OrderNotFoundException.class, () -> model.setOrder(new OrderBuilder(BOTTLE).build(), ABACUS));

        // success when selectedPerson has the same order
        Order order = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        model.setOrder(order, BOTTLE);
        assertFalse(model.getFilteredOrderList().contains(order));
        assertTrue(model.getFilteredOrderList().contains(BOTTLE));

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

        // different selectedPerson -> returns false
        modelManager.updateSelectedPerson(ALICE);
        assertFalse(modelManager.equals(modelManagerCopy));
        assertFalse(modelManagerCopy.equals(modelManager));
    }
}

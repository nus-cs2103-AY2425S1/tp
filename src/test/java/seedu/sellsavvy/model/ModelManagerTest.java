package seedu.sellsavvy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_NAME_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.sellsavvy.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;
import static seedu.sellsavvy.testutil.Assert.assertThrows;
import static seedu.sellsavvy.testutil.TypicalCustomers.ALICE;
import static seedu.sellsavvy.testutil.TypicalCustomers.BENSON;
import static seedu.sellsavvy.testutil.TypicalCustomers.BOB;
import static seedu.sellsavvy.testutil.TypicalCustomers.GEORGE;
import static seedu.sellsavvy.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.sellsavvy.testutil.TypicalOrders.ABACUS;
import static seedu.sellsavvy.testutil.TypicalOrders.BOTTLE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.commons.core.GuiSettings;
import seedu.sellsavvy.model.customer.Customer;
import seedu.sellsavvy.model.customer.NameContainsKeywordsPredicate;
import seedu.sellsavvy.model.customer.exceptions.CustomerNotFoundException;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.exceptions.OrderNotFoundException;
import seedu.sellsavvy.testutil.AddressBookBuilder;
import seedu.sellsavvy.testutil.CustomerBuilder;
import seedu.sellsavvy.testutil.OrderBuilder;
import seedu.sellsavvy.testutil.TypicalIndexes;

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
    public void hasCustomer_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasCustomer(null));
    }

    @Test
    public void hasCustomer_customerNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasCustomer(ALICE));
    }

    @Test
    public void hasCustomer_customerInAddressBook_returnsTrue() {
        modelManager.addCustomer(ALICE);
        assertTrue(modelManager.hasCustomer(ALICE));
    }

    @Test
    public void hasSimilarCustomer_onlyIdenticalCustomer_returnsFalse() {
        modelManager.addCustomer(ALICE);
        assertFalse(modelManager.hasSimilarCustomer(ALICE));
    }

    @Test
    public void hasSimilarCustomer_customerWithSameIdentityFieldsInAddressBook_returnsTrue() {
        modelManager.addCustomer(ALICE);
        Customer editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(modelManager.hasSimilarCustomer(editedAlice));
    }

    @Test
    public void hasSimilarCustomer_customerWithSimilarNameInAddressBook_returnsTrue() {
        modelManager.addCustomer(ALICE);
        Customer editedAlice = new CustomerBuilder(ALICE)
                .withName(ALICE.getName()
                        .fullName.toUpperCase())
                .withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(modelManager.hasSimilarCustomer(editedAlice));
    }

    @Test
    public void getFilteredCustomerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredCustomerList().remove(0));
    }

    @Test
    public void getSelectedCustomerProperty_innerContent_isNullInitially() {
        assertNotNull(modelManager.getSelectedCustomerProperty());
        //ensures that when first initiated no customer's order will be displayed
        assertNull(modelManager.getSelectedCustomerProperty().get());
        assertNull(modelManager.getSelectedCustomer());
        assertNull(modelManager.getFilteredOrderList());
        assertNull(modelManager.getSelectedOrderList());
    }

    @Test
    public void updateSelectedCustomer_updateSuccessfully() {
        // Update to a customer
        modelManager.updateSelectedCustomer(ALICE);
        assertEquals(modelManager.getSelectedCustomerProperty().get(), ALICE);
        assertEquals(modelManager.getSelectedCustomer(), ALICE);
        assertEquals(modelManager.getFilteredOrderList(), ALICE.getFilteredOrderList());
        assertEquals(modelManager.getSelectedOrderList(), ALICE.getOrderList());

        // Update back to null
        modelManager.updateSelectedCustomer(null);
        assertNull(modelManager.getSelectedCustomerProperty().get());
        assertNull(modelManager.getSelectedCustomer());
        assertNull(modelManager.getFilteredOrderList());
        assertNull(modelManager.getSelectedOrderList());
    }

    @Test
    public void findEquivalentCustomer_returnNullWhenInputIsNull() {
        assertNull(modelManager.findEquivalentCustomer(null));
    }

    @Test
    public void findEquivalentCustomer_modelContainsEquivalentCustomer() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();
        Model modelCopy = model.createCopy();
        Customer selectedCustomer = model.getFilteredCustomerList().get(INDEX_FIRST.getZeroBased());
        Customer selectedCustomerCopy = modelCopy.findEquivalentCustomer(selectedCustomer);
        assertNotSame(selectedCustomerCopy, selectedCustomer);
        assertEquals(selectedCustomerCopy, selectedCustomer);
    }

    @Test
    public void findEquivalentCustomer_modelDoesNotContainsEquivalentCustomer() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();
        Customer selectedCustomer = model.getFilteredCustomerList().get(INDEX_FIRST.getZeroBased());
        Customer differentCustomer = new CustomerBuilder(selectedCustomer).withName(VALID_NAME_BOB).build();
        assertThrows(CustomerNotFoundException.class, () -> model.findEquivalentCustomer(differentCustomer));
    }

    @Test
    public void isSelectedCustomer_noSelectedCustomer() {
        assertTrue(modelManager.isSelectedCustomer(null));
    }

    @Test
    public void isSelectedCustomer_withSelectedCustomer() {
        modelManager.updateSelectedCustomer(ALICE);

        // same object -> returns true
        assertTrue(modelManager.isSelectedCustomer(ALICE));

        // same values -> returns true
        assertTrue(modelManager.isSelectedCustomer(new CustomerBuilder(ALICE).build()));

        // different values -> returns false
        assertFalse(modelManager.isSelectedCustomer(BOB));

        // null -> returns false
        assertFalse(modelManager.isSelectedCustomer(null));
    }

    @Test
    public void setOrder() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();

        // Assertion error thrown when there is no selectedCustomer
        assertThrows(AssertionError.class, () -> model.setOrder(ABACUS, BOTTLE));

        Customer selectedCustomer = model.findEquivalentCustomer(GEORGE);
        model.updateSelectedCustomer(selectedCustomer);

        // OrderNotFoundException are thrown when selectedCustomer has no such order
        assertThrows(OrderNotFoundException.class, () -> model.setOrder(new OrderBuilder(BOTTLE).build(), ABACUS));

        // success when selectedCustomer has the same order
        Order order = model.getFilteredOrderList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        model.setOrder(order, BOTTLE);
        assertFalse(model.getFilteredOrderList().contains(order));
        assertTrue(model.getFilteredOrderList().contains(BOTTLE));

    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withCustomer(ALICE).withCustomer(BENSON).build();
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
        modelManager.updateFilteredCustomerList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));

        // different selectedCustomer -> returns false
        modelManager.updateSelectedCustomer(ALICE);
        assertFalse(modelManager.equals(modelManagerCopy));
        assertFalse(modelManagerCopy.equals(modelManager));
    }
}

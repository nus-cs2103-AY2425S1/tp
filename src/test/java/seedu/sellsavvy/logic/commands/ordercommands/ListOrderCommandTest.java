package seedu.sellsavvy.logic.commands.ordercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.assertCommandFailure;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.assertCommandSuccess;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.showCustomerAtIndex;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.sellsavvy.testutil.TypicalCustomers.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;
import seedu.sellsavvy.model.UserPrefs;
import seedu.sellsavvy.model.customer.Customer;

public class ListOrderCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs()).createCopy();
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Customer selectedCustomer = model.getFilteredCustomerList().get(INDEX_FIRST.getZeroBased());
        ListOrderCommand listOrderCommand = new ListOrderCommand(INDEX_FIRST);

        String expectedMessage = String.format(ListOrderCommand.MESSAGE_LIST_ORDER_SUCCESS,
                selectedCustomer.getName().fullName);

        expectedModel.updateSelectedCustomer(
                expectedModel.getFilteredCustomerList().get(INDEX_FIRST.getZeroBased()));

        assertCommandSuccess(listOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCustomerList().size() + 1);
        ListOrderCommand listOrderCommand = new ListOrderCommand(outOfBoundIndex);

        assertCommandFailure(listOrderCommand, model, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCustomerAtIndex(model, INDEX_FIRST);
        showCustomerAtIndex(expectedModel, INDEX_FIRST);

        Customer selectedCustomer = model.getFilteredCustomerList().get(INDEX_FIRST.getZeroBased());
        ListOrderCommand listOrderCommand = new ListOrderCommand(INDEX_FIRST);

        expectedModel.updateSelectedCustomer(
                expectedModel.getFilteredCustomerList().get(INDEX_FIRST.getZeroBased()));

        String expectedMessage = String.format(ListOrderCommand.MESSAGE_LIST_ORDER_SUCCESS,
                selectedCustomer.getName().fullName);

        assertCommandSuccess(listOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCustomerAtIndex(model, INDEX_FIRST);
        showCustomerAtIndex(expectedModel, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getCustomerList().size());
        assertTrue(outOfBoundIndex.getZeroBased() < expectedModel.getAddressBook().getCustomerList().size());

        ListOrderCommand listOrderCommand = new ListOrderCommand(outOfBoundIndex);

        assertCommandFailure(listOrderCommand, model, Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ListOrderCommand listOrderOfFirstCustomerCommand = new ListOrderCommand(INDEX_FIRST);
        ListOrderCommand listOrderOfSecondCustomerCommand = new ListOrderCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(listOrderOfFirstCustomerCommand.equals(listOrderOfFirstCustomerCommand));

        // same values -> returns true
        ListOrderCommand listOrderOfFirstCustomerCommandCopy = new ListOrderCommand(INDEX_FIRST);
        assertTrue(listOrderOfFirstCustomerCommand.equals(listOrderOfFirstCustomerCommandCopy));

        // different types -> returns false
        assertFalse(listOrderOfFirstCustomerCommand.equals(1));

        // null -> returns false
        assertFalse(listOrderOfFirstCustomerCommand.equals(null));

        // different customer -> returns false
        assertFalse(listOrderOfFirstCustomerCommand.equals(listOrderOfSecondCustomerCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        ListOrderCommand listOrderCommand = new ListOrderCommand(targetIndex);
        String expected = ListOrderCommand.class.getCanonicalName() + "{index=" + targetIndex + "}";
        assertEquals(expected, listOrderCommand.toString());
    }

}

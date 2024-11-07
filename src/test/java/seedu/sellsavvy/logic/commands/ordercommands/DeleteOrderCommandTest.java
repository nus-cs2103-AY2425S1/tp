package seedu.sellsavvy.logic.commands.ordercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.assertCommandFailure;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.assertCommandSuccess;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.getOrderByIndex;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.getOrderListByIndex;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FOURTH;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.sellsavvy.testutil.TypicalCustomers.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;
import seedu.sellsavvy.model.UserPrefs;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.OrderList;
import seedu.sellsavvy.model.order.Status;
import seedu.sellsavvy.model.order.StatusEqualsKeywordPredicate;
import seedu.sellsavvy.model.customer.Customer;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteOrderCommand}.
 */
public class DeleteOrderCommandTest {

    private Model model;
    private Customer customerToDeleteOrderUnder;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();
        customerToDeleteOrderUnder = model.getFilteredCustomerList().get(INDEX_FOURTH.getZeroBased());
        model.updateSelectedCustomer(customerToDeleteOrderUnder);
    }

    @Test
    public void execute_validIndexUnfilteredOrderList_success() {
        Model expectedModel = model.createCopy();

        OrderList expectedUnfilteredOrderList = getOrderListByIndex(expectedModel, INDEX_FOURTH);
        Order expectedOrder = getOrderByIndex(expectedModel, INDEX_FIRST);
        expectedUnfilteredOrderList.remove(expectedOrder);
        String expectedMessage = String.format(DeleteOrderCommand.MESSAGE_DELETE_ORDER_SUCCESS,
                Messages.format(expectedOrder));

        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(INDEX_FIRST);
        assertCommandSuccess(deleteOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredOrderList_success() {
        customerToDeleteOrderUnder.updateFilteredOrderList(new StatusEqualsKeywordPredicate(Status.COMPLETED));
        Model expectedModel = model.createCopy();

        OrderList expectedFilteredOrderList = getOrderListByIndex(expectedModel, INDEX_FOURTH);
        Order expectedOrder = getOrderByIndex(expectedModel, INDEX_FIRST);
        expectedFilteredOrderList.remove(expectedOrder);
        String expectedMessage = String.format(DeleteOrderCommand.MESSAGE_DELETE_ORDER_SUCCESS,
                Messages.format(expectedOrder));

        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(INDEX_FIRST);
        assertCommandSuccess(deleteOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredOrderList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(outOfBoundIndex);

        assertCommandFailure(deleteOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredOrderList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size());

        customerToDeleteOrderUnder.updateFilteredOrderList(new StatusEqualsKeywordPredicate(Status.COMPLETED));

        assertTrue(outOfBoundIndex.getZeroBased() >= model.getFilteredOrderList().size());

        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(outOfBoundIndex);

        assertCommandFailure(deleteOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noOrderListDisplayed_throwsCommandException() {
        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(INDEX_FIRST);
        model.updateSelectedCustomer(null);

        assertCommandFailure(deleteOrderCommand, model, Messages.MESSAGE_ORDERLIST_DOES_NOT_EXIST);
    }

    @Test
    public void equals() {
        DeleteOrderCommand deleteFirstCommand = new DeleteOrderCommand(INDEX_FIRST);
        DeleteOrderCommand deleteSecondCommand = new DeleteOrderCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteOrderCommand deleteFirstCommandCopy = new DeleteOrderCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different customer -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = INDEX_FIRST;
        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(targetIndex);
        String expected = DeleteOrderCommand.class.getCanonicalName() + "{index=" + targetIndex + "}";
        assertEquals(expected, deleteOrderCommand.toString());
    }
}

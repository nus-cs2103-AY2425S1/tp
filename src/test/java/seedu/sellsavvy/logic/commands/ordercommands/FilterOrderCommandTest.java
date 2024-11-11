package seedu.sellsavvy.logic.commands.ordercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.Messages.MESSAGE_ORDERLIST_DOES_NOT_EXIST;
import static seedu.sellsavvy.logic.commands.ordercommands.FilterOrderCommand.MESSAGE_FILTER_ORDERS_SUCCESS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.assertCommandFailure;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.assertCommandSuccess;
import static seedu.sellsavvy.testutil.TypicalCustomers.getTypicalAddressBook;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FOURTH;
import static seedu.sellsavvy.testutil.TypicalOrders.ABACUS;
import static seedu.sellsavvy.testutil.TypicalOrders.BLOCKS;
import static seedu.sellsavvy.testutil.TypicalOrders.CAMERA;
import static seedu.sellsavvy.testutil.TypicalOrders.DAGGER;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;
import seedu.sellsavvy.model.UserPrefs;
import seedu.sellsavvy.model.customer.Customer;
import seedu.sellsavvy.model.order.Status;
import seedu.sellsavvy.model.order.StatusEqualsKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterOrderCommand}.
 */
public class FilterOrderCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();

    @Test
    public void equals() {
        StatusEqualsKeywordPredicate firstPredicate =
                new StatusEqualsKeywordPredicate(Status.COMPLETED);
        StatusEqualsKeywordPredicate secondPredicate =
                new StatusEqualsKeywordPredicate(Status.PENDING);

        FilterOrderCommand filterFirstCommand = new FilterOrderCommand(firstPredicate);
        FilterOrderCommand filterSecondCommand = new FilterOrderCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same status -> returns true
        FilterOrderCommand filterFirstCommandCopy = new FilterOrderCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different status -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_noSelectedCustomerInModel_throwsCommandException() {
        String expectedMessage = String.format(MESSAGE_ORDERLIST_DOES_NOT_EXIST);
        StatusEqualsKeywordPredicate predicate = new StatusEqualsKeywordPredicate(Status.PENDING);
        FilterOrderCommand command = new FilterOrderCommand(predicate);

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_statusCompleted_oneOrderListed() {
        String expectedMessage = String.format(MESSAGE_FILTER_ORDERS_SUCCESS, 1, Status.COMPLETED.getValue());
        StatusEqualsKeywordPredicate predicate = new StatusEqualsKeywordPredicate(Status.COMPLETED);
        FilterOrderCommand command = new FilterOrderCommand(predicate);

        Model expectedModel = model.createCopy();
        Customer expectedCustomer = expectedModel.getFilteredCustomerList().get(INDEX_FOURTH.getZeroBased());
        expectedCustomer.updateFilteredOrderList(predicate);
        expectedModel.updateSelectedCustomer(expectedCustomer);

        Customer customer = model.getFilteredCustomerList().get(INDEX_FOURTH.getZeroBased());
        model.updateSelectedCustomer(customer);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BLOCKS), customer.getFilteredOrderList());
    }

    @Test
    public void execute_statusPending_threeOrdersListed() {
        String expectedMessage = String.format(MESSAGE_FILTER_ORDERS_SUCCESS, 3, Status.PENDING.getValue());
        StatusEqualsKeywordPredicate predicate = new StatusEqualsKeywordPredicate(Status.PENDING);
        FilterOrderCommand command = new FilterOrderCommand(predicate);

        Model expectedModel = model.createCopy();
        Customer expectedCustomer = expectedModel.getFilteredCustomerList().get(INDEX_FOURTH.getZeroBased());
        expectedCustomer.updateFilteredOrderList(predicate);
        expectedModel.updateSelectedCustomer(expectedCustomer);

        Customer customer = model.getFilteredCustomerList().get(INDEX_FOURTH.getZeroBased());
        model.updateSelectedCustomer(customer);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ABACUS, CAMERA, DAGGER), customer.getFilteredOrderList());
    }

    @Test
    public void toStringMethod() {
        StatusEqualsKeywordPredicate predicate = new StatusEqualsKeywordPredicate(Status.PENDING);
        FilterOrderCommand filterOrderCommand = new FilterOrderCommand(predicate);
        String expected = FilterOrderCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, filterOrderCommand.toString());
    }
}

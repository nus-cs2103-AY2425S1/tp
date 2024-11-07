package seedu.sellsavvy.logic.commands.ordercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.commons.util.StringUtil.normalise;
import static seedu.sellsavvy.logic.commands.ordercommands.AddOrderCommand.MESSAGE_DUPLICATE_ORDER_WARNING;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.assertCommandFailure;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.assertCommandSuccess;
import static seedu.sellsavvy.logic.commands.customercommands.PersonCommandTestUtil.showPersonAtIndex;
import static seedu.sellsavvy.model.order.Date.MESSAGE_OUTDATED_WARNING;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.sellsavvy.testutil.TypicalOrders.ABACUS;
import static seedu.sellsavvy.testutil.TypicalOrders.BLOCKS;
import static seedu.sellsavvy.testutil.TypicalOrders.CAMERA;
import static seedu.sellsavvy.testutil.TypicalCustomers.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;
import seedu.sellsavvy.model.UserPrefs;
import seedu.sellsavvy.model.customer.Customer;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.Status;
import seedu.sellsavvy.testutil.OrderBuilder;

public class AddOrderCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();
    }

    @Test
    public void execute_validPersonIndexUnfilteredList_success() {
        AddOrderCommand addFirstOrder = new AddOrderCommand(INDEX_FIRST, ABACUS);

        Model expectedModel = model.createCopy();
        Customer customerToAddUnder = expectedModel.getFilteredPersonList().get(0);
        String firstExpectedMessage = String.format(AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS,
                customerToAddUnder.getName(), Messages.format(ABACUS));
        customerToAddUnder.getOrderList().add(ABACUS);
        expectedModel.updateSelectedPerson(customerToAddUnder);

        // First order
        assertCommandSuccess(addFirstOrder, model, firstExpectedMessage, expectedModel);

        // Second order, no warning messages
        AddOrderCommand addSecondOrder = new AddOrderCommand(INDEX_FIRST, BLOCKS);
        String secondExpectedMessage = String.format(AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS,
                customerToAddUnder.getName(), Messages.format(BLOCKS));
        customerToAddUnder.getOrderList().add(BLOCKS);

        assertCommandSuccess(addSecondOrder, model, secondExpectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePendingOrder_warningGiven() {
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST, ABACUS);
        // add the first order to the customer's order list
        model.getFilteredPersonList().get(0).getOrderList().add(ABACUS.createCopy());

        Model expectedModel = model.createCopy();
        Customer customerToAddUnder = expectedModel.getFilteredPersonList().get(0);
        customerToAddUnder.getOrderList().add(ABACUS);
        expectedModel.updateSelectedPerson(customerToAddUnder);
        String expectedMessage = String.format(MESSAGE_DUPLICATE_ORDER_WARNING
                + AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS, customerToAddUnder.getName(), Messages.format(ABACUS));

        assertCommandSuccess(addOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_similarPendingOrder_warningGiven() {
        // add something similar(but not identical) to the first order to the customer's order list
        String editedItemString = normalise(ABACUS.getItem().fullDescription);
        Order toAdd = new OrderBuilder(ABACUS).withItem(editedItemString).build();
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST, toAdd);

        //ensures it is not equal to ABACUS
        assertNotEquals(toAdd, ABACUS);

        model.getFilteredPersonList().get(0).getOrderList().add(ABACUS);

        Model expectedModel = model.createCopy();
        Customer customerToAddUnder = expectedModel.getFilteredPersonList().get(0);
        customerToAddUnder.getOrderList().add(toAdd);
        expectedModel.updateSelectedPerson(customerToAddUnder);
        String expectedMessage = String.format(MESSAGE_DUPLICATE_ORDER_WARNING
                + AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS, customerToAddUnder.getName(), Messages.format(toAdd));

        assertCommandSuccess(addOrderCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Ensures that when there is an {@code Order} with identical fields but different {@code Status} is present,
     * no error or warning will be thrown because of it.
     */
    @Test
    public void execute_duplicateCompletedOrder_success() {
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST, ABACUS);
        Order completedAbacus = new OrderBuilder(ABACUS).withStatus(Status.COMPLETED).build();
        model.getFilteredPersonList().get(0).getOrderList().add(completedAbacus);

        Model expectedModel = model.createCopy();
        Customer customerToAddUnder = expectedModel.getFilteredPersonList().get(0);
        customerToAddUnder.getOrderList().add(ABACUS);
        expectedModel.updateSelectedPerson(customerToAddUnder);
        String expectedMessage = String.format(AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS,
                customerToAddUnder.getName(), Messages.format(ABACUS));

        assertCommandSuccess(addOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_orderDateHasElapsed_warningGiven() {
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST, CAMERA);

        Model expectedModel = model.createCopy();
        Customer customerToAddUnder = expectedModel.getFilteredPersonList().get(0);
        String expectedMessage = String.format(MESSAGE_OUTDATED_WARNING + AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS,
                customerToAddUnder.getName(), Messages.format(CAMERA));
        customerToAddUnder.getOrderList().add(CAMERA);
        expectedModel.updateSelectedPerson(customerToAddUnder);

        assertCommandSuccess(addOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddOrderCommand addOrderCommand = new AddOrderCommand(outOfBoundIndex, ABACUS);

        assertCommandFailure(addOrderCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validPersonIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST, ABACUS);

        Model expectedModel = model.createCopy();
        showPersonAtIndex(expectedModel, INDEX_FIRST);
        Customer customerToAddUnder = expectedModel.getFilteredPersonList().get(0);
        String expectedMessage = String.format(AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS,
                customerToAddUnder.getName(), Messages.format(ABACUS));
        customerToAddUnder.getOrderList().add(ABACUS);
        expectedModel.updateSelectedPerson(customerToAddUnder);

        assertCommandSuccess(addOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddOrderCommand addOrderCommand = new AddOrderCommand(outOfBoundIndex, ABACUS);

        assertCommandFailure(addOrderCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddOrderCommand standardCommand = new AddOrderCommand(INDEX_FIRST, ABACUS);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // same values -> returns true
        assertTrue(standardCommand.equals(new AddOrderCommand(INDEX_FIRST, ABACUS)));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different Index -> returns false
        assertFalse(standardCommand.equals(new AddOrderCommand(INDEX_SECOND, ABACUS)));

        // different item -> returns false
        assertFalse(standardCommand.equals(new AddOrderCommand(INDEX_FIRST, BLOCKS)));
    }

    @Test
    public void toStringMethod() {
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST, ABACUS);
        String expected = AddOrderCommand.class.getCanonicalName()
                + "{index=" + INDEX_FIRST + ", order=" + ABACUS + "}";
        assertEquals(expected, addOrderCommand.toString());
    }
}

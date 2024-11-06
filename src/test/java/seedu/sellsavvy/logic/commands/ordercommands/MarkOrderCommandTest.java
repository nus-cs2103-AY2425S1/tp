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
import static seedu.sellsavvy.testutil.TypicalPersons.getTypicalAddressBook;

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
import seedu.sellsavvy.model.person.Person;
import seedu.sellsavvy.testutil.OrderBuilder;

public class MarkOrderCommandTest {

    private Model model;
    private Person personToMarkOrderUnder;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();
        personToMarkOrderUnder = model.getFilteredPersonList().get(INDEX_FOURTH.getZeroBased());
        model.updateSelectedPerson(personToMarkOrderUnder);
    }

    @Test
    public void execute_validIndexUnfilteredOrderList_success() {
        MarkOrderCommand markOrderCommand = new MarkOrderCommand(INDEX_FIRST);

        Model expectedModel = model.createCopy();
        Order orderToBeMarkedInUnfilteredList = getOrderByIndex(expectedModel, INDEX_FIRST);
        OrderBuilder orderBuilder = new OrderBuilder(orderToBeMarkedInUnfilteredList);
        Order markedOrder = orderBuilder.withStatus(Status.COMPLETED).build();

        getOrderListByIndex(expectedModel, INDEX_FOURTH).setOrder(orderToBeMarkedInUnfilteredList, markedOrder);
        String expectedMessage = String.format(MarkOrderCommand.MESSAGE_MARK_ORDER_SUCCESS,
                Messages.format(markedOrder));

        assertCommandSuccess(markOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredOrderList_success() {
        personToMarkOrderUnder.updateFilteredOrderList(new StatusEqualsKeywordPredicate(Status.PENDING));
        MarkOrderCommand markOrderCommand = new MarkOrderCommand(INDEX_FIRST);

        Model expectedModel = model.createCopy();
        Order orderToBeMarkedInFilteredList = getOrderByIndex(expectedModel, INDEX_FIRST);
        OrderBuilder orderBuilder = new OrderBuilder(orderToBeMarkedInFilteredList);
        Order markedOrder = orderBuilder.withStatus(Status.COMPLETED).build();

        getOrderListByIndex(expectedModel, INDEX_FOURTH).setOrder(orderToBeMarkedInFilteredList, markedOrder);
        String expectedMessage = String.format(MarkOrderCommand.MESSAGE_MARK_ORDER_SUCCESS,
                Messages.format(markedOrder));

        assertCommandSuccess(markOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredOrderList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        MarkOrderCommand markOrderCommand = new MarkOrderCommand(outOfBoundIndex);

        assertCommandFailure(markOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredOrderList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size());

        personToMarkOrderUnder.updateFilteredOrderList(new StatusEqualsKeywordPredicate(Status.PENDING));

        assertTrue(outOfBoundIndex.getZeroBased() >= model.getFilteredOrderList().size());

        MarkOrderCommand markOrderCommand = new MarkOrderCommand(outOfBoundIndex);

        assertCommandFailure(markOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noOrderListDisplayed_throwsCommandException() {
        MarkOrderCommand markOrderCommand = new MarkOrderCommand(INDEX_FIRST);
        model.updateSelectedPerson(null);

        assertCommandFailure(markOrderCommand, model, Messages.MESSAGE_ORDERLIST_DOES_NOT_EXIST);
    }

    @Test
    public void execute_orderAlreadyMarked_throwsCommandException() {
        Index targetIndex = INDEX_FIRST;
        MarkOrderCommand markOrderCommand = new MarkOrderCommand(targetIndex);

        // mark the first order
        OrderList orderList = model.getSelectedOrderList();
        Order order = orderList.get(targetIndex.getZeroBased());
        OrderBuilder orderBuilder = new OrderBuilder(order);
        Order markedOrder = orderBuilder.withStatus(Status.COMPLETED).build();
        orderList.setOrder(order, markedOrder);

        assertCommandFailure(markOrderCommand, model, MarkOrderCommand.MESSAGE_ORDER_ALREADY_MARKED);
    }

    @Test
    public void equals() {
        MarkOrderCommand markFirstCommand = new MarkOrderCommand(INDEX_FIRST);
        MarkOrderCommand markSecondCommand = new MarkOrderCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkOrderCommand markFirstCommandCopy = new MarkOrderCommand(INDEX_FIRST);
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different order index -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));
    }

    @Test
    public void toStringMethod() {
        MarkOrderCommand markOrderCommand = new MarkOrderCommand(INDEX_FIRST);
        String expected = MarkOrderCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST + "}";
        assertEquals(expected, markOrderCommand.toString());
    }
}

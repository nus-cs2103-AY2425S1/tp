package seedu.sellsavvy.logic.commands.ordercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.assertCommandFailure;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.assertCommandSuccess;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.getOrderByIndex;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.getOrderListByIndex;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.sellsavvy.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;
import seedu.sellsavvy.model.UserPrefs;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.Status;
import seedu.sellsavvy.model.order.StatusEqualsKeywordPredicate;
import seedu.sellsavvy.model.person.Person;
import seedu.sellsavvy.testutil.OrderBuilder;

public class UnmarkOrderCommandTest {

    private Model model;
    private Person personToUnmarkOrderUnder;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();
        personToUnmarkOrderUnder = model.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());
        model.updateSelectedPerson(personToUnmarkOrderUnder);
    }

    @Test
    public void execute_validIndexUnfilteredOrderList_success() {
        UnmarkOrderCommand unmarkOrderCommand = new UnmarkOrderCommand(INDEX_SECOND_ORDER);

        Model expectedModel = model.createCopy();
        Order orderToBeUnmarkedInUnfilteredList = getOrderByIndex(expectedModel, INDEX_SECOND_ORDER);
        OrderBuilder orderBuilder = new OrderBuilder(orderToBeUnmarkedInUnfilteredList);
        Order unmarkedOrder = orderBuilder.withStatus(Status.PENDING).build();

        getOrderListByIndex(expectedModel, INDEX_FOURTH_PERSON)
                .setOrder(orderToBeUnmarkedInUnfilteredList, unmarkedOrder);
        String expectedMessage = String.format(UnmarkOrderCommand.MESSAGE_UNMARK_ORDER_SUCCESS,
                Messages.format(unmarkedOrder));

        assertCommandSuccess(unmarkOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredOrderList_success() {
        personToUnmarkOrderUnder.updateFilteredOrderList(new StatusEqualsKeywordPredicate(Status.COMPLETED));
        UnmarkOrderCommand unmarkOrderCommand = new UnmarkOrderCommand(INDEX_FIRST_ORDER);

        Model expectedModel = model.createCopy();
        Order orderToBeUnmarkedInFilteredList = getOrderByIndex(expectedModel, INDEX_FIRST_ORDER);
        OrderBuilder orderBuilder = new OrderBuilder(orderToBeUnmarkedInFilteredList);
        Order unmarkedOrder = orderBuilder.withStatus(Status.PENDING).build();

        getOrderListByIndex(expectedModel, INDEX_FOURTH_PERSON)
                .setOrder(orderToBeUnmarkedInFilteredList, unmarkedOrder);
        String expectedMessage = String.format(UnmarkOrderCommand.MESSAGE_UNMARK_ORDER_SUCCESS,
                Messages.format(unmarkedOrder));

        assertCommandSuccess(unmarkOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredOrderList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        UnmarkOrderCommand unmarkOrderCommand = new UnmarkOrderCommand(outOfBoundIndex);

        assertCommandFailure(unmarkOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredOrderList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size());

        personToUnmarkOrderUnder.updateFilteredOrderList(new StatusEqualsKeywordPredicate(Status.COMPLETED));

        assertTrue(outOfBoundIndex.getZeroBased() >= model.getFilteredOrderList().size());

        UnmarkOrderCommand unmarkOrderCommand = new UnmarkOrderCommand(outOfBoundIndex);

        assertCommandFailure(unmarkOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noOrderListDisplayed_throwsCommandException() {
        UnmarkOrderCommand unmarkOrderCommand = new UnmarkOrderCommand(INDEX_FIRST_ORDER);
        model.updateSelectedPerson(null);

        assertCommandFailure(unmarkOrderCommand, model, Messages.MESSAGE_ORDERLIST_DOES_NOT_EXIST);
    }

    @Test
    public void execute_orderAlreadyUnmarked_throwsCommandException() {
        Index targetIndex = INDEX_FIRST_ORDER;
        UnmarkOrderCommand unmarkOrderCommand = new UnmarkOrderCommand(targetIndex);

        assertCommandFailure(unmarkOrderCommand, model, UnmarkOrderCommand.MESSAGE_ORDER_ALREADY_UNMARKED);
    }

    @Test
    public void equals() {
        UnmarkOrderCommand unmarkFirstCommand = new UnmarkOrderCommand(INDEX_FIRST_ORDER);
        UnmarkOrderCommand unmarkSecondCommand = new UnmarkOrderCommand(INDEX_SECOND_ORDER);

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // same values -> returns true
        UnmarkOrderCommand unmarkFirstCommandCopy = new UnmarkOrderCommand(INDEX_FIRST_ORDER);
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(unmarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unmarkFirstCommand.equals(null));

        // different order index -> returns false
        assertFalse(unmarkFirstCommand.equals(unmarkSecondCommand));
    }

    @Test
    public void toStringMethod() {
        UnmarkOrderCommand unmarkOrderCommand = new UnmarkOrderCommand(INDEX_FIRST_ORDER);
        String expected = UnmarkOrderCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_ORDER + "}";
        assertEquals(expected, unmarkOrderCommand.toString());
    }
}

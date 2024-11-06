package seedu.sellsavvy.logic.commands.ordercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.assertCommandFailure;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.assertCommandSuccess;
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
import seedu.sellsavvy.model.person.Person;

public class UnmarkOrderCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();
        Person personToUnmarkOrderUnder = model.getFilteredPersonList().get(INDEX_FOURTH.getZeroBased());
        model.updateSelectedPerson(personToUnmarkOrderUnder);
    }

    @Test
    public void execute_validIndexOrderList_success() {
        UnmarkOrderCommand unmarkOrderCommand = new UnmarkOrderCommand(INDEX_SECOND);

        Model expectedModel = model.createCopy();
        Order orderToBeUnmarked = expectedModel.getFilteredPersonList().get(INDEX_FOURTH.getZeroBased())
                .getOrderList().get(INDEX_SECOND.getZeroBased());
        Order unmarkedOrder = UnmarkOrderCommand.createUnmarkedOrder(orderToBeUnmarked);

        expectedModel.getFilteredPersonList().get(INDEX_FOURTH.getZeroBased()).getOrderList()
                .setOrder(orderToBeUnmarked, unmarkedOrder);
        String expectedMessage = String.format(UnmarkOrderCommand.MESSAGE_UNMARK_ORDER_SUCCESS,
                Messages.format(unmarkedOrder));

        assertCommandSuccess(unmarkOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexOrderList_throwsCommandException() {
        Person person = model.getFilteredPersonList().get(INDEX_FOURTH.getZeroBased());
        model.updateSelectedPerson(person);
        Index outOfBoundIndex = Index.fromOneBased(person.getOrderList().size() + 1);
        UnmarkOrderCommand unmarkOrderCommand = new UnmarkOrderCommand(outOfBoundIndex);

        assertCommandFailure(unmarkOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noOrderListDisplayed_throwsCommandException() {
        UnmarkOrderCommand unmarkOrderCommand = new UnmarkOrderCommand(INDEX_FIRST);
        model.updateSelectedPerson(null);

        assertCommandFailure(unmarkOrderCommand, model, Messages.MESSAGE_ORDERLIST_DOES_NOT_EXIST);
    }

    @Test
    public void execute_orderAlreadyUnmarked_throwsCommandException() {
        Index targetIndex = INDEX_FIRST;
        UnmarkOrderCommand unmarkOrderCommand = new UnmarkOrderCommand(targetIndex);

        assertCommandFailure(unmarkOrderCommand, model, UnmarkOrderCommand.MESSAGE_ORDER_ALREADY_UNMARKED);
    }

    @Test
    public void equals() {
        UnmarkOrderCommand unmarkFirstCommand = new UnmarkOrderCommand(INDEX_FIRST);
        UnmarkOrderCommand unmarkSecondCommand = new UnmarkOrderCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(unmarkFirstCommand.equals(unmarkFirstCommand));

        // same values -> returns true
        UnmarkOrderCommand unmarkFirstCommandCopy = new UnmarkOrderCommand(INDEX_FIRST);
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
        UnmarkOrderCommand unmarkOrderCommand = new UnmarkOrderCommand(INDEX_FIRST);
        String expected = UnmarkOrderCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST + "}";
        assertEquals(expected, unmarkOrderCommand.toString());
    }
}

package seedu.sellsavvy.logic.commands.ordercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.assertCommandFailure;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.assertCommandSuccess;
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
import seedu.sellsavvy.model.person.Person;

public class UnmarkOrderCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();
        Person personToUnmarkOrderUnder = model.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());
        model.updateSelectedPerson(personToUnmarkOrderUnder);
    }

    @Test
    public void execute_validIndexOrderList_success() {
        UnmarkOrderCommand unmarkOrderCommand = new UnmarkOrderCommand(INDEX_SECOND_ORDER);

        Model expectedModel = model.createCopy();
        Order orderToBeUnmarked = expectedModel.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased())
                .getOrderList().get(INDEX_SECOND_ORDER.getZeroBased());
        Order unmarkedOrder = UnmarkOrderCommand.createUnmarkedOrder(orderToBeUnmarked);

        expectedModel.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased()).getOrderList()
                .setOrder(orderToBeUnmarked, unmarkedOrder);
        String expectedMessage = String.format(UnmarkOrderCommand.MESSAGE_UNMARK_ORDER_SUCCESS,
                Messages.format(unmarkedOrder));

        assertCommandSuccess(unmarkOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexOrderList_throwsCommandException() {
        Person person = model.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());
        model.updateSelectedPerson(person);
        Index outOfBoundIndex = Index.fromOneBased(person.getOrderList().size() + 1);
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
    public void execute_orderAlreadyUnmarked_warningGiven() {
        UnmarkOrderCommand unmarkOrderCommand = new UnmarkOrderCommand(INDEX_FIRST_ORDER);

        Model expectedModel = model.createCopy();
        Order orderToBeUnmarked = expectedModel.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased())
                .getOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order unmarkedOrder = UnmarkOrderCommand.createUnmarkedOrder(orderToBeUnmarked);

        expectedModel.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased()).getOrderList()
                .setOrder(orderToBeUnmarked, unmarkedOrder);
        String expectedMessage = String.format(UnmarkOrderCommand.MESSAGE_ORDER_ALREADY_UNMARKED_WARNING
                        + UnmarkOrderCommand.MESSAGE_UNMARK_ORDER_SUCCESS,
                Messages.format(unmarkedOrder));

        assertCommandSuccess(unmarkOrderCommand, model, expectedMessage, expectedModel);
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

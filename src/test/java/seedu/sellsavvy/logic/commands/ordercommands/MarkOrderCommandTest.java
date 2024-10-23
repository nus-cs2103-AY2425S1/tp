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

public class MarkOrderCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();
        Person personToMarkOrderUnder = model.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());
        model.updateSelectedPerson(personToMarkOrderUnder);
    }

    @Test
    public void execute_validIndexOrderList_success() {
        MarkOrderCommand markOrderCommand = new MarkOrderCommand(INDEX_FIRST_ORDER);

        Model expectedModel = model.createCopy();
        Order orderToBeMarked = expectedModel.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased())
                .getOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        Order markedOrder = MarkOrderCommand.createMarkedOrder(orderToBeMarked);

        expectedModel.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased()).getOrderList()
                .setOrder(orderToBeMarked, markedOrder);
        String expectedMessage = String.format(MarkOrderCommand.MESSAGE_MARK_ORDER_SUCCESS,
                Messages.format(markedOrder));

        assertCommandSuccess(markOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexOrderList_throwsCommandException() {
        Person person = model.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());
        model.updateSelectedPerson(person);
        Index outOfBoundIndex = Index.fromOneBased(person.getOrderList().size() + 1);
        MarkOrderCommand markOrderCommand = new MarkOrderCommand(outOfBoundIndex);

        assertCommandFailure(markOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noOrderListDisplayed_throwsCommandException() {
        MarkOrderCommand markOrderCommand = new MarkOrderCommand(INDEX_FIRST_ORDER);
        model.updateSelectedPerson(null);

        assertCommandFailure(markOrderCommand, model, Messages.MESSAGE_ORDERLIST_DOES_NOT_EXIST);
    }

    @Test
    public void execute_orderAlreadyMarked_warningGiven() {
        MarkOrderCommand markOrderCommand = new MarkOrderCommand(INDEX_SECOND_ORDER);

        Model expectedModel = model.createCopy();
        Order orderToBeMarked = expectedModel.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased())
                .getOrderList().get(INDEX_SECOND_ORDER.getZeroBased());
        Order markedOrder = MarkOrderCommand.createMarkedOrder(orderToBeMarked);

        expectedModel.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased()).getOrderList()
                .setOrder(orderToBeMarked, markedOrder);
        String expectedMessage = String.format(MarkOrderCommand.MESSAGE_ORDER_ALREADY_MARKED_WARNING
                        + MarkOrderCommand.MESSAGE_MARK_ORDER_SUCCESS,
                Messages.format(markedOrder));

        assertCommandSuccess(markOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        MarkOrderCommand markFirstCommand = new MarkOrderCommand(INDEX_FIRST_ORDER);
        MarkOrderCommand markSecondCommand = new MarkOrderCommand(INDEX_SECOND_ORDER);

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkOrderCommand markFirstCommandCopy = new MarkOrderCommand(INDEX_FIRST_ORDER);
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
        MarkOrderCommand markOrderCommand = new MarkOrderCommand(INDEX_FIRST_ORDER);
        String expected = MarkOrderCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_ORDER + "}";
        assertEquals(expected, markOrderCommand.toString());
    }
}

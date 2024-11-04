package seedu.sellsavvy.logic.commands.ordercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.ordercommands.AddOrderCommand.MESSAGE_DUPLICATE_ORDER_WARNING;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.assertCommandFailure;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.assertCommandSuccess;
import static seedu.sellsavvy.logic.commands.personcommands.PersonCommandTestUtil.showPersonAtIndex;
import static seedu.sellsavvy.model.order.Date.MESSAGE_OUTDATED_WARNING;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.sellsavvy.testutil.TypicalOrders.ABACUS;
import static seedu.sellsavvy.testutil.TypicalOrders.BLOCKS;
import static seedu.sellsavvy.testutil.TypicalOrders.CAMERA;
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
import seedu.sellsavvy.model.person.Person;
import seedu.sellsavvy.testutil.OrderBuilder;

public class AddOrderCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();
    }

    @Test
    public void execute_validPersonIndexUnfilteredList_success() {
        AddOrderCommand addFirstOrder = new AddOrderCommand(INDEX_FIRST_PERSON, ABACUS);

        Model expectedModel = model.createCopy();
        Person personToAddUnder = expectedModel.getFilteredPersonList().get(0);
        String firstExpectedMessage = String.format(AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS,
                personToAddUnder.getName(), Messages.format(ABACUS));
        personToAddUnder.getOrderList().add(ABACUS);
        expectedModel.updateSelectedPerson(personToAddUnder);

        // First order
        assertCommandSuccess(addFirstOrder, model, firstExpectedMessage, expectedModel);

        // Second order, no warning messages
        AddOrderCommand addSecondOrder = new AddOrderCommand(INDEX_FIRST_PERSON, BLOCKS);
        String secondExpectedMessage = String.format(AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS,
                personToAddUnder.getName(), Messages.format(BLOCKS));
        personToAddUnder.getOrderList().add(BLOCKS);

        assertCommandSuccess(addSecondOrder, model, secondExpectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePendingOrder_warningGiven() {
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST_PERSON, ABACUS);
        // add the first order to the person's order list
        model.getFilteredPersonList().get(0).getOrderList().add(ABACUS);

        Model expectedModel = model.createCopy();
        Person personToAddUnder = expectedModel.getFilteredPersonList().get(0);
        personToAddUnder.getOrderList().add(ABACUS);
        expectedModel.updateSelectedPerson(personToAddUnder);
        String expectedMessage = String.format(MESSAGE_DUPLICATE_ORDER_WARNING
                + AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS, personToAddUnder.getName(), Messages.format(ABACUS));

        assertCommandSuccess(addOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCompletedOrder_success() {
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST_PERSON, ABACUS);
        Order completedAbacus = new OrderBuilder(ABACUS).withStatus(Status.COMPLETED).build();
        model.getFilteredPersonList().get(0).getOrderList().add(completedAbacus);

        Model expectedModel = model.createCopy();
        Person personToAddUnder = expectedModel.getFilteredPersonList().get(0);
        personToAddUnder.getOrderList().add(ABACUS);
        expectedModel.updateSelectedPerson(personToAddUnder);
        String expectedMessage = String.format(AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS,
                personToAddUnder.getName(), Messages.format(ABACUS));

        assertCommandSuccess(addOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_orderDateHasElapsed_warningGiven() {
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST_PERSON, CAMERA);

        Model expectedModel = model.createCopy();
        Person personToAddUnder = expectedModel.getFilteredPersonList().get(0);
        String expectedMessage = String.format(MESSAGE_OUTDATED_WARNING + AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS,
                personToAddUnder.getName(), Messages.format(CAMERA));
        personToAddUnder.getOrderList().add(CAMERA);
        expectedModel.updateSelectedPerson(personToAddUnder);

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
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST_PERSON, ABACUS);

        Model expectedModel = model.createCopy();
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        Person personToAddUnder = expectedModel.getFilteredPersonList().get(0);
        String expectedMessage = String.format(AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS,
                personToAddUnder.getName(), Messages.format(ABACUS));
        personToAddUnder.getOrderList().add(ABACUS);
        expectedModel.updateSelectedPerson(personToAddUnder);

        assertCommandSuccess(addOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddOrderCommand addOrderCommand = new AddOrderCommand(outOfBoundIndex, ABACUS);

        assertCommandFailure(addOrderCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddOrderCommand standardCommand = new AddOrderCommand(INDEX_FIRST_PERSON, ABACUS);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // same values -> returns true
        assertTrue(standardCommand.equals(new AddOrderCommand(INDEX_FIRST_PERSON, ABACUS)));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different Index -> returns false
        assertFalse(standardCommand.equals(new AddOrderCommand(INDEX_SECOND_PERSON, ABACUS)));

        // different item -> returns false
        assertFalse(standardCommand.equals(new AddOrderCommand(INDEX_FIRST_PERSON, BLOCKS)));
    }

    @Test
    public void toStringMethod() {
        AddOrderCommand addOrderCommand = new AddOrderCommand(INDEX_FIRST_PERSON, ABACUS);
        String expected = AddOrderCommand.class.getCanonicalName()
                + "{index=" + INDEX_FIRST_PERSON + ", order=" + ABACUS + "}";
        assertEquals(expected, addOrderCommand.toString());
    }
}

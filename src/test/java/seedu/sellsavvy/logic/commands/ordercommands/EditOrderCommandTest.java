package seedu.sellsavvy.logic.commands.ordercommands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.DESC_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.DESC_BOTTLE;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_COUNT_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_COUNT_BOTTLE;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_DATE_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.assertCommandFailure;
import static seedu.sellsavvy.logic.commands.personcommands.PersonCommandTestUtil.assertCommandSuccess;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.sellsavvy.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.logic.commands.generalcommands.ClearCommand;
import seedu.sellsavvy.logic.commands.ordercommands.EditOrderCommand.EditOrderDescriptor;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;
import seedu.sellsavvy.model.UserPrefs;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.person.Person;
import seedu.sellsavvy.testutil.EditOrderDescriptorBuilder;
import seedu.sellsavvy.testutil.OrderBuilder;

public class EditOrderCommandTest {
    private Model model;
    private Person selectedPerson;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs()).createCopy();
        selectedPerson = model.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());
        model.updateSelectedPerson(selectedPerson);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Order editedOrder = new OrderBuilder().build();
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(editedOrder).build();
        EditOrderCommand editOrderCommand = new EditOrderCommand(INDEX_FIRST_ORDER, descriptor);

        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS,
                Messages.format(editedOrder));

        Model expectedModel = model.createCopy();
        expectedModel.setOrder(expectedModel.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased()), editedOrder);

        assertCommandSuccess(editOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastOrder = Index.fromOneBased(model.getFilteredOrderList().size());
        Order lastOrder = model.getFilteredOrderList().get(indexLastOrder.getZeroBased());

        OrderBuilder orderInList = new OrderBuilder(lastOrder);
        Order editedOrder = orderInList.withCount(VALID_COUNT_ATLAS)
                .withDate(VALID_DATE_ATLAS).build();

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withQuantity(VALID_COUNT_ATLAS)
                .withDate(VALID_DATE_ATLAS).build();
        EditOrderCommand editOrderCommand = new EditOrderCommand(indexLastOrder, descriptor);

        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS,
                Messages.format(editedOrder));

        Model expectedModel = model.createCopy();
        expectedModel.setOrder(expectedModel.getFilteredOrderList().get(indexLastOrder.getZeroBased()), editedOrder);

        assertCommandSuccess(editOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noOrderListDisplayed_failure() {
        model.updateSelectedPerson(null);
        Order editedOrder = new OrderBuilder().build();
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(editedOrder).build();
        EditOrderCommand editOrderCommand = new EditOrderCommand(INDEX_FIRST_ORDER, descriptor);

        String expectedMessage = Messages.MESSAGE_ORDERLIST_DOES_NOT_EXIST;

        assertCommandFailure(editOrderCommand, model, expectedMessage);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditOrderCommand editOrderCommand = new EditOrderCommand(INDEX_FIRST_ORDER, new EditOrderDescriptor());
        Order editedOrder = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());

        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS,
                Messages.format(editedOrder));

        Model expectedModel = model.createCopy();

        assertCommandSuccess(editOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateOrderUnfilteredList_warning() {
        Order firstOrder = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(firstOrder).build();
        EditOrderCommand editOrderCommand = new EditOrderCommand(INDEX_THIRD_PERSON, descriptor);

        String expectedMessage = EditOrderCommand.MESSAGE_DUPLICATE_ORDER_WARNING
                + String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS, Messages.format(firstOrder));

        Model expectedModel = model.createCopy();
        expectedModel.setOrder(expectedModel.getFilteredOrderList().get(INDEX_THIRD_PERSON.getZeroBased()),
                expectedModel.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased()));

        assertCommandSuccess(editOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidOrderIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withQuantity(VALID_COUNT_BOTTLE).build();
        EditOrderCommand editOrderCommand = new EditOrderCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    //TODO editOrder test case to maintain status (secondOrder is completeted and will remain completed even if changed
    //TODO editOrder test case for filteredList

    @Test
    public void equals() {
        final EditOrderCommand standardCommand = new EditOrderCommand(INDEX_FIRST_ORDER, DESC_ATLAS);

        // same values -> returns true
        EditOrderDescriptor copyDescriptor = new EditOrderDescriptor(DESC_ATLAS);
        EditOrderCommand commandWithSameValues = new EditOrderCommand(INDEX_FIRST_ORDER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditOrderCommand(INDEX_SECOND_ORDER, DESC_ATLAS)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditOrderCommand(INDEX_FIRST_ORDER, DESC_BOTTLE)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditOrderDescriptor editOrderDescriptor = new EditOrderDescriptor();
        EditOrderCommand editOrderCommand = new EditOrderCommand(index, editOrderDescriptor);
        String expected = EditOrderCommand.class.getCanonicalName() + "{index=" + index + ", editOrderDescriptor="
                + editOrderDescriptor + "}";
        assertEquals(expected, editOrderCommand.toString());
    }
}

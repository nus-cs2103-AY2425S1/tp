package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDeliveries.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DELIVERY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_DELIVERY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.Delivery;

public class DeleteDeliveryCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Delivery deliveryToDelete = model.getFilteredDeliveryList().get(INDEX_FIRST_DELIVERY.getZeroBased());
        DeleteDeliveryCommand deleteDeliveryCommand = new DeleteDeliveryCommand(INDEX_FIRST_DELIVERY);

        String expectedMessage = String.format(DeleteDeliveryCommand.MESSAGE_DELETE_DELIVERY_SUCCESS,
                deliveryToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteDelivery(deliveryToDelete);

        assertCommandSuccess(deleteDeliveryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDeliveryList().size() + 1);
        DeleteDeliveryCommand deleteDeliveryCommand = new DeleteDeliveryCommand(outOfBoundIndex);

        assertCommandFailure(deleteDeliveryCommand, model, Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteDeliveryCommand deleteFirstCommand = new DeleteDeliveryCommand(INDEX_FIRST_DELIVERY);
        DeleteDeliveryCommand deleteSecondCommand = new DeleteDeliveryCommand(INDEX_SECOND_DELIVERY);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteDeliveryCommand deleteFirstCommandCopy = new DeleteDeliveryCommand(INDEX_FIRST_DELIVERY);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different delivery -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}


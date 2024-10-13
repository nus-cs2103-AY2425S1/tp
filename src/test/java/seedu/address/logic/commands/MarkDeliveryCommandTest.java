package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalDeliveries.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_DELIVERY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.Status;
import seedu.address.testutil.TypicalDeliveries;

public class MarkDeliveryCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws InterruptedException {
        Delivery deliveryToMark = TypicalDeliveries.BREAD;
        Index index = Index.fromOneBased(model.getFilteredDeliveryList().indexOf(deliveryToMark) + 1);
        MarkDeliveryCommand markDeliveryCommand = new MarkDeliveryCommand(index, Status.DELIVERED);

        String expectedMessage = String.format(
                MarkDeliveryCommand.MESSAGE_MARK_DELIVERY_SUCCESS, deliveryToMark, Status.DELIVERED);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Delivery updatedDelivery = new Delivery(
                deliveryToMark.getDeliveryProduct(),
                deliveryToMark.getDeliverySender(),
                Status.DELIVERED, // new status
                deliveryToMark.getDeliveryDate(),
                deliveryToMark.getDeliveryCost(),
                deliveryToMark.getDeliveryQuantity());
        expectedModel.setDelivery(deliveryToMark, updatedDelivery); // Update expected model

        assertCommandSuccess(markDeliveryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDeliveryList().size() + 1);
        MarkDeliveryCommand markDeliveryCommand = new MarkDeliveryCommand(outOfBoundIndex, Status.DELIVERED);

        assertCommandFailure(markDeliveryCommand, model, Messages.MESSAGE_INVALID_DELIVERY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MarkDeliveryCommand markFirstCommand = new MarkDeliveryCommand(INDEX_FIRST_DELIVERY, Status.DELIVERED);
        MarkDeliveryCommand markSecondCommand = new MarkDeliveryCommand(Index.fromOneBased(2), Status.CANCELLED);

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkDeliveryCommand markFirstCommandCopy = new MarkDeliveryCommand(INDEX_FIRST_DELIVERY, Status.DELIVERED);
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different delivery -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));
    }
}



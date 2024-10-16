package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.vendor.Vendor;
import seedu.address.testutil.TypicalEvents;
import seedu.address.testutil.TypicalVendors;

class UnassignCommandTest {

    private Model model;
    private Vendor vendor;
    private Event event;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalVendors.getTypicalAddressBook(), new UserPrefs());
        vendor = TypicalVendors.ALICE; // Customize with utility
        event = TypicalEvents.ALICE; // Use the predefined event
        model.addEvent(event);
        model.assignVendorToEvent(vendor, event);
    }

    @Test
    public void execute_unassignSuccess() throws Exception {
        UnassignCommand unassignCommand = new UnassignCommand(Index.fromOneBased(1), Index.fromOneBased(1));

        CommandResult result = unassignCommand.execute(model);

        // Verify the output and unassignment
        assertEquals(String.format(UnassignCommand.MESSAGE_UNASSIGN_SUCCESS, 1, 1), result.getFeedbackToUser());

        // Verify that the vendor is not unassigned to the event
        assertEquals(false, model.isVendorAssignedToEvent(vendor, event));
    }

    @Test
    public void execute_invalidVendorIndex_throwsCommanaException() {
        UnassignCommand unassignCommand = new UnassignCommand(
                Index.fromZeroBased(TypicalVendors.getTypicalVendors().size()),
                Index.fromOneBased(1));

        // Verify that an exception is thrown for an invalid vendor index
        CommandException exception = assertThrows(CommandException.class, () -> unassignCommand.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX, exception.getMessage());
    }

    @Test
    public void execute_invalidEventIndex_throwsCommandException() {
        UnassignCommand unassignCommand = new UnassignCommand(Index.fromOneBased(1), Index.fromOneBased(2));

        // Verify that an exception is thrown for an invalid event index
        CommandException exception = assertThrows(CommandException.class, () -> unassignCommand.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, exception.getMessage());
    }

    @Test
    public void execute_assignDoesNotExist_throwsCommandException() {
        // Add the vendor and event, and unassign them once
        model.unassignVendorFromEvent(vendor, event);

        UnassignCommand unassignCommand = new UnassignCommand(Index.fromOneBased(1), Index.fromOneBased(1));

        // Verify that an exception is thrown when the vendor is already unassigned
        CommandException exception = assertThrows(CommandException.class, () -> unassignCommand.execute(model));
        assertEquals(Messages.MESSAGE_VENDOR_NOT_ASSIGNED_TO_EVENT, exception.getMessage());
    }
}

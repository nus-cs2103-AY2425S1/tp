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

class AssignCommandTest {

    private Model model;
    private Vendor vendor;
    private Event event;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalVendors.getTypicalAddressBook(), new UserPrefs());
        vendor = TypicalVendors.ALICE; // Customize with utility
        event = TypicalEvents.ALICE; // Use the predefined event
        model.addEvent(event);
    }

    @Test
    public void execute_assignSuccess() throws Exception {
        AssignCommand assignCommand = new AssignCommand(Index.fromOneBased(1), Index.fromOneBased(1));

        CommandResult result = assignCommand.execute(model);

        // Verify the output and assignment
        assertEquals(String.format(AssignCommand.MESSAGE_ASSIGN_SUCCESS, 1, 1), result.getFeedbackToUser());

        // Verify that the vendor is assigned to the event
        assertEquals(true, model.isVendorAssignedToEvent(vendor, event));
    }

    @Test
    public void execute_invalidVendorIndex_throwsCommandException() {
        AssignCommand assignCommand = new AssignCommand(
                Index.fromZeroBased(TypicalVendors.getTypicalVendors().size()),
                Index.fromOneBased(1));

        // Verify that an exception is thrown for an invalid vendor index
        CommandException exception = assertThrows(CommandException.class, () -> assignCommand.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX, exception.getMessage());
    }

    @Test
    public void execute_invalidEventIndex_throwsCommandException() {
        AssignCommand assignCommand = new AssignCommand(Index.fromOneBased(1), Index.fromOneBased(2));

        // Verify that an exception is thrown for an invalid event index
        CommandException exception = assertThrows(CommandException.class, () -> assignCommand.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, exception.getMessage());
    }

    @Test
    public void execute_assignAlreadyExists_throwsCommandException() {
        // Add the vendor and event, and assign them once
        model.assignVendorToEvent(vendor, event);

        AssignCommand assignCommand = new AssignCommand(Index.fromOneBased(1), Index.fromOneBased(1));

        // Verify that an exception is thrown when the vendor is already assigned
        CommandException exception = assertThrows(CommandException.class, () -> assignCommand.execute(model));
        assertEquals(Messages.MESSAGE_VENDOR_ALREADY_ASSIGNED, exception.getMessage());
    }
}

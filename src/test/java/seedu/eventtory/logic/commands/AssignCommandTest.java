package seedu.eventtory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.eventtory.commons.core.index.Index;
import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.ModelManager;
import seedu.eventtory.model.UserPrefs;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.testutil.TypicalEvents;
import seedu.eventtory.testutil.TypicalVendors;

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

    @Test
    public void equals() {
        AssignCommand assignCommand = new AssignCommand(Index.fromOneBased(1), Index.fromOneBased(1));

        // Same object -> returns true
        assertTrue(assignCommand.equals(assignCommand));

        // Same values -> returns true
        assertTrue(assignCommand.equals(new AssignCommand(Index.fromOneBased(1), Index.fromOneBased(1))));

        // Different vendor index -> returns false
        assertFalse(assignCommand.equals(new AssignCommand(Index.fromOneBased(2), Index.fromOneBased(1))));

        // Different event index -> returns false
        assertFalse(assignCommand.equals(new AssignCommand(Index.fromOneBased(1), Index.fromOneBased(2))));
    }
}

package seedu.eventtory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventtory.testutil.TypicalIndexes.INDEX_LAST_EVENT;
import static seedu.eventtory.testutil.TypicalIndexes.INDEX_LAST_VENDOR;

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
import seedu.eventtory.ui.UiState;

class AssignCommandTest {
    private static final Index INDEX_VENDOR_OVERFLOW = Index.fromOneBased(INDEX_LAST_VENDOR.getOneBased() + 1);
    private static final Index INDEX_EVENT_OVERFLOW = Index.fromOneBased(INDEX_LAST_EVENT.getOneBased() + 1);
    private Model model;
    private Vendor vendor;
    private Event event;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalVendors.getTypicalEventTory(), new UserPrefs());
        vendor = TypicalVendors.ALICE; // Customize with utility
        event = TypicalEvents.ALICE; // Use the predefined event
        model.addEvent(event);
    }

    @Test
    public void execute_assignSuccess() throws Exception {
        model.viewEvent(event);
        AssignCommand assignCommand = new AssignCommand(Index.fromOneBased(1));

        CommandResult result = assignCommand.execute(model);

        // Verify the output and assignment
        assertEquals(String.format(AssignCommand.MESSAGE_ASSIGN_VENDOR_SUCCESS, vendor.getName(), event.getName()),
                result.getFeedbackToUser());

        // Verify that the vendor is assigned to the event
        assertEquals(true, model.isVendorAssignedToEvent(vendor, event));
    }

    @Test
    public void execute_invalidVendorIndex_throwsCommandException() {
        model.viewEvent(event);
        AssignCommand assignCommand = new AssignCommand(INDEX_VENDOR_OVERFLOW);

        // Verify that an exception is thrown for an invalid vendor index
        CommandException exception = assertThrows(CommandException.class, () -> assignCommand.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX, exception.getMessage());
    }

    @Test
    public void execute_invalidEventIndex_throwsCommandException() {
        model.viewVendor(vendor);
        AssignCommand assignCommand = new AssignCommand(INDEX_LAST_EVENT);

        // Verify that an exception is thrown for an invalid event index
        CommandException exception = assertThrows(CommandException.class, () -> assignCommand.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, exception.getMessage());
    }

    @Test
    public void execute_assignAlreadyExists_throwsCommandException() {
        // Add the vendor and event, and assign them once
        model.assignVendorToEvent(vendor, event);
        model.viewEvent(event);

        AssignCommand assignCommand = new AssignCommand(INDEX_LAST_VENDOR);

        // Verify that an exception is thrown when the vendor is already assigned
        CommandException exception = assertThrows(CommandException.class, () -> assignCommand.execute(model));
        assertEquals(Messages.MESSAGE_EVENT_ALREADY_CONTAINS_VENDOR, exception.getMessage());
    }

    @Test
    public void execute_assignEventToVendorInVendorView() {
        model.viewVendor(vendor);
        AssignCommand assignCommand = new AssignCommand(Index.fromOneBased(1));

        try {
            CommandResult result = assignCommand.execute(model);
            // Verify the output and assignment
            assertEquals(String.format(AssignCommand.MESSAGE_ASSIGN_EVENT_SUCCESS, vendor.getName(), event.getName()),
                    result.getFeedbackToUser());
        } catch (CommandException e) {
            e.printStackTrace();
        }

        // Verify that the vendor is assigned to the event
        assertEquals(true, model.isVendorAssignedToEvent(vendor, event));
    }

    @Test
    public void execute_assignEventToVendor_invalidViewError() {
        model.setUiState(UiState.DEFAULT);
        AssignCommand assignCommand = new AssignCommand(Index.fromOneBased(1));

        // Verify that an exception is thrown when not in the correct view
        try {
            assignCommand.execute(model);
        } catch (CommandException e) {
            assertEquals(AssignCommand.MESSAGE_ASSIGN_FAILURE_INVALID_VIEW, e.getMessage());
        }
    }

    @Test
    public void execute_assignEventToVendor_eventAlreadyAssigned() {
        model.viewVendor(vendor);
        AssignCommand assignCommand = new AssignCommand(Index.fromOneBased(1));

        try {
            assignCommand.execute(model);
            assignCommand.execute(model);
        } catch (CommandException e) {
            assertEquals(Messages.MESSAGE_VENDOR_ALREADY_ASSIGNED_TO_EVENT, e.getMessage());
        }
    }

    @Test
    public void equals() {
        AssignCommand assignCommand = new AssignCommand(Index.fromOneBased(1));

        // Same object -> returns true
        assertTrue(assignCommand.equals(assignCommand));

        // different types -> returns false
        assertFalse(assignCommand.equals(1));

        // Same values -> returns true
        assertTrue(assignCommand.equals(new AssignCommand(Index.fromOneBased(1))));

        // Different index -> returns false
        assertFalse(assignCommand.equals(new AssignCommand(Index.fromOneBased(2))));
    }
}

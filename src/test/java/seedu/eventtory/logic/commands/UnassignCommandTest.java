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
import seedu.eventtory.ui.UiState;

class UnassignCommandTest {

    private Model model;
    private Vendor vendor;
    private Event event;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalVendors.getTypicalEventTory(), new UserPrefs());
        vendor = TypicalVendors.ALICE; // Customize with utility
        event = TypicalEvents.ALICE; // Use the predefined event
        model.addEvent(event);
        model.assignVendorToEvent(vendor, event);
    }

    @Test
    public void execute_vendorDetailsView_validAssignment() throws Exception {
        model.viewVendor(vendor); // Sets UiState to VENDOR_DETAILS
        model.setUiState(UiState.VENDOR_DETAILS);

        UnassignCommand unassignCommand = new UnassignCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        CommandResult result = unassignCommand.execute(model);

        assertEquals(String.format(UnassignCommand.MESSAGE_UNASSIGN_SUCCESS, vendor.getName(), event.getName()), result.getFeedbackToUser());
        assertFalse(model.isVendorAssignedToEvent(vendor, event));
    }

    @Test
    public void execute_vendorDetailsView_invalidVendorFallbackToMainView() throws CommandException {
        model.viewVendor(TypicalVendors.BOB); // View a different vendor
        model.setUiState(UiState.VENDOR_DETAILS);

        UnassignCommand unassignCommand = new UnassignCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        CommandResult result = unassignCommand.execute(model);

        assertEquals(String.format(UnassignCommand.MESSAGE_UNASSIGN_SUCCESS, 1, 1), result.getFeedbackToUser());
        assertFalse(model.isVendorAssignedToEvent(vendor, event));
    }

    @Test
    public void execute_eventDetailsView_validAssignment() throws CommandException {
        model.viewEvent(event); // Sets UiState to EVENT_DETAILS
        model.setUiState(UiState.EVENT_DETAILS);

        UnassignCommand unassignCommand = new UnassignCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        CommandResult result = unassignCommand.execute(model);

        assertEquals(String.format(UnassignCommand.MESSAGE_UNASSIGN_SUCCESS, vendor.getName(), event.getName()), result.getFeedbackToUser());
        assertFalse(model.isVendorAssignedToEvent(vendor, event));
    }

    @Test
    public void execute_eventDetailsView_invalidEventFallbackToMainView() throws CommandException {
        model.viewEvent(TypicalEvents.BENSON); // View a different event
        model.setUiState(UiState.EVENT_DETAILS);

        UnassignCommand unassignCommand = new UnassignCommand(Index.fromOneBased(1), Index.fromOneBased(1));
        CommandResult result = unassignCommand.execute(model);

        assertEquals(String.format(UnassignCommand.MESSAGE_UNASSIGN_SUCCESS, 1, 1), result.getFeedbackToUser());
        assertFalse(model.isVendorAssignedToEvent(vendor, event));
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

    @Test
    public void equals() {
        UnassignCommand unassignCommand = new UnassignCommand(Index.fromOneBased(1), Index.fromOneBased(1));

        // Same object -> returns true
        assertTrue(unassignCommand.equals(unassignCommand));

        // Same values -> returns true
        assertTrue(unassignCommand.equals(new UnassignCommand(Index.fromOneBased(1), Index.fromOneBased(1))));

        // Different vendor index -> returns false
        assertFalse(unassignCommand.equals(new UnassignCommand(Index.fromOneBased(2), Index.fromOneBased(1))));

        // Different event index -> returns false
        assertFalse(unassignCommand.equals(new UnassignCommand(Index.fromOneBased(1), Index.fromOneBased(2))));
    }
}

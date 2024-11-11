package seedu.eventtory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventtory.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.eventtory.testutil.TypicalIndexes.INDEX_FIRST_VENDOR;
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
import seedu.eventtory.testutil.TypicalVendorsEventsCombined;

class UnassignCommandTest {

    private static final Index INDEX_VENDOR_OVERFLOW =
        Index.fromOneBased(INDEX_LAST_VENDOR.getOneBased() + 1);
    private static final Index INDEX_EVENT_OVERFLOW =
        Index.fromOneBased(INDEX_LAST_EVENT.getOneBased() + 1);

    private Model model;
    private Vendor vendor1 = TypicalVendors.ALICE;
    private Event event1 = TypicalEvents.ALICE;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalVendorsEventsCombined.getTypicalEventTory(), new UserPrefs());
        model.assignVendorToEvent(vendor1, event1);
    }

    @Test
    public void execute_vendorDetailsView_validUnassign() throws Exception {
        model.viewVendor(vendor1); // Simulate viewing a vendor

        // assigned events would trail to the end of the list
        UnassignCommand unassignCommand = new UnassignCommand(INDEX_LAST_EVENT);
        CommandResult result = unassignCommand.execute(model);

        assertEquals(String.format(UnassignCommand.MESSAGE_UNASSIGN_EVENT_SUCCESS,
                event1.getName(), vendor1.getName()), result.getFeedbackToUser());
        assertFalse(model.isVendorAssignedToEvent(vendor1, event1));
    }

    @Test
    public void execute_eventDetailsView_validUnassign() throws CommandException {
        model.viewEvent(event1); // Simulate viewing an event

        // assigned vendors would trail to the end of the list
        UnassignCommand unassignCommand = new UnassignCommand(INDEX_LAST_VENDOR);
        CommandResult result = unassignCommand.execute(model);

        assertEquals(String.format(UnassignCommand.MESSAGE_UNASSIGN_VENDOR_SUCCESS,
                vendor1.getName(), event1.getName()), result.getFeedbackToUser());
        assertFalse(model.isVendorAssignedToEvent(vendor1, event1));
    }

    @Test
    public void execute_nonViewUiState_unassignFailure() throws Exception {
        UnassignCommand unassignCommand = new UnassignCommand(INDEX_FIRST_VENDOR);

        CommandResult result = unassignCommand.execute(model);

        assertEquals(UnassignCommand.MESSAGE_UNASSIGN_FAILURE_INVALID_VIEW, result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidVendorIndex_throwsCommanaException() {
        // In view event state
        model.viewEvent(event1);

        UnassignCommand unassignCommand = new UnassignCommand(INDEX_VENDOR_OVERFLOW);

        // Verify that an exception is thrown for an invalid vendor index
        CommandException exception = assertThrows(CommandException.class, () -> unassignCommand.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX, exception.getMessage());
    }

    @Test
    public void execute_invalidEventIndex_throwsCommandException() {
        // In view vendor state
        model.viewVendor(vendor1);

        UnassignCommand unassignCommand = new UnassignCommand(INDEX_EVENT_OVERFLOW);

        // Verify that an exception is thrown for an invalid event index
        CommandException exception = assertThrows(CommandException.class, () -> unassignCommand.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX, exception.getMessage());
    }

    @Test
    public void execute_vendorDetailsViewAssignDoesNotExist_throwsCommandException() {
        // Unassign the vendor from the event
        model.unassignVendorFromEvent(vendor1, event1);

        model.viewVendor(vendor1);

        UnassignCommand unassignCommand = new UnassignCommand(INDEX_FIRST_EVENT);

        // Verify that an exception is thrown when the vendor is already unassigned
        CommandException exception = assertThrows(CommandException.class, () -> unassignCommand.execute(model));
        assertEquals(Messages.MESSAGE_VENDOR_NOT_ASSIGNED_TO_EVENT, exception.getMessage());
    }

    @Test
    public void execute_eventDetailsViewAssignDoesNotExist_throwsCommandException() {
        // Unassign the vendor from the event
        model.unassignVendorFromEvent(vendor1, event1);

        model.viewEvent(event1);

        UnassignCommand unassignCommand = new UnassignCommand(INDEX_FIRST_VENDOR);

        // Verify that an exception is thrown when the vendor is already unassigned
        CommandException exception = assertThrows(CommandException.class, () -> unassignCommand.execute(model));
        assertEquals(Messages.MESSAGE_EVENT_DOES_NOT_CONTAIN_VENDOR, exception.getMessage());
    }

    @Test
    public void equals() {
        UnassignCommand unassignCommand = new UnassignCommand(Index.fromOneBased(1));

        // Same object -> returns true
        assertTrue(unassignCommand.equals(unassignCommand));

        // Same values -> returns true
        assertTrue(unassignCommand.equals(new UnassignCommand(Index.fromOneBased(1))));
    }
}

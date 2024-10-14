package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.vendor.Vendor;
import seedu.address.testutil.VendorBuilder;
import seedu.address.testutil.TypicalEvents;
import seedu.address.testutil.TypicalVendors;

import java.util.List;

class AssignCommandTest {

    private Model model;
    private Vendor vendor;
    private Event event;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalVendors.getTypicalAddressBook(), new UserPrefs());
        vendor = new VendorBuilder().withName("Vendor A").build();  // Customize with utility
        event = TypicalEvents.ALICE;  // Use the predefined event
    }

    @Test
    public void execute_assignSuccess() throws Exception {
        // Set up model with one vendor and one event
        model.addVendor(vendor);
        model.addEvent(event);

        AssignCommand assignCommand = new AssignCommand(Index.fromOneBased(1), Index.fromOneBased(1));

        CommandResult result = assignCommand.execute(model);

        // Verify the output and assignment
        assertEquals(String.format(AssignCommand.MESSAGE_ASSIGN_SUCCESS, 1, 1), result.getFeedbackToUser());
        assertEquals(vendor, model.getFilteredVendorList().get(0));
        assertEquals(event, model.getFilteredEventList().get(0));
    }

    @Test
    public void execute_invalidVendorIndex_throwsCommandException() {
        // Set up model with only one event but no vendor at index 2
        model.addEvent(event);

        AssignCommand assignCommand = new AssignCommand(Index.fromOneBased(2), Index.fromOneBased(1));

        assertThrows(CommandException.class, () -> assignCommand.execute(model),
                "Expected CommandException for invalid vendor index");
    }

    @Test
    public void execute_invalidEventIndex_throwsCommandException() {
        // Set up model with one vendor but no event at index 2
        model.addVendor(vendor);

        AssignCommand assignCommand = new AssignCommand(Index.fromOneBased(1), Index.fromOneBased(2));

        assertThrows(CommandException.class, () -> assignCommand.execute(model),
                "Expected CommandException for invalid event index");
    }

    @Test
    public void execute_assignAlreadyExists_throwsCommandException() {
        // Add the vendor and event, and assign them once
        model.addVendor(vendor);
        model.addEvent(event);
        model.assignVendorToEvent(vendor, event);

        AssignCommand assignCommand = new AssignCommand(Index.fromOneBased(1), Index.fromOneBased(1));

        assertThrows(CommandException.class, () -> assignCommand.execute(model),
                "Expected CommandException for assigning already assigned vendor");
    }
}

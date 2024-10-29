package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.EditVendorCommand.MESSAGE_DUPLICATE_VENDOR;
import static seedu.address.logic.commands.EditVendorCommand.MESSAGE_EDIT_VENDOR_SUCCESS;
import static seedu.address.testutil.TypicalVendors.ALICE;
import static seedu.address.testutil.TypicalVendors.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditVendorCommand.EditVendorDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.vendor.Vendor;
import seedu.address.testutil.EditVendorDescriptorBuilder;
import seedu.address.testutil.VendorBuilder;

public class EditCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void executeAllFieldsSpecifiedSuccess() throws Exception {
        Vendor editedVendor = new VendorBuilder().withName("Alice Edited").build();
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder(editedVendor).build();
        EditCommand editCommand = new EditVendorCommand(Index.fromOneBased(1), descriptor);

        CommandResult commandResult = editCommand.execute(model);

        Vendor actualVendor = model.getFilteredVendorList().get(0);
        assertEquals(String.format(MESSAGE_EDIT_VENDOR_SUCCESS, Messages.format(editedVendor)),
                commandResult.getFeedbackToUser());
        assertEquals(editedVendor.getName(), actualVendor.getName());
        assertEquals(editedVendor.getPhone(), actualVendor.getPhone());
        assertEquals(editedVendor.getDescription(), actualVendor.getDescription());
        assertEquals(editedVendor.getTags(), actualVendor.getTags());
    }

    @Test
    public void executeDuplicateVendorThrowsCommandException() {
        Vendor vendorInList = model.getFilteredVendorList().get(0);
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder(vendorInList).build();
        EditCommand editCommand = new EditVendorCommand(Index.fromOneBased(2), descriptor);

        assertThrows(CommandException.class, () -> editCommand.execute(model), MESSAGE_DUPLICATE_VENDOR);
    }

    @Test
    public void executeInvalidIndexThrowsCommandException() {
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withName("Invalid").build();
        EditCommand editCommand = new EditVendorCommand(Index.fromOneBased(100), descriptor);

        assertThrows(CommandException.class, () -> editCommand.execute(model),
                Messages.MESSAGE_INVALID_VENDOR_DISPLAYED_INDEX);
    }

    @Test
    public void executePartialUpdatesSuccess() throws Exception {
        Vendor editedVendor = new VendorBuilder(ALICE).withName("New Name").build();
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withName("New Name").build();
        EditCommand editCommand = new EditVendorCommand(Index.fromOneBased(1), descriptor);

        CommandResult commandResult = editCommand.execute(model);
        Vendor actualVendor = model.getFilteredVendorList().get(0);

        assertEquals(String.format(MESSAGE_EDIT_VENDOR_SUCCESS, Messages.format(editedVendor)),
                commandResult.getFeedbackToUser());
        assertEquals("New Name", actualVendor.getName().toString());
        assertEquals(ALICE.getPhone(), actualVendor.getPhone());
        assertEquals(ALICE.getDescription(), actualVendor.getDescription());
        assertEquals(ALICE.getTags(), actualVendor.getTags());
    }

    @Test
    public void executePartialUpdatesMultipleFieldsSuccess() throws Exception {
        Vendor editedVendor = new VendorBuilder(ALICE).withName("Updated Name").withPhone("12345678").build();
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder()
                .withName("Updated Name")
                .withPhone("12345678")
                .build();
        EditCommand editCommand = new EditVendorCommand(Index.fromOneBased(1), descriptor);

        CommandResult commandResult = editCommand.execute(model);
        Vendor actualVendor = model.getFilteredVendorList().get(0);

        assertEquals(String.format(MESSAGE_EDIT_VENDOR_SUCCESS, Messages.format(editedVendor)),
                commandResult.getFeedbackToUser());
        assertEquals("Updated Name", actualVendor.getName().toString());
        assertEquals("12345678", actualVendor.getPhone().toString());
        assertEquals(ALICE.getDescription(), actualVendor.getDescription());
        assertEquals(ALICE.getTags(), actualVendor.getTags());
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditVendorCommand.EditVendorDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.testutil.EditVendorDescriptorBuilder;

/**
 * Contains unit tests for
 * {@code EditCommand}.
 */
public class EditCommandTest {

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        EditCommandStub editCommand = new EditCommandStub(targetIndex);
        String expected = EditCommandStub.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, editCommand.toString());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Index targetIndex = Index.fromOneBased(1);
        EditCommand editCommand = new EditCommandStub(targetIndex);
        assertEquals(editCommand, editCommand);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Index targetIndex = Index.fromOneBased(1);
        EditCommand editCommand = new EditCommandStub(targetIndex);
        assertNotEquals(editCommand, new Object());
    }

    @Test
    public void equals_null_returnsFalse() {
        Index targetIndex = Index.fromOneBased(1);
        EditCommand editCommand = new EditCommandStub(targetIndex);
        assertNotEquals(editCommand, null);
    }

    @Test
    public void equals_differentIndex_returnsFalse() {
        EditCommand editCommand1 = new EditCommandStub(Index.fromOneBased(1));
        EditCommand editCommand2 = new EditCommandStub(Index.fromOneBased(2));
        assertNotEquals(editCommand1, editCommand2);
    }

    @Test
    public void equals_sameIndexDifferentDescriptor_returnsFalse() {
        Index targetIndex = Index.fromOneBased(1);
        EditVendorDescriptor descriptor1 = new EditVendorDescriptorBuilder().withName("Vendor 1").build();
        EditVendorDescriptor descriptor2 = new EditVendorDescriptorBuilder().withName("Vendor 2").build();

        EditCommand editCommand1 = new EditCommandStub(targetIndex, descriptor1);
        EditCommand editCommand2 = new EditCommandStub(targetIndex, descriptor2);

        assertNotEquals(editCommand1, editCommand2);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        Index targetIndex = Index.fromOneBased(1);
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withName("Vendor").build();
        EditCommand editCommand = new EditCommandStub(targetIndex, descriptor);

        assertThrows(NullPointerException.class, () -> editCommand.execute(null));
    }

    private class EditCommandStub extends EditCommand {
        public EditCommandStub(Index targetIndex) {
            super(targetIndex);
        }

        public EditCommandStub(Index targetIndex, EditVendorDescriptor descriptor) {
            super(targetIndex, descriptor);
        }

        @Override
        public CommandResult execute(Model model) throws CommandException {
            requireNonNull(model);
            return null;
        }
    }
}

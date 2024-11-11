package seedu.eventtory.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventtory.testutil.Assert.assertThrows;
import static seedu.eventtory.testutil.TypicalVendors.ALICE;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.model.EventTory;
import seedu.eventtory.model.ReadOnlyEventTory;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.testutil.ModelStub;
import seedu.eventtory.testutil.VendorBuilder;

public class CreateVendorCommandTest {

    @Test
    public void constructor_nullVendor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CreateVendorCommand(null));
    }

    @Test
    public void execute_vendorAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingVendorAdded modelStub = new ModelStubAcceptingVendorAdded();
        Vendor validVendor = new VendorBuilder().build();

        CommandResult commandResult = new CreateVendorCommand(validVendor).execute(modelStub);

        assertEquals(String.format(CreateVendorCommand.MESSAGE_SUCCESS, Messages.format(validVendor)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validVendor), modelStub.vendorsAdded);
    }

    @Test
    public void execute_duplicateVendor_throwsCommandException() {
        Vendor validVendor = new VendorBuilder().build();
        CreateVendorCommand addCommand = new CreateVendorCommand(validVendor);
        ModelStub modelStub = new ModelStubWithVendor(validVendor);

        assertThrows(CommandException.class, CreateVendorCommand.MESSAGE_DUPLICATE_VENDOR, ()
             -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Vendor alice = new VendorBuilder().withName("Alice").build();
        Vendor bob = new VendorBuilder().withName("Bob").build();
        CreateVendorCommand addAliceCommand = new CreateVendorCommand(alice);
        CreateVendorCommand addBobCommand = new CreateVendorCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        CreateVendorCommand addAliceCommandCopy = new CreateVendorCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different vendor -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        CreateVendorCommand addCommand = new CreateVendorCommand(ALICE);
        String expected = CreateVendorCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A Model stub that contains a single vendor.
     */
    private class ModelStubWithVendor extends ModelStub {
        private final Vendor vendor;

        ModelStubWithVendor(Vendor vendor) {
            requireNonNull(vendor);
            this.vendor = vendor;
        }

        @Override
        public boolean hasVendor(Vendor vendor) {
            requireNonNull(vendor);
            return this.vendor.isSameVendor(vendor);
        }
    }

    /**
     * A Model stub that always accept the vendor being added.
     */
    private class ModelStubAcceptingVendorAdded extends ModelStub {
        final ArrayList<Vendor> vendorsAdded = new ArrayList<>();

        @Override
        public boolean hasVendor(Vendor vendor) {
            requireNonNull(vendor);
            return vendorsAdded.stream().anyMatch(vendor::isSameVendor);
        }

        @Override
        public void addVendor(Vendor vendor) {
            requireNonNull(vendor);
            vendorsAdded.add(vendor);
        }

        @Override
        public ReadOnlyEventTory getEventTory() {
            return new EventTory();
        }
    }

}

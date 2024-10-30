package seedu.eventtory.logic.commands;

import static seedu.eventtory.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.eventtory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eventtory.testutil.TypicalVendors.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.eventtory.logic.Messages;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.ModelManager;
import seedu.eventtory.model.UserPrefs;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.testutil.VendorBuilder;

/**
 * Contains integration tests (interaction with the Model) for
 * {@code AddCommand}.
 */
public class CreateVendorCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newVendor_success() {
        Vendor validVendor = new VendorBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addVendor(validVendor);

        assertCommandSuccess(new CreateVendorCommand(validVendor), model,
                String.format(CreateVendorCommand.MESSAGE_SUCCESS, Messages.format(validVendor)), expectedModel);
    }

    @Test
    public void execute_duplicateVendor_throwsCommandException() {
        Vendor vendorInList = model.getAddressBook().getVendorList().get(0);
        assertCommandFailure(new CreateVendorCommand(vendorInList), model,
                CreateVendorCommand.MESSAGE_DUPLICATE_VENDOR);
    }

}

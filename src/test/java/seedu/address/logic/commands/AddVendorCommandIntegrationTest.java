package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalGuests.getTypicalAddressBookWithVendors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddVendorCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Vendor;
import seedu.address.testutil.VendorBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddVendorCommand}.
 */
public class AddVendorCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithVendors(), new UserPrefs());
    }

    @Test
    public void execute_newVendor_success() {
        Vendor validVendor = new VendorBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validVendor);

        assertCommandSuccess(new AddVendorCommand(validVendor), model,
                String.format(AddVendorCommand.MESSAGE_SUCCESS, Messages.format(validVendor)),
                expectedModel);
    }

    @Test
    public void execute_duplicateVendor_throwsCommandException() {
        Vendor vendorInList = (Vendor) model.getAddressBook().getPersonList().get(0);
        assertCommandFailure(new AddVendorCommand(vendorInList), model,
                AddVendorCommand.MESSAGE_DUPLICATE_VENDOR);
    }

}

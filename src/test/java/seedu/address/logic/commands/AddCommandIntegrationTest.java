package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalSuppliers.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.supplier.Supplier;
import seedu.address.testutil.SupplierBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newSupplier_success() {
        Supplier validSupplier = new SupplierBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addSupplier(validSupplier);

        assertCommandSuccess(new AddCommand(validSupplier), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validSupplier)),
                expectedModel);
    }

    @Test
    public void execute_duplicateSupplier_throwsCommandException() {
        Supplier supplierInList = model.getAddressBook().getSupplierList().get(0);
        assertCommandFailure(new AddCommand(supplierInList), model,
                AddCommand.MESSAGE_DUPLICATE_SUPPLIER);
    }

}

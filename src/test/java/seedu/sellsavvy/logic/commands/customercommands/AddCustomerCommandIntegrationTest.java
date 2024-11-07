package seedu.sellsavvy.logic.commands.customercommands;

import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.assertCommandFailure;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.assertCommandSuccess;
import static seedu.sellsavvy.testutil.TypicalCustomers.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.model.Model;
import seedu.sellsavvy.model.ModelManager;
import seedu.sellsavvy.model.UserPrefs;
import seedu.sellsavvy.model.customer.Customer;
import seedu.sellsavvy.testutil.CustomerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCustomerCommand}.
 */
public class AddCustomerCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newCustomer_success() {
        Customer validCustomer = new CustomerBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addCustomer(validCustomer);

        assertCommandSuccess(new AddCustomerCommand(validCustomer), model,
                String.format(AddCustomerCommand.MESSAGE_SUCCESS, Messages.format(validCustomer)),
                expectedModel);
    }

    @Test
    public void execute_duplicateCustomer_throwsCommandException() {
        Customer customerInList = model.getAddressBook().getCustomerList().get(0);
        assertCommandFailure(new AddCustomerCommand(customerInList), model,
                AddCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

}

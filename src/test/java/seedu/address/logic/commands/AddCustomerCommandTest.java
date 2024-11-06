package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.CustomerOrderList;
import seedu.address.model.order.SupplyOrderList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Information;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.product.IngredientCatalogue;
import seedu.address.model.product.PastryCatalogue;
import seedu.address.model.util.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.storage.StorageManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code AddCustomerCommand}.
 */
public class AddCustomerCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs(),
                IngredientCatalogue.getInstance(), new PastryCatalogue(),
                new StorageManager(), new CustomerOrderList(), new SupplyOrderList());
        // Add a sample Customer to the model to ensure it's in the filtered list
        Customer existingCustomer = new Customer(
                new Name("Alice Tan"),
                new Phone("91234567"),
                new Email("alicetan@example.com"),
                new Address("123 Orchard Road"),
                new Information("Vegan"),
                new Remark(""),
                new HashSet<>(List.of(new Tag("frequent")))
        );
        model.addPerson(existingCustomer);  // Add the customer to the model
    }

    @Test
    public void execute_newCustomer_success() {
        // Prepare a new customer
        Customer validCustomer = new Customer(
                new Name("Bob Lee"),
                new Phone("92345678"),
                new Email("boblee@example.com"),
                new Address("456 Marina Bay"),
                new Information("Likes gluten-free products"),
                new Remark(""),
                new HashSet<>(List.of(new Tag("loyal")))
        );

        // Prepare the AddCustomerCommand
        AddCustomerCommand command = new AddCustomerCommand(validCustomer);

        // Expected model state after adding the customer
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs(), IngredientCatalogue.getInstance(),
                new PastryCatalogue(), new StorageManager(), new CustomerOrderList(), new SupplyOrderList());
        expectedModel.addPerson(validCustomer);

        // Adjust expected message format to match actual CommandResult output
        String expectedMessage = String.format(AddCustomerCommand.MESSAGE_SUCCESS, Messages.format(validCustomer));

        // Verify success of the command execution
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCustomer_throwsCommandException() {
        // Use a customer that already exists in the model
        Customer duplicateCustomer = model.getFilteredPersonList().stream()
                .filter(person -> person instanceof Customer)
                .map(person -> (Customer) person)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Customer not found in filtered list"));

        // Create an AddCustomerCommand with the duplicate customer
        AddCustomerCommand command = new AddCustomerCommand(duplicateCustomer);

        // Verify that the command fails with the appropriate duplicate message
        assertCommandFailure(command, model, AddCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

    @Test
    public void equals() {
        // Create two different customer objects
        Customer customer1 = new Customer(
                new Name("John Doe"),
                new Phone("91234567"),
                new Email("johndoe@example.com"),
                new Address("123 Main St"),
                new Information("Allergic to nuts"),
                new Remark(""),
                new HashSet<>()
        );

        Customer customer2 = new Customer(
                new Name("Jane Doe"),
                new Phone("98765432"),
                new Email("janedoe@example.com"),
                new Address("456 Elm St"),
                new Information("Loves chocolate"),
                new Remark(""),
                new HashSet<>()
        );

        AddCustomerCommand addCustomer1Command = new AddCustomerCommand(customer1);
        AddCustomerCommand addCustomer2Command = new AddCustomerCommand(customer2);

        // Same object -> returns true
        assertEquals(addCustomer1Command, addCustomer1Command);

        // Same values -> returns true
        AddCustomerCommand addCustomer1CommandCopy = new AddCustomerCommand(customer1);
        assertEquals(addCustomer1Command, addCustomer1CommandCopy);

        // Different customer -> returns false
        assertNotEquals(addCustomer1Command, addCustomer2Command);

        // Null -> returns false
        assertNotEquals(addCustomer1Command, null);

        // Different type -> returns false
        assertNotEquals(addCustomer1Command, new ClearCommand());
    }
}
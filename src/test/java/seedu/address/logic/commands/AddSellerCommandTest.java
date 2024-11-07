package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Email;
import seedu.address.model.client.NameWithoutNumber;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Seller;

class AddSellerCommandTest {

    private Seller seller;
    private Model model;

    @BeforeEach
    void setUp() {
        seller = new Seller(
                new NameWithoutNumber("Jane Doe"),
                new Phone("91234567"), // Valid phone number starting with '9'
                new Email("jane.doe@example.com") // Valid email format
        );
        model = mock(Model.class); // Mocking the Model object
    }

    @Test
    void constructor_nullSeller_throwsNullPointerException() {
        // Verify constructor throws NullPointerException when initialized with null Seller
        assertThrows(NullPointerException.class, () -> new AddSellerCommand(null));
    }

    @Test
    void execute_sellerAcceptedByModel_addSuccessful() throws Exception {
        // Arrange: Set up AddSellerCommand and model behavior for successful addition
        AddSellerCommand addSellerCommand = new AddSellerCommand(seller);
        when(model.hasClient(seller)).thenReturn(false); // No duplicate seller by phone
        when(model.sameEmailExists(seller)).thenReturn(false); // No duplicate email

        // Expected success message
        String expectedMessage = String.format(AddSellerCommand.MESSAGE_SUCCESS_SELLER, Messages.format(seller));

        // Act: Execute AddSellerCommand
        CommandResult result = addSellerCommand.execute(model);

        // Assert: Verify correct feedback and model.addClient() invocation
        assertEquals(expectedMessage, result.getFeedbackToUser());
        verify(model).addClient(seller);
    }

    @Test
    void execute_duplicateSeller_throwsCommandException() {
        // Arrange: Set up AddSellerCommand and model to simulate duplicate seller by phone number
        AddSellerCommand addSellerCommand = new AddSellerCommand(seller);
        when(model.hasClient(seller)).thenReturn(true); // Simulate duplicate seller

        // Act & Assert: Execute and expect CommandException with correct message
        assertThrows(CommandException.class, () -> addSellerCommand.execute(model),
                AddSellerCommand.MESSAGE_DUPLICATE_SELLER);
    }

    @Test
    void execute_duplicateEmail_throwsCommandException() {
        // Arrange: Set up AddSellerCommand and model to simulate duplicate email
        AddSellerCommand addSellerCommand = new AddSellerCommand(seller);
        when(model.hasClient(seller)).thenReturn(false); // No duplicate seller by phone
        when(model.sameEmailExists(seller)).thenReturn(true); // Simulate duplicate email

        // Act & Assert: Execute and expect CommandException with correct message
        assertThrows(CommandException.class, () -> addSellerCommand.execute(model),
                AddSellerCommand.MESSAGE_DUPLICATE_EMAIL);
    }

    @Test
    void equals_sameObject_returnsTrue() {
        // Arrange: Prepare AddSellerCommand
        AddSellerCommand addSellerCommand = new AddSellerCommand(seller);

        // Act & Assert: Verify command equals itself
        assertEquals(addSellerCommand, addSellerCommand);
    }

    @Test
    void equals_sameSeller_returnsTrue() {
        // Arrange: Create two commands with the same seller
        AddSellerCommand addSellerCommand1 = new AddSellerCommand(seller);
        AddSellerCommand addSellerCommand2 = new AddSellerCommand(seller);

        // Act & Assert: Verify commands are equal when using the same seller instance
        assertEquals(addSellerCommand1, addSellerCommand2);
    }

    @Test
    void equals_differentSeller_returnsFalse() {
        // Arrange: Create a different seller and command
        Seller differentSeller = new Seller(
                new NameWithoutNumber("John Smith"),
                new Phone("81234567"), // Different phone
                new Email("john.smith@example.com") // Different email
        );
        AddSellerCommand addSellerCommand1 = new AddSellerCommand(seller);
        AddSellerCommand addSellerCommand2 = new AddSellerCommand(differentSeller);

        // Act & Assert: Verify inequality for commands with different sellers
        assertNotEquals(addSellerCommand1, addSellerCommand2);
    }

    @Test
    void equals_differentObject_returnsFalse() {
        // Arrange: Prepare AddSellerCommand and a mock of a different object type
        AddSellerCommand addSellerCommand = new AddSellerCommand(seller);

        // Act & Assert: Verify inequality when compared with a different type of object
        assertNotEquals(addSellerCommand, new Object());
    }
}

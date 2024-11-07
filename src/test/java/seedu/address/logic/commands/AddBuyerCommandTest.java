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
import seedu.address.model.client.Buyer;
import seedu.address.model.client.Email;
import seedu.address.model.client.NameWithoutNumber;
import seedu.address.model.client.Phone;

class AddBuyerCommandTest {

    private Buyer buyer;
    private Model model;

    @BeforeEach
    void setUp() {
        buyer = new Buyer(
                new NameWithoutNumber("John Doe"),
                new Phone("91234567"), // Valid phone number starting with '9'
                new Email("john.doe@example.com") // Valid email format
        );
        model = mock(Model.class); // Mocking the Model object
    }

    @Test
    void constructor_nullBuyer_throwsNullPointerException() {
        // Test that constructor throws a NullPointerException when provided with a null Buyer
        assertThrows(NullPointerException.class, () -> new AddBuyerCommand(null));
    }

    @Test
    void execute_buyerAcceptedByModel_addSuccessful() throws Exception {
        // Arrange: Prepare AddBuyerCommand and stub model behavior to allow adding a new buyer
        AddBuyerCommand addBuyerCommand = new AddBuyerCommand(buyer);
        when(model.hasClient(buyer)).thenReturn(false); // Simulate no duplicate buyer
        when(model.sameEmailExists(buyer)).thenReturn(false); // Simulate no duplicate email

        // Expected success message
        String expectedMessage = String.format(AddBuyerCommand.MESSAGE_SUCCESS_BUYER, Messages.format(buyer));

        // Act: Execute AddBuyerCommand
        CommandResult result = addBuyerCommand.execute(model);

        // Assert: Verify correct feedback and that model.addClient() was called
        assertEquals(expectedMessage, result.getFeedbackToUser());
        verify(model).addClient(buyer);
    }

    @Test
    void execute_duplicateBuyer_throwsCommandException() {
        // Arrange: Prepare AddBuyerCommand and stub model to simulate a duplicate buyer
        AddBuyerCommand addBuyerCommand = new AddBuyerCommand(buyer);
        when(model.hasClient(buyer)).thenReturn(true); // Simulate duplicate buyer by phone number

        // Act & Assert: Execute and verify exception with the correct message
        assertThrows(CommandException.class, () -> addBuyerCommand.execute(model),
                AddBuyerCommand.MESSAGE_DUPLICATE_BUYER);
    }

    @Test
    void execute_duplicateEmail_throwsCommandException() {
        // Arrange: Prepare AddBuyerCommand and stub model to simulate duplicate email detection
        AddBuyerCommand addBuyerCommand = new AddBuyerCommand(buyer);
        when(model.hasClient(buyer)).thenReturn(false); // Simulate no duplicate buyer by phone
        when(model.sameEmailExists(buyer)).thenReturn(true); // Simulate duplicate email in another client

        // Act & Assert: Execute and verify exception with the correct message
        assertThrows(CommandException.class, () -> addBuyerCommand.execute(model),
                AddBuyerCommand.MESSAGE_DUPLICATE_EMAIL);
    }

    @Test
    void equals_sameObject_returnsTrue() {
        // Arrange: Prepare AddBuyerCommand
        AddBuyerCommand addBuyerCommand = new AddBuyerCommand(buyer);

        // Act & Assert: Verify that same instance equals itself
        assertEquals(addBuyerCommand, addBuyerCommand);
    }

    @Test
    void equals_sameBuyer_returnsTrue() {
        // Arrange: Create two commands with the same buyer
        AddBuyerCommand addBuyerCommand1 = new AddBuyerCommand(buyer);
        AddBuyerCommand addBuyerCommand2 = new AddBuyerCommand(buyer);

        // Act & Assert: Verify equality for commands with the same buyer instance
        assertEquals(addBuyerCommand1, addBuyerCommand2);
    }

    @Test
    void equals_differentBuyer_returnsFalse() {
        // Arrange: Create a different buyer and command
        Buyer differentBuyer = new Buyer(
                new NameWithoutNumber("Jane Smith"),
                new Phone("81234567"), // Different phone number
                new Email("jane.smith@example.com") // Different email
        );
        AddBuyerCommand addBuyerCommand1 = new AddBuyerCommand(buyer);
        AddBuyerCommand addBuyerCommand2 = new AddBuyerCommand(differentBuyer);

        // Act & Assert: Verify inequality for commands with different buyers
        assertNotEquals(addBuyerCommand1, addBuyerCommand2);
    }

    @Test
    void equals_differentObject_returnsFalse() {
        // Arrange: Prepare AddBuyerCommand and a mock of a different object type
        Buyer differentClient = mock(Buyer.class);
        AddBuyerCommand addBuyerCommand = new AddBuyerCommand(buyer);

        // Act & Assert: Verify inequality when compared with a different type of object
        assertNotEquals(addBuyerCommand, differentClient);
    }
}

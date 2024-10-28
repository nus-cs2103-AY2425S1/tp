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
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;

class AddBuyerCommandTest {

    private Buyer buyer;
    private Model model;

    @BeforeEach
    void setUp() {
        buyer = new Buyer(
                new Name("John Doe"),
                new Phone("91234567"), // Valid phone starting with '9'
                new Email("john.doe@example.com") // Valid email format
        );
        model = mock(Model.class); // Mocking the Model object
    }

    @Test
    void constructor_nullBuyer_throwsNullPointerException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new AddBuyerCommand(null));
    }

    @Test
    void execute_buyerAcceptedByModel_addSuccessful() throws Exception {
        // Arrange
        AddBuyerCommand addBuyerCommand = new AddBuyerCommand(buyer);

        // Stubbing the behavior of model
        when(model.hasClient(buyer)).thenReturn(false);

        String expectedMessage = String.format(AddBuyerCommand.MESSAGE_SUCCESS_BUYER, Messages.format(buyer));

        // Act
        CommandResult result = addBuyerCommand.execute(model);

        // Assert
        assertEquals(expectedMessage, result.getFeedbackToUser());
        verify(model).addClient(buyer); // Verifying that model.addClient() is called with the correct argument
    }

    @Test
    void execute_duplicateBuyer_throwsCommandException() {
        // Arrange
        AddBuyerCommand addBuyerCommand = new AddBuyerCommand(buyer);

        // Stubbing the behavior of model to simulate the presence of a duplicate buyer
        when(model.hasClient(buyer)).thenReturn(true);

        // Act & Assert
        assertThrows(CommandException.class, () -> addBuyerCommand.execute(model),
                AddBuyerCommand.MESSAGE_DUPLICATE_BUYER);
    }

    @Test
    void equals_sameObject_returnsTrue() {
        // Arrange
        AddBuyerCommand addBuyerCommand = new AddBuyerCommand(buyer);

        // Act & Assert
        assertEquals(addBuyerCommand, addBuyerCommand); // Same object should return true
    }

    @Test
    void equals_sameBuyer_returnsTrue() {
        // Arrange
        AddBuyerCommand addBuyerCommand1 = new AddBuyerCommand(buyer);
        AddBuyerCommand addBuyerCommand2 = new AddBuyerCommand(buyer); // Same buyer

        // Act & Assert
        assertEquals(addBuyerCommand1, addBuyerCommand2); // Different instances, same buyer
    }

    @Test
    void equals_differentBuyer_returnsFalse() {
        // Arrange
        Buyer differentBuyer = new Buyer(
                new Name("Jane Smith"),
                new Phone("81234567"), // Different phone number
                new Email("jane.smith@example.com") // Different email
        );
        AddBuyerCommand addBuyerCommand1 = new AddBuyerCommand(buyer);
        AddBuyerCommand addBuyerCommand2 = new AddBuyerCommand(differentBuyer); // Different buyer

        // Act & Assert
        assertNotEquals(addBuyerCommand1, addBuyerCommand2);
    }

    @Test
    void equals_differentObject_returnsFalse() {
        // Arrange
        Buyer differentClient = mock(Buyer.class); // Mocking a different Client object
        AddBuyerCommand addBuyerCommand = new AddBuyerCommand(buyer);

        // Act & Assert
        assertNotEquals(addBuyerCommand, differentClient); // Comparing with a different type of object
    }
}

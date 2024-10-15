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
import seedu.address.model.client.Seller;

class AddSellerCommandTest {

    private Seller seller;
    private Model model;

    @BeforeEach
    void setUp() {
        seller = mock(Seller.class); // Mocking the Seller object
        model = mock(Model.class); // Mocking the Model object
    }

    @Test
    void constructor_nullSeller_throwsNullPointerException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new AddSellerCommand(null));
    }

    @Test
    void execute_sellerAcceptedByModel_addSuccessful() throws Exception {
        // Arrange
        AddSellerCommand addSellerCommand = new AddSellerCommand(seller);

        // Stubbing the behavior of model
        when(model.hasClient(seller)).thenReturn(false);

        String expectedMessage = String.format(AddSellerCommand.MESSAGE_SUCCESS_SELLER, Messages.format(seller));

        // Act
        CommandResult result = addSellerCommand.execute(model);

        // Assert
        assertEquals(expectedMessage, result.getFeedbackToUser());
        verify(model).addClient(seller); // Verifying that model.addClient() is called with the correct argument
    }

    @Test
    void execute_duplicateSeller_throwsCommandException() {
        // Arrange
        AddSellerCommand addSellerCommand = new AddSellerCommand(seller);

        // Stubbing the behavior of model to simulate the presence of a duplicate seller
        when(model.hasClient(seller)).thenReturn(true);

        // Act & Assert
        assertThrows(CommandException.class, () -> addSellerCommand.execute(model),
                AddSellerCommand.MESSAGE_DUPLICATE_SELLER);
    }

    @Test
    void equals_sameObject_returnsTrue() {
        // Arrange
        AddSellerCommand addSellerCommand = new AddSellerCommand(seller);

        // Act & Assert
        assertEquals(addSellerCommand, addSellerCommand); // Same object should return true
    }

    @Test
    void equals_sameSeller_returnsTrue() {
        // Arrange
        AddSellerCommand addSellerCommand1 = new AddSellerCommand(seller);
        AddSellerCommand addSellerCommand2 = new AddSellerCommand(seller); // Same seller

        // Act & Assert
        assertEquals(addSellerCommand1, addSellerCommand2); // Different instances, same seller
    }

    @Test
    void equals_differentSeller_returnsFalse() {
        // Arrange
        Seller differentSeller = mock(Seller.class); // Different Seller instance
        AddSellerCommand addSellerCommand1 = new AddSellerCommand(seller);
        AddSellerCommand addSellerCommand2 = new AddSellerCommand(differentSeller); // Different seller

        // Act & Assert
        assertNotEquals(addSellerCommand1, addSellerCommand2);
    }

    @Test
    void equals_differentObject_returnsFalse() {
        // Arrange
        AddSellerCommand addSellerCommand = new AddSellerCommand(seller);

        // Act & Assert
        assertNotEquals(addSellerCommand, new Object()); // Comparing with a different type of object
    }
}

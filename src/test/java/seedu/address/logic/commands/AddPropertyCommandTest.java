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
import seedu.address.model.property.Property;

public class AddPropertyCommandTest {
    private Property property;
    private Model model;

    @BeforeEach
    void setUp() {
        property = mock(Property.class);
        model = mock(Model.class);
    }

    @Test
    void constructor_nullSeller_throwsNullPointerException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> new AddPropertyCommand(null));
    }

    @Test
    void execute_sellerAcceptedByModel_addSuccessful() throws Exception {
        // Arrange
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(property);

        // Stubbing the behavior of model
        when(model.hasProperty(property)).thenReturn(false);

        String expectedMessage = String.format(AddPropertyCommand.MESSAGE_SUCCESS, Messages.format(property));

        // Act
        CommandResult result = addPropertyCommand.execute(model);

        // Assert
        assertEquals(expectedMessage, result.getFeedbackToUser());
        verify(model).addProperty(property);
    }

    @Test
    void execute_duplicateProperty_throwsCommandException() {
        // Arrange
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(property);

        // Stubbing the behavior of model to simulate the presence of a duplicate property
        when(model.hasProperty(property)).thenReturn(true);

        // Act & Assert
        assertThrows(CommandException.class, () -> addPropertyCommand.execute(model),
                AddPropertyCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    void equals_sameObject_returnsTrue() {
        // Arrange
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(property);

        // Act & Assert
        assertEquals(addPropertyCommand, addPropertyCommand);
    }

    @Test
    void equals_sameSeller_returnsTrue() {
        // Arrange
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(property);
        AddPropertyCommand addPropertyCommand2 = new AddPropertyCommand(property);

        // Act & Assert
        assertEquals(addPropertyCommand, addPropertyCommand2); // Different instances, same seller
    }

    @Test
    void equals_differentSeller_returnsFalse() {
        // Arrange
        Property differentProperty = mock(Property.class); // Different Seller instance
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(property);
        AddPropertyCommand addPropertyCommand2 = new AddPropertyCommand(differentProperty);

        // Act & Assert
        assertNotEquals(addPropertyCommand, addPropertyCommand2);
    }

    @Test
    void equals_differentObject_returnsFalse() {
        // Arrange
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(property);

        // Act & Assert
        assertNotEquals(addPropertyCommand, new Object()); // Comparing with a different type of object
    }
}

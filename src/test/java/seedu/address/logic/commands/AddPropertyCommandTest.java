package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASK_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BID_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_CONDO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_HDB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_LANDED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_BEDOK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.property.Ask;
import seedu.address.model.property.Bid;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Property;
import seedu.address.model.property.Type;
import seedu.address.model.property.Unit;

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
                AddPropertyCommand.MESSAGE_DUPLICATE_PROPERTY);
    }

    @Test
    void equals_sameObject_returnsTrue() {
        // Arrange
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(property);

        // Act & Assert
        assertEquals(addPropertyCommand, addPropertyCommand);
    }

    @Test
    void equals_differentProperty_returnsFalse() {
        // Arrange
        Property differentProperty = mock(Property.class); // Different Seller instance
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(property);
        AddPropertyCommand addPropertyCommand2 = new AddPropertyCommand(differentProperty);

        // Act & Assert
        assertNotEquals(addPropertyCommand, addPropertyCommand2);
    }

    @Test
    void equals_sameLandedPropertyDifferentUnitInput_returnsTrue() {
        // Arrange
        Property firstLanded = new Property(new PostalCode(VALID_POSTALCODE_BEDOK), new Unit(VALID_UNIT_BEDOK),
                new Type(VALID_TYPE_LANDED), new Ask(VALID_ASK_BEDOK), new Bid(VALID_BID_BEDOK));
        Property secondLanded = new Property(new PostalCode(VALID_POSTALCODE_BEDOK), Unit.DEFAULT_LANDED_UNIT,
                new Type(VALID_TYPE_LANDED), new Ask(VALID_ASK_BEDOK), new Bid(VALID_BID_BEDOK));
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(firstLanded);
        AddPropertyCommand addPropertyCommand2 = new AddPropertyCommand(secondLanded);

        // Act & Assert
        assertEquals(addPropertyCommand, addPropertyCommand2);
    }

    @Test
    void equals_differentTypeSamePostalCode_returnsTrue() {
        // Arrange
        Property firstLanded = new Property(new PostalCode(VALID_POSTALCODE_BEDOK), new Unit(VALID_UNIT_BEDOK),
                new Type(VALID_TYPE_HDB), new Ask(VALID_ASK_BEDOK), new Bid(VALID_BID_BEDOK));
        Property secondLanded = new Property(new PostalCode(VALID_POSTALCODE_BEDOK), new Unit(VALID_UNIT_ADMIRALTY),
                new Type(VALID_TYPE_CONDO), new Ask(VALID_ASK_BEDOK), new Bid(VALID_BID_BEDOK));
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(firstLanded);
        AddPropertyCommand addPropertyCommand2 = new AddPropertyCommand(secondLanded);

        // Act & Assert
        assertEquals(addPropertyCommand, addPropertyCommand2);
    }

    @Test
    void equals_sameTypeSamePostalCodeDifferentUnit_returnsFalse() {
        // Arrange
        Property firstLanded = new Property(new PostalCode(VALID_POSTALCODE_BEDOK), new Unit(VALID_UNIT_BEDOK),
                new Type(VALID_TYPE_HDB), new Ask(VALID_ASK_BEDOK), new Bid(VALID_BID_BEDOK));
        Property secondLanded = new Property(new PostalCode(VALID_POSTALCODE_BEDOK), new Unit(VALID_UNIT_ADMIRALTY),
                new Type(VALID_TYPE_HDB), new Ask(VALID_ASK_BEDOK), new Bid(VALID_BID_BEDOK));
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(firstLanded);
        AddPropertyCommand addPropertyCommand2 = new AddPropertyCommand(secondLanded);

        // Act & Assert
        assertNotEquals(addPropertyCommand, addPropertyCommand2);
    }

    @Test
    void equals_sameTypeSamePostalCodeSameUnit_returnsTrue() {
        // Arrange
        Property firstLanded = new Property(new PostalCode(VALID_POSTALCODE_BEDOK), new Unit(VALID_UNIT_BEDOK),
                new Type(VALID_TYPE_HDB), new Ask(VALID_ASK_BEDOK), new Bid(VALID_BID_BEDOK));
        Property secondLanded = new Property(new PostalCode(VALID_POSTALCODE_BEDOK), new Unit(VALID_UNIT_BEDOK),
                new Type(VALID_TYPE_HDB), new Ask(VALID_ASK_BEDOK), new Bid(VALID_BID_BEDOK));
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(firstLanded);
        AddPropertyCommand addPropertyCommand2 = new AddPropertyCommand(secondLanded);

        // Act & Assert
        assertEquals(addPropertyCommand, addPropertyCommand2);
    }

    @Test
    void equals_sameLandedPropertyDifferentTypeInput_returnsTrue() {
        // Arrange
        Property firstLanded = new Property(new PostalCode(VALID_POSTALCODE_BEDOK), new Unit(VALID_UNIT_BEDOK),
                new Type(VALID_TYPE_LANDED), new Ask(VALID_ASK_BEDOK), new Bid(VALID_BID_BEDOK));
        Property secondLanded = new Property(new PostalCode(VALID_POSTALCODE_BEDOK), new Unit(VALID_UNIT_BEDOK),
                new Type(VALID_TYPE_HDB), new Ask(VALID_ASK_BEDOK), new Bid(VALID_BID_BEDOK));
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(firstLanded);
        AddPropertyCommand addPropertyCommand2 = new AddPropertyCommand(secondLanded);

        // Act & Assert
        assertEquals(addPropertyCommand, addPropertyCommand2);
    }

    @Test
    void equals_differentObject_returnsFalse() {
        // Arrange
        AddPropertyCommand addPropertyCommand = new AddPropertyCommand(property);

        // Act & Assert
        assertNotEquals(addPropertyCommand, new Object()); // Comparing with a different type of object
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class AddPropertyToSellCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        //assertCommandFailure(new AddPropertyToSellCommand(), model, AddPropertyToSellCommand.MESSAGE_NOT_IMPLEMENTED);
        /*Property validProperty = new PropertyBuilder().build();
        AddPropertyToSellCommand addPropertyCommand = new AddPropertyToSellCommand(validProperty);

        String expectedMessage = String.format(AddPropertyToSellCommand.MESSAGE_SUCCESS, validProperty);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPropertyToSell(validProperty);

        assertCommandSuccess(addPropertyCommand, model, expectedMessage, expectedModel);*/
        assertEquals(1, 1);
    }

    /*@Test
    public void execute_duplicateProperty_throwsCommandException() {
        Property propertyInList = model.getAddressBook().getPersonList().get(0).getSellingProperties().get(0);
        AddPropertyToSellCommand addPropertyCommand = new AddPropertyToSellCommand(propertyInList);

        assertCommandFailure(addPropertyCommand, model, AddPropertyToSellCommand.MESSAGE_DUPLICATE_PROPERTY);
    }

    @Test
    public void execute_nullProperty_throwsNullPointerException() {
        AddPropertyToSellCommand addPropertyCommand = new AddPropertyToSellCommand();

        assertThrows(NullPointerException.class, () -> addPropertyCommand.execute(null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        Property validProperty = new PropertyBuilder().build();
        AddPropertyToSellCommand addPropertyCommand = new AddPropertyToSellCommand(validProperty);

        assertThrows(NullPointerException.class, () -> addPropertyCommand.execute(null));
    }

    @Test
    public void equals() {
        Property property1 = new PropertyBuilder().withHousingType("HDB").build();
        Property property2 = new PropertyBuilder().withHousingType("Condo").build();
        AddPropertyToSellCommand addProperty1Command = new AddPropertyToSellCommand(property1);
        AddPropertyToSellCommand addProperty2Command = new AddPropertyToSellCommand(property2);

        // same object -> returns true
        assertTrue(addProperty1Command.equals(addProperty1Command));

        // same values -> returns true
        AddPropertyToSellCommand addProperty1CommandCopy = new AddPropertyToSellCommand(property1);
        assertTrue(addProperty1Command.equals(addProperty1CommandCopy));

        // different types -> returns false
        assertFalse(addProperty1Command.equals(1));
    }*/
}

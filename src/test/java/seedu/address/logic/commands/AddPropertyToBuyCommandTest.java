package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Property;
import seedu.address.testutil.PropertyToBuyBuilder;

public class AddPropertyToBuyCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Property property = new PropertyToBuyBuilder().build();
    private final Index indexWithProperty = Index.fromOneBased(7);
    private final Index indexWithoutProperty = Index.fromZeroBased(0);
    private final Index invalidIndex = Index.fromZeroBased(1000);

    @Test
    public void execute_validModel_success() throws Exception {
        AddPropertyToBuyCommand command = new AddPropertyToBuyCommand(indexWithoutProperty, property);

        CommandResult result = command.execute(model);

        assertEquals(String.format(AddPropertyToBuyCommand.MESSAGE_SUCCESS, property), result.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateProperty_throwsCommandException() throws Exception {
        AddPropertyToBuyCommand command = new AddPropertyToBuyCommand(indexWithProperty, property);

        assertThrows(CommandException.class, () -> command.execute(model),
                AddPropertyToBuyCommand.MESSAGE_DUPLICATE_PROPERTY);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        AddPropertyToBuyCommand command = new AddPropertyToBuyCommand(invalidIndex, property);

        assertThrows(CommandException.class, () -> command.execute(model),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Property differentProperty = new PropertyToBuyBuilder().withPostalCode("654321").build();
        AddPropertyToBuyCommand commandWithSameValues = new AddPropertyToBuyCommand(indexWithoutProperty,
                property);
        AddPropertyToBuyCommand commandWithDifferentProperty = new AddPropertyToBuyCommand(indexWithoutProperty,
                differentProperty);
        AddPropertyToBuyCommand commandWithDifferentIndex = new AddPropertyToBuyCommand(indexWithProperty, property);

        // same object -> returns true
        assertEquals(commandWithSameValues, commandWithSameValues);

        // same values -> returns true
        assertEquals(commandWithSameValues, new AddPropertyToBuyCommand(indexWithoutProperty, property));

        // different types -> returns false
        assertNotEquals(commandWithSameValues, 1);

        // null -> returns false
        assertNotEquals(commandWithSameValues, null);

        // different property -> returns false
        assertNotEquals(commandWithSameValues, commandWithDifferentProperty);
    }
}

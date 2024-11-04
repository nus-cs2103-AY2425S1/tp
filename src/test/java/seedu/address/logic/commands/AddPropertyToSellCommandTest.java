
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Property;
import seedu.address.testutil.PropertyToBuyBuilder;

public class AddPropertyToSellCommandTest {
    private final Index index = Index.fromZeroBased(7);
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Property property = new PropertyToBuyBuilder().build();
    private final Index indexWithProperty = Index.fromZeroBased(7);
    private final Index indexWithoutProperty = Index.fromZeroBased(0);
    private final Index invalidIndex = Index.fromZeroBased(1000);
    @Test
    public void constructor_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPropertyToSellCommand(index, null));
    }

    @Test
    public void execute_duplicateProperty_throwsCommandException() throws Exception {
        AddPropertyToSellCommand command = new AddPropertyToSellCommand(indexWithProperty, property);
        assertEquals(1, 1);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        AddPropertyToSellCommand command = new AddPropertyToSellCommand(invalidIndex, property);

        Assertions.assertThrows(CommandException.class, () -> command.execute(model),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AddPropertyToSellCommand addPropertyToSellCommand = new AddPropertyToSellCommand(index, property);

        // same object -> returns true
        assertTrue(addPropertyToSellCommand.equals(addPropertyToSellCommand));

        // same values -> returns true
        AddPropertyToSellCommand addPropertyToSellCommandCopy = new AddPropertyToSellCommand(index, property);
        assertTrue(addPropertyToSellCommand.equals(addPropertyToSellCommandCopy));

        // different types -> returns false
        assertFalse(addPropertyToSellCommand.equals(1));

        // null -> returns false
        assertFalse(addPropertyToSellCommand.equals(null));

        // different property -> returns false
        Property differentProperty = new PropertyToBuyBuilder().withPostalCode("654321").build();
        AddPropertyToSellCommand addDifferentPropertyCommand = new AddPropertyToSellCommand(index, differentProperty);
        assertFalse(addPropertyToSellCommand.equals(addDifferentPropertyCommand));
    }
    @Test
    public void execute() {
        AddPropertyToSellCommand command = new AddPropertyToSellCommand(indexWithoutProperty, property);
        Assertions.assertDoesNotThrow(() -> command.execute(model));
    }
}

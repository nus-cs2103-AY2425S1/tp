
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Property;
import seedu.address.testutil.PropertyBuilder;

public class AddPropertyToSellCommandTest {
    private final Index index = Index.fromZeroBased(7);
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Property property = new PropertyBuilder().build();
    private final Index indexWithProperty = Index.fromZeroBased(7);
    private final Index indexWithoutProperty = Index.fromZeroBased(0);
    private final Index invalidIndex = Index.fromZeroBased(1000);
    @Test
    public void constructor_nullProperty_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPropertyToSellCommand(index, null));
    }

    @Test
    public void execute_validModel_success() throws Exception {
        AddPropertyToBuyCommand command = new AddPropertyToBuyCommand(indexWithoutProperty, property);

        CommandResult result = command.execute(model);

        assertEquals(AddPropertyToBuyCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
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
        Property differentProperty = new PropertyBuilder().withPostalCode("654321").build();
        AddPropertyToSellCommand addDifferentPropertyCommand = new AddPropertyToSellCommand(index, differentProperty);
        assertFalse(addPropertyToSellCommand.equals(addDifferentPropertyCommand));
    }
}

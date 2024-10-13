package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import seedu.address.testutil.PropertyBuilder;

public class AddPropertyToBuyCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Property property = new PropertyBuilder().build();
    private final Index validIndex = Index.fromZeroBased(0);
    private final Index invalidIndex = Index.fromZeroBased(-1);

    @Test
    public void execute_validModel_success() throws Exception {
        AddPropertyToBuyCommand command = new AddPropertyToBuyCommand(validIndex, property);

        CommandResult result = command.execute(model);

        assertEquals(AddPropertyToBuyCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateProperty_throwsCommandException() throws Exception {
        AddPropertyToBuyCommand command1 = new AddPropertyToBuyCommand(validIndex, property);
        command1.execute(model);
        AddPropertyToBuyCommand command2 = new AddPropertyToBuyCommand(validIndex, property);

        assertThrows(CommandException.class, () -> command2.execute(model),
                AddPropertyToBuyCommand.MESSAGE_DUPLICATE_PROPERTY);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        AddPropertyToBuyCommand command = new AddPropertyToBuyCommand(invalidIndex, property);

        assertThrows(CommandException.class, () -> command.execute(model),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}

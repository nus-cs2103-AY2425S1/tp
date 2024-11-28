package seedu.address.logic.commands;

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

public class SortIndividualCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Index validIndex = Index.fromOneBased(1);
    private final Index invalidIndex = Index.fromOneBased(1000);

    @Test
    public void execute_validModel_success() throws Exception {
        SortIndividualCommand sortCommand = new SortIndividualCommand(validIndex, "Price", "L");

        CommandResult result = sortCommand.execute(model);

        assertNotEquals("Sorted person at index 1 by Price from Low to High", result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        SortIndividualCommand sortCommand = new SortIndividualCommand(invalidIndex, "Price", "L");

        assertThrows(CommandException.class, () -> sortCommand.execute(model),
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}

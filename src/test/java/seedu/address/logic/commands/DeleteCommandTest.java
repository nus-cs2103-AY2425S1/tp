package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validNameUnfilteredList_success() throws Exception {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(personToDelete.getName().fullName);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                Messages.formatForDeletion(personToDelete));

        CommandResult commandResult = deleteCommand.execute(model);

        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        String invalidName = "Non Existent";
        DeleteCommand deleteCommand = new DeleteCommand(invalidName);

        assertThrows(CommandException.class, String.format(DeleteCommand.MESSAGE_NO_MATCH_FOUND, invalidName), () ->
                deleteCommand.execute(model));
    }

    @Test
    public void execute_missingName_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand("");

        assertThrows(CommandException.class, DeleteCommand.MESSAGE_MISSING_NAME, () ->
                deleteCommand.execute(model));
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand("Alice");
        DeleteCommand deleteSecondCommand = new DeleteCommand("Bob");

        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        DeleteCommand deleteFirstCommandCopy = new DeleteCommand("Alice");
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        assertFalse(deleteFirstCommand.equals(1));

        assertFalse(deleteFirstCommand.equals(null));

        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}

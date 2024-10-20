package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.AddNotesCommand.MESSAGE_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTES_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTES_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Notes;
/**
 * Contains integration tests (interaction with the Model) and unit tests for AddNotesCommand.
 */
public class AddNotesCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute() {
        final Notes notes = new Notes("Some notes");
        assertCommandFailure(new AddNotesCommand(INDEX_FIRST_PERSON, notes), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), notes));
    }
    @Test
    public void equals() {
        final AddNotesCommand standardCommand = new AddNotesCommand(INDEX_FIRST_PERSON, new Notes(VALID_NOTES_AMY));
        // same values -> returns true
        AddNotesCommand commandWithSameValues = new AddNotesCommand(INDEX_FIRST_PERSON, new Notes(VALID_NOTES_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new AddNotesCommand(INDEX_SECOND_PERSON, new Notes(VALID_NOTES_AMY))));
        // different remark -> returns false
        assertFalse(standardCommand.equals(new AddNotesCommand(INDEX_FIRST_PERSON, new Notes(VALID_NOTES_BOB))));
    }
}

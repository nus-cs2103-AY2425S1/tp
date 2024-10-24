package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_NOTED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_NOTE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_OUTOFBOUND_NOTE;
import static seedu.address.testutil.TypicalIndexes.INDEX_OUTOFBOUND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalNotes.DUPLICATE_NOTE;
import static seedu.address.testutil.TypicalNotes.TYPICAL_NOTE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * EditCommand.
 */
public class EditNotesCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_editNotesCommand_success() throws Exception {
        Person testPerson = model.getFilteredPersonList().get(0);
        CommandResult commandResult = new EditNotesCommand(INDEX_FIRST_PERSON, INDEX_FIRST_NOTE, TYPICAL_NOTE)
                .execute(model);
        String expectedMessage = String.format(EditNotesCommand.MESSAGE_EDIT_NOTES_SUCCESS,
                Messages.format(testPerson));
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_duplicateNote_throwsCommandException() {
        EditNotesCommand editNotesCommand = new EditNotesCommand(INDEX_SECOND_PERSON, INDEX_FIRST_NOTE, DUPLICATE_NOTE);
        assertThrows(CommandException.class, Note.DUPLICATE_MESSAGE_CONSTRAINTS, () -> editNotesCommand.execute(model));
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        EditNotesCommand editNotesCommand = new EditNotesCommand(INDEX_OUTOFBOUND_PERSON, INDEX_FIRST_NOTE,
                DUPLICATE_NOTE);
        assertThrows(CommandException.class, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () ->
                editNotesCommand.execute(model));
    }

    @Test
    public void execute_invalidNoteIndex_throwsCommandException() {
        EditNotesCommand editNotesCommand = new EditNotesCommand(INDEX_SECOND_PERSON, INDEX_OUTOFBOUND_NOTE,
                TYPICAL_NOTE);
        assertThrows(CommandException.class, MESSAGE_INVALID_NOTED_INDEX, () ->
                editNotesCommand.execute(model));
    }

}

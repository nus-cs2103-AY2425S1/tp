package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;

public class NotesCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NotesCommand(null, NotesCommand.Mode.VIEW));
    }

    @Test
    public void constructor_nullMode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NotesCommand(new Name("Alice"), null));
    }

    @Test
    public void execute_viewValidName_success() throws Exception {
        Name validName = ALICE.getName();
        NotesCommand notesCommand = new NotesCommand(validName, NotesCommand.Mode.VIEW);
        CommandResult result = notesCommand.execute(model);
        assertEquals(String.format(NotesCommand.MESSAGE_VIEW_NOTES_SUCCESS,
            validName, ALICE.getNotes().toString()), result.getFeedbackToUser());
    }

    @Test
    public void execute_deleteValidName_success() throws Exception {
        Name validName = ALICE.getName();
        NotesCommand notesCommand = new NotesCommand(validName, NotesCommand.Mode.DELETE);
        CommandResult result = notesCommand.execute(model);
        assertEquals(String.format(NotesCommand.MESSAGE_DELETE_NOTES_SUCCESS, validName),
            result.getFeedbackToUser());
    }

    @Test
    public void execute_addValidName_success() throws Exception {
        Name validName = ALICE.getName();
        Notes newNotes = new Notes("New test notes");
        NotesCommand notesCommand = new NotesCommand(validName, NotesCommand.Mode.ADD, newNotes);
        CommandResult result = notesCommand.execute(model);
        assertEquals(String.format(NotesCommand.MESSAGE_ADD_NOTES_SUCCESS, validName, newNotes),
            result.getFeedbackToUser());
    }

    @Test
    public void execute_invalidName_throwsCommandException() {
        Name nonExistentName = new Name("NonExistent");
        NotesCommand notesCommand = new NotesCommand(nonExistentName, NotesCommand.Mode.VIEW);
        assertThrows(CommandException.class, () -> notesCommand.execute(model));
    }

    @Test
    public void equals() {
        Name aliceName = ALICE.getName();
        Notes testNotes = new Notes("Test notes");

        NotesCommand viewCommand = new NotesCommand(aliceName, NotesCommand.Mode.VIEW);
        NotesCommand deleteCommand = new NotesCommand(aliceName, NotesCommand.Mode.DELETE);
        NotesCommand addCommand = new NotesCommand(aliceName, NotesCommand.Mode.ADD, testNotes);

        // same object -> returns true
        assertTrue(viewCommand.equals(viewCommand));

        // same values -> returns true
        NotesCommand viewCommandCopy = new NotesCommand(aliceName, NotesCommand.Mode.VIEW);
        assertTrue(viewCommand.equals(viewCommandCopy));

        // different types -> returns false
        assertFalse(viewCommand.equals(1));

        // null -> returns false
        assertFalse(viewCommand.equals(null));

        // different mode -> returns false
        assertFalse(viewCommand.equals(deleteCommand));
        assertFalse(viewCommand.equals(addCommand));

        // different name -> returns false
        NotesCommand viewCommandDifferentName = new NotesCommand(new Name("Bob"), NotesCommand.Mode.VIEW);
        assertFalse(viewCommand.equals(viewCommandDifferentName));
    }
}

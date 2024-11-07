package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;

public class NotesCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NotesCommand((Name) null, NotesCommand.Mode.VIEW));
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NotesCommand((Index) null, NotesCommand.Mode.VIEW));
    }

    @Test
    public void constructor_nullMode_throwsNullPointerException() {
        Name validName = new Name(VALID_NAME_AMY);
        assertThrows(NullPointerException.class, () -> new NotesCommand(validName, null));
        assertThrows(NullPointerException.class, () -> new NotesCommand(INDEX_FIRST_PERSON, null));
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
    public void execute_viewValidIndex_success() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        NotesCommand notesCommand = new NotesCommand(INDEX_FIRST_PERSON, NotesCommand.Mode.VIEW);
        CommandResult result = notesCommand.execute(model);
        assertEquals(String.format(NotesCommand.MESSAGE_VIEW_NOTES_SUCCESS,
            firstPerson.getName(), firstPerson.getNotes().toString()), result.getFeedbackToUser());
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
    public void execute_deleteValidIndex_success() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        NotesCommand notesCommand = new NotesCommand(INDEX_FIRST_PERSON, NotesCommand.Mode.DELETE);
        CommandResult result = notesCommand.execute(model);
        assertEquals(String.format(NotesCommand.MESSAGE_DELETE_NOTES_SUCCESS, firstPerson.getName()),
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
    public void execute_addValidIndex_success() throws Exception {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Notes newNotes = new Notes("New test notes");
        NotesCommand notesCommand = new NotesCommand(INDEX_FIRST_PERSON, NotesCommand.Mode.ADD, newNotes);
        CommandResult result = notesCommand.execute(model);
        assertEquals(String.format(NotesCommand.MESSAGE_ADD_NOTES_SUCCESS, firstPerson.getName(), newNotes),
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

        // Commands with name identifier
        NotesCommand viewCommandName = new NotesCommand(aliceName, NotesCommand.Mode.VIEW);
        NotesCommand deleteCommandName = new NotesCommand(aliceName, NotesCommand.Mode.DELETE);
        NotesCommand addCommandName = new NotesCommand(aliceName, NotesCommand.Mode.ADD, testNotes);

        // Commands with index identifier
        NotesCommand viewCommandIndex = new NotesCommand(INDEX_FIRST_PERSON, NotesCommand.Mode.VIEW);
        NotesCommand deleteCommandIndex = new NotesCommand(INDEX_FIRST_PERSON, NotesCommand.Mode.DELETE);
        NotesCommand addCommandIndex = new NotesCommand(INDEX_FIRST_PERSON, NotesCommand.Mode.ADD, testNotes);

        // same object -> returns true
        assertTrue(viewCommandName.equals(viewCommandName));
        assertTrue(viewCommandIndex.equals(viewCommandIndex));

        // same values -> returns true
        NotesCommand viewCommandNameCopy = new NotesCommand(aliceName, NotesCommand.Mode.VIEW);
        NotesCommand viewCommandIndexCopy = new NotesCommand(INDEX_FIRST_PERSON, NotesCommand.Mode.VIEW);
        assertTrue(viewCommandName.equals(viewCommandNameCopy));
        assertTrue(viewCommandIndex.equals(viewCommandIndexCopy));

        // different types -> returns false
        assertFalse(viewCommandName.equals(1));
        assertFalse(viewCommandIndex.equals(""));

        // null -> returns false
        assertFalse(viewCommandName.equals(null));
        assertFalse(viewCommandIndex.equals(null));

        // different mode -> returns false
        assertFalse(viewCommandName.equals(deleteCommandName));
        assertFalse(viewCommandName.equals(addCommandName));
        assertFalse(viewCommandIndex.equals(deleteCommandIndex));
        assertFalse(viewCommandIndex.equals(addCommandIndex));

        // different identifier type -> returns false
        assertFalse(viewCommandName.equals(viewCommandIndex));
        assertFalse(deleteCommandName.equals(deleteCommandIndex));
        assertFalse(addCommandName.equals(addCommandIndex));

        // different name/index -> returns false
        NotesCommand viewCommandDifferentName = new NotesCommand(new Name("Bob"), NotesCommand.Mode.VIEW);
        NotesCommand viewCommandDifferentIndex = new NotesCommand(INDEX_SECOND_PERSON, NotesCommand.Mode.VIEW);
        assertFalse(viewCommandName.equals(viewCommandDifferentName));
        assertFalse(viewCommandIndex.equals(viewCommandDifferentIndex));
    }
}

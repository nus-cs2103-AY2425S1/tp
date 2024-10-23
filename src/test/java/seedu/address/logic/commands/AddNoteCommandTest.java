package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Note;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

public class AddNoteCommandTest {

    @Test
    public void execute_successfulCommand() {

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Note oldnote = new Note("Note text");
        AddNoteCommand command = new AddNoteCommand(ALICE.getNric(), oldnote);

        String expectedMessage = String.format(AddNoteCommand.MESSAGE_SUCCESS, ALICE.getNric(), "Note text");
        Model expectModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Note note = new Note("Note text");
        Person newAlice = new Person(ALICE);
        expectModel.addNoteToPerson(note, newAlice);
        assertCommandSuccess(command, model, expectedMessage, expectModel);

    }

    @Test
    public void execute_personNotFound() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Note note = new Note("Note text");
        AddNoteCommand command = new AddNoteCommand(new Nric("S8484131E"), note);
        assertCommandFailure(command, model, AddNoteCommand.MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    public void equals() {

        Note note = new Note("Note text");
        Nric nric = ALICE.getNric();

        AddNoteCommand addNoteCommand1 = new AddNoteCommand(nric, note);
        AddNoteCommand addNoteCommand2 = new AddNoteCommand(nric, note);

        // same object -> returns true
        assertTrue(addNoteCommand1.equals(addNoteCommand1));

        // same values -> returns true
        assertTrue(addNoteCommand1.equals(addNoteCommand2));

        // different types -> returns false
        assertFalse(addNoteCommand1.equals(1));

        // null -> returns false
        assertFalse(addNoteCommand1.equals(null));

        // different appointment -> returns false
        AddNoteCommand addNoteCommand3 = new AddNoteCommand(nric, new Note("Note text 2"));
        assertFalse(addNoteCommand1.equals(addNoteCommand3));
    }
}

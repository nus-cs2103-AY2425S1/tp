package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;

public class AddNoteCommandTest {

    @Test
    public void execute_successfulCommand() {

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        AddNoteCommand command = new AddNoteCommand(ALICE.getNric(), "Note text");

        String expectedMessage = String.format(AddNoteCommand.MESSAGE_SUCCESS, ALICE.getNric(), "Note text");
        Model expectModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Note note = new Note("Note text");
        Person newAlice = new Person(ALICE);
        expectModel.addNoteToPerson(note, newAlice);
        assertCommandSuccess(command, model, expectedMessage, expectModel);

    }

}

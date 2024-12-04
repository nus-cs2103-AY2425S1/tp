package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class UndoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addCommand_success() throws Exception {
        UndoCommand undoCommand = new UndoCommand();
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        addCommand.execute(model);
        model.pushToUndoStack(addCommand);

        String expectedMessage = UndoCommand.MESSAGE_SUCCESS + String.format(AddCommand.MESSAGE_UNDO_SUCCESS,
                Messages.format(validPerson));

        assertCommandSuccess(undoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyUndoStack_throwsCommandException() {
        UndoCommand undoCommand = new UndoCommand();
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_NO_COMMANDS);
    }
}

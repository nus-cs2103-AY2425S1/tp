package keycontacts.logic.commands;

import static keycontacts.logic.commands.CommandTestUtil.assertCommandFailure;
import static keycontacts.logic.commands.CommandTestUtil.assertCommandSuccess;
import static keycontacts.testutil.TypicalStudents.ALICE;
import static keycontacts.testutil.TypicalStudents.BENSON;
import static keycontacts.testutil.TypicalStudents.CARL;

import org.junit.jupiter.api.Test;

import keycontacts.model.Model;
import keycontacts.model.ModelManager;
import keycontacts.model.StudentDirectory;
import keycontacts.model.UserPrefs;

public class OverrideUndoneCommandTest {

    private final Model model = new ModelManager(new StudentDirectory(), new UserPrefs());

    @Test
    public void test_executingCommandAfterUndoOverrides_success() throws Exception {
        Model expectedModelEmpty = new ModelManager(new StudentDirectory(), new UserPrefs());
        Model expectedModelCarl = new ModelManager(new StudentDirectory(), new UserPrefs());
        expectedModelCarl.addStudent(CARL);

        model.addStudent(ALICE);
        model.commitStudentDirectory();

        model.addStudent(BENSON);
        model.commitStudentDirectory();

        // State should look like this:
        // [ ], [ALICE], [ALICE, BENSON]
        //                      ^

        Command undoCommand = new UndoCommand();
        undoCommand.execute(model);
        undoCommand.execute(model);

        // State should look like this:
        // [ ], [ALICE], [ALICE, BENSON]
        //  ^

        // Executing a command after undoing should clear the states
        model.addStudent(CARL);
        model.commitStudentDirectory();

        // State should look like this:
        // [ ], [CARL]
        //  ^

        Command redoCommand = new RedoCommand();

        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModelEmpty);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModelCarl);

        // State should look like this:
        // [ ], [CARL]
        //         ^

        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_LAST_VERSION);
    }
}

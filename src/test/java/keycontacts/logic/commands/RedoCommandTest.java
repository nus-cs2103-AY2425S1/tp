package keycontacts.logic.commands;

import static keycontacts.logic.commands.CommandTestUtil.assertCommandFailure;
import static keycontacts.logic.commands.CommandTestUtil.assertCommandSuccess;
import static keycontacts.testutil.TypicalStudents.getTypicalStudentDirectory;

import org.junit.jupiter.api.Test;

import keycontacts.model.Model;
import keycontacts.model.ModelManager;
import keycontacts.model.StudentDirectory;
import keycontacts.model.UserPrefs;

public class RedoCommandTest {
    private final Model model = new ModelManager(getTypicalStudentDirectory(), new UserPrefs());
    private final Model expectedModel = new ModelManager(new StudentDirectory(), new UserPrefs());

    @Test
    public void execute_afterUndoCommand_success() throws Exception {
        model.setStudentDirectory(new StudentDirectory());
        model.commitStudentDirectory();

        Command undoCommand = new UndoCommand();
        undoCommand.execute(model);

        Command redoCommand = new RedoCommand();
        assertCommandSuccess(redoCommand, model, new CommandResult(RedoCommand.MESSAGE_SUCCESS), expectedModel);
    }

    @Test
    public void execute_noUndoCommand_failure() {
        Command redoCommand = new RedoCommand();
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_LAST_VERSION);
    }
}

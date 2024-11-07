package keycontacts.logic.commands;

import static keycontacts.logic.commands.CommandTestUtil.assertCommandFailure;
import static keycontacts.logic.commands.CommandTestUtil.assertCommandSuccess;
import static keycontacts.testutil.TypicalStudents.getTypicalStudentDirectory;

import org.junit.jupiter.api.Test;

import keycontacts.model.Model;
import keycontacts.model.ModelManager;
import keycontacts.model.StudentDirectory;
import keycontacts.model.UserPrefs;

public class UndoCommandTest {
    private final Model model = new ModelManager(getTypicalStudentDirectory(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalStudentDirectory(), new UserPrefs());

    @Test
    public void execute_afterClearCommand_success() {
        model.setStudentDirectory(new StudentDirectory());
        model.commitStudentDirectory();

        Command undoCommand = new UndoCommand();
        assertCommandSuccess(undoCommand, model, new CommandResult(UndoCommand.MESSAGE_SUCCESS), expectedModel);
    }

    @Test
    public void execute_noPriorCommand_failure() {
        Command undoCommand = new UndoCommand();
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_LAST_VERSION);
    }
}

package seedu.edulog.logic.commands;

import static seedu.edulog.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edulog.testutil.TypicalEdulog.getTypicalEduLog;

import org.junit.jupiter.api.Test;

import seedu.edulog.model.EduLog;
import seedu.edulog.model.Model;
import seedu.edulog.model.ModelManager;
import seedu.edulog.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyEduLog_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyEduLog_success() {
        Model model = new ModelManager(getTypicalEduLog(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalEduLog(), new UserPrefs());
        expectedModel.setEduLog(new EduLog());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

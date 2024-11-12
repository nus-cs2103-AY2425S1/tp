package seedu.academyassist.logic.commands;

import static seedu.academyassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.academyassist.testutil.TypicalPersons.getTypicalAcademyAssist;

import org.junit.jupiter.api.Test;

import seedu.academyassist.model.AcademyAssist;
import seedu.academyassist.model.Model;
import seedu.academyassist.model.ModelManager;
import seedu.academyassist.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAcademyAssist_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAcademyAssist_success() {
        Model model = new ModelManager(getTypicalAcademyAssist(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAcademyAssist(), new UserPrefs());
        expectedModel.setAcademyAssist(new AcademyAssist());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

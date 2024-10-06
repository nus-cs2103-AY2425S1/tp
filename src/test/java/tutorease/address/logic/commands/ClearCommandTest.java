package tutorease.address.logic.commands;

import static tutorease.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static tutorease.address.testutil.TypicalPersons.getTypicalTutorEase;

import org.junit.jupiter.api.Test;

import tutorease.address.model.Model;
import tutorease.address.model.ModelManager;
import tutorease.address.model.TutorEase;
import tutorease.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyTutorEase_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTutorEase_success() {
        Model model = new ModelManager(getTypicalTutorEase(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalTutorEase(), new UserPrefs());
        expectedModel.setTutorEase(new TutorEase());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

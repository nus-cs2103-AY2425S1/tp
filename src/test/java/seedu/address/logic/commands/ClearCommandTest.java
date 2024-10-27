package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalEduContacts;

import org.junit.jupiter.api.Test;

import seedu.address.model.EduContacts;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyEduContacts_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyEduContacts_success() {
        Model model = new ModelManager(getTypicalEduContacts(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalEduContacts(), new UserPrefs());
        expectedModel.setEduContacts(new EduContacts());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

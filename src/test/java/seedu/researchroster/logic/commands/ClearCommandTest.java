package seedu.researchroster.logic.commands;

import static seedu.researchroster.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.researchroster.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.researchroster.model.ResearchRoster;
import seedu.researchroster.model.Model;
import seedu.researchroster.model.ModelManager;
import seedu.researchroster.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new ResearchRoster());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

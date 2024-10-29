package seedu.hireme.logic.commands;

import static seedu.hireme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hireme.logic.commands.InsightsCommand.SHOWING_INSIGHTS_MESSAGE;
import static seedu.hireme.testutil.TypicalInternshipApplications.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.hireme.model.Model;
import seedu.hireme.model.ModelManager;
import seedu.hireme.model.UserPrefs;

public class InsightsCommandTest {
    private final Model model =
            new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel =
            new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_insights_success() {
        CommandResult expectedCommandResult =
                new CommandResult(SHOWING_INSIGHTS_MESSAGE, false, false, true, model.getInsights());
        assertCommandSuccess(new InsightsCommand(), model, expectedCommandResult, expectedModel);
    }
}

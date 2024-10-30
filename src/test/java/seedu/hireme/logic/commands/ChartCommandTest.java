package seedu.hireme.logic.commands;

import static seedu.hireme.logic.commands.ChartCommand.SHOWING_CHART_MESSAGE;
import static seedu.hireme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hireme.testutil.TypicalInternshipApplications.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.hireme.model.Model;
import seedu.hireme.model.ModelManager;
import seedu.hireme.model.UserPrefs;

public class ChartCommandTest {
    private final Model model =
            new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel =
            new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_chart_success() {
        CommandResult expectedCommandResult =
                new CommandResult(SHOWING_CHART_MESSAGE, false, false, true, model.getChartData());
        assertCommandSuccess(new ChartCommand(), model, expectedCommandResult, expectedModel);
    }
}

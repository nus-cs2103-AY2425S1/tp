package seedu.hireme.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hireme.logic.commands.exceptions.CommandException;
import seedu.hireme.model.Model;

/**
 * Provide status insights of internship applications
 */
public class ChartCommand extends Command {
    public static final String COMMAND_WORD = "/chart";
    public static final String SHOWING_CHART_MESSAGE = "Opened chart window.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a pie chart with a data summary of internship application statuses\n"
            + "Example: " + COMMAND_WORD;
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult(SHOWING_CHART_MESSAGE, false,
                false, true, model.getChartData());
    }
}

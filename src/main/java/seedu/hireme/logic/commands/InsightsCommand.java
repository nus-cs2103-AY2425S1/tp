package seedu.hireme.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Map;

import seedu.hireme.logic.Messages;
import seedu.hireme.logic.commands.exceptions.CommandException;
import seedu.hireme.model.Model;
import seedu.hireme.model.internshipapplication.Status;

/**
 * Provide status insight of internship applications
 */
public class InsightsCommand extends Command {
    public static final String COMMAND_WORD = "/insights";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Map<Status, Integer> insightsMap = model.getInsights();
        return new CommandResult(Messages.formatInsights(insightsMap));
    }
}

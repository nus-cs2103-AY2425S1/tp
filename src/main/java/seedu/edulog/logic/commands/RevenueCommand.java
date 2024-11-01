package seedu.edulog.logic.commands;

import seedu.edulog.commons.util.ToStringBuilder;
import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;

/**
 * Calculate total revenue earned
 */
public class RevenueCommand extends Command {
    public static final String COMMAND_WORD = "revenue";
    public static final String COMMAND_USAGE = COMMAND_WORD + ": calculate total amount of money earned"
            + "from students who have paid. Example: revenue";
    public static final String COMMAND_SUCCESS = "Total revenue is $%d";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(String.format(COMMAND_SUCCESS, model.getRevenue()));
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof RevenueCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}

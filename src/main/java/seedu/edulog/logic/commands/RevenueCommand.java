package seedu.edulog.logic.commands;

import seedu.edulog.commons.util.ToStringBuilder;
import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;

/**
 * Calculate total revenue earned
 */
public class RevenueCommand extends Command {
    public static final String COMMAND_WORD = "revenue";
    public static final String COMMAND_USAGE = COMMAND_WORD + " [paid/unpaid]"
            + ": calculate total amount of money earned/not earned"
            + "from students who have paid/not paid. Example: revenue paid";
    public static final String COMMAND_SUCCESS = "Total revenue %s is $%d";
    public static final String PAID = "paid";
    public static final String UNPAID = "unpaid";

    private String option;

    public RevenueCommand(String option) {
        this.option = option;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        int result = (option.equals(PAID)) ? model.getPaid() : model.getUnpaid();
        String output = String.format(COMMAND_SUCCESS, option, result);
        return new CommandResult(output);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof RevenueCommand)) {
            return false;
        }

        RevenueCommand otherCommand = (RevenueCommand) other;
        return otherCommand.option.equals(this.option);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("option", option).toString();
    }
}

package seedu.edulog.logic.commands;

import seedu.edulog.logic.commands.exceptions.CommandException;
import seedu.edulog.model.Model;

/**
 * Calculate total revenue earned
 */
public abstract class RevenueCommand extends Command {
    public static final String COMMAND_WORD = "revenue";
    public static final String COMMAND_USAGE = COMMAND_WORD + " [paid/unpaid]"
            + ": calculate total amount of money earned/not earned"
            + "from students who have paid/not paid. Example: revenue paid";
    public static final String MESSAGE_SUCCESS = "Total revenue %s is $%d";
    public static final String PAID = "paid";
    public static final String UNPAID = "unpaid";

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;

    @Override
    public abstract String toString();
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.model.Model;


/**
 * Displays total money earned, as well as total money
 * owed
 */
public class IncomeCommand extends Command {
    public static final String COMMAND_WORD = "income";
    public static final String COMMAND_WORD_RANDOM_CASE = "InCome";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        double totalPaidAmount = model.getTotalPaidAmount();
        double totalOwedAmount = model.getTotalOwedAmount();

        return new CommandResult(Messages.getIncomeMessage(totalPaidAmount, totalOwedAmount));
    }
}

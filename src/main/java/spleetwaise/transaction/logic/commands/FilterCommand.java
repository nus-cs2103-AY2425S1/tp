package spleetwaise.transaction.logic.commands;

import static java.util.Objects.requireNonNull;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_AMOUNT_SIGN;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DATE;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static spleetwaise.transaction.logic.parser.CliSyntax.PREFIX_STATUS;

import spleetwaise.commons.logic.commands.Command;
import spleetwaise.commons.logic.commands.CommandResult;
import spleetwaise.commons.logic.commands.exceptions.CommandException;
import spleetwaise.commons.model.CommonModelManager;
import spleetwaise.commons.util.ToStringBuilder;
import spleetwaise.transaction.model.FilterCommandPredicate;

/**
 * This class represents a command for filtering transactions.
 */
public class FilterCommand extends Command {

    /**
     * The word used to trigger this command in the input.
     */
    public static final String COMMAND_WORD = "filterTxn";

    /**
     * The message that is displayed upon successful execution of this command.
     */
    public static final String MESSAGE_SUCCESS = "Transaction book filtered.";

    /**
     * The message usage string that explains how to use this command.
     */
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Filter the transaction book.\n" + "Parameters: "
                    + "[INDEX] " + "[" + PREFIX_AMOUNT + "AMOUNT] " + "[" + PREFIX_DESCRIPTION
                    + "DESCRIPTION] " + "[" + PREFIX_DATE + "DATE] " + "[" + PREFIX_STATUS
                    + "STATUS] " + "[" + PREFIX_AMOUNT_SIGN + "AMOUNT_SIGN]\n"
                    + "At least one of the above filtering criteria is needed\n"
                    + "Example: " + COMMAND_WORD + " 1";

    private final FilterCommandPredicate filterPredicate;

    /**
     * Creates an AddCommand to add the specified {@code Transaction}.
     *
     * @param predicate the predicate to filter the transactions.
     */
    public FilterCommand(FilterCommandPredicate predicate) {
        requireNonNull(predicate);

        filterPredicate = predicate;
    }

    /**
     * This method executes the filter command.
     *
     * @return the result of the execution.
     */
    @Override
    public CommandResult execute() throws CommandException {
        CommonModelManager model = CommonModelManager.getInstance();

        model.updateFilteredTransactionList(filterPredicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterCommand otherFilterCommand)) {
            return false;
        }

        return filterPredicate.equals(otherFilterCommand.filterPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", filterPredicate)
                .toString();
    }
}

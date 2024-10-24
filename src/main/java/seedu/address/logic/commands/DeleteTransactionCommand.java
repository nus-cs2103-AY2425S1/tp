package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Transaction;

/**
 * Deletes a transaction from a person's transaction list using its specified index.
 */
public class DeleteTransactionCommand extends Command {
    public static final String COMMAND_WORD = "deletet";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the transaction identified by the index number used in the displayed transaction list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TRANSACTION_SUCCESS = "Deleted transaction %1$s \nfrom %2$s";

    private static final Index CURRENT_PERSON = Index.fromOneBased(1);
    private final Index index;


    public DeleteTransactionCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (CURRENT_PERSON.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person selected = lastShownList.get(CURRENT_PERSON.getZeroBased());
        List<Transaction> transactions = new ArrayList<>(model.getFilteredTransactionList());
        if (transactions.isEmpty()) {
            transactions = selected.getTransactions();
        }

        if (index.getZeroBased() >= transactions.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToRemove = transactions.get(index.getZeroBased());
        transactions.remove(index.getZeroBased());
        selected.updateBalance(transactionToRemove.getAmount() * -1);
        model.updateTransactionList(transactions);

        return new CommandResult(String.format(MESSAGE_DELETE_TRANSACTION_SUCCESS, Messages.format(transactionToRemove),
                Messages.format(selected)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteTransactionCommand)) {
            return false;
        }

        DeleteTransactionCommand otherCommand = (DeleteTransactionCommand) other;
        return index.equals(otherCommand.index);
    }
}

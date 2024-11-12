package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.client.IsSelectedPredicate;


/**
 * Lists all transactions for the selected client.
 */
public class ListTransactionCommand extends Command {
    public static final String COMMAND_WORD = "listt";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists the transactions of the client identified by "
            + "the index number used in the displayed client list.\n"
            + "Parameter: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SUCCESS = "Listed %1$d transaction(s) for %2$s";

    private final Index index;
    private final Logger logger = LogsCenter.getLogger(ListTransactionCommand.class);

    /**
     * @param index the index of the client to view transactions of.
     */
    public ListTransactionCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getIsViewTransactions()) {
            logger.fine("CommandException caused by attempt to use listt command in transaction view.");
            throw new CommandException(String.format(Messages.MESSAGE_MUST_BE_CLIENT_LIST, COMMAND_WORD));
        }

        List<Client> lastShownList = model.getFilteredClientList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_EMPTY_CLIENT_LIST, COMMAND_WORD));
        }
        if (index.getZeroBased() >= lastShownList.size()) {
            logger.fine("CommandException caused by invalid client index provided.");
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client selected = lastShownList.get(index.getZeroBased());
        assert selected != null : "Client should not be null";
        model.updateFilteredClientList(new IsSelectedPredicate(model, index));
        model.updateTransactionList(selected.getTransactions());
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                selected.getTransactions().size(), Messages.format(selected)));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ListTransactionCommand)) {
            return false;
        }
        ListTransactionCommand otherCommand = (ListTransactionCommand) other;
        return this.index.equals(otherCommand.index);
    }
}

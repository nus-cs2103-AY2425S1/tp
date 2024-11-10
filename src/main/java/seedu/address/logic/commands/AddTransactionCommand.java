package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OTHER_PARTY;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.client.Transaction;
import seedu.address.model.client.TransactionDateComparator;

/**
 * Adds a transaction to the selected client in address book.
 */
public class AddTransactionCommand extends Command {

    public static final String COMMAND_WORD = "addt";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a transaction to selected client. "
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_AMOUNT + "AMOUNT "
            + PREFIX_OTHER_PARTY + "OTHER PARTY "
            + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "buy new equipment "
            + PREFIX_AMOUNT + "-1000.55 "
            + PREFIX_OTHER_PARTY + "Company XYZ "
            + PREFIX_DATE + "2024-10-20";

    public static final String MESSAGE_ADD_TRANSACTION_SUCCESS = "Added new transaction %1$s\nto %2$s";

    private final Index index;
    private final Transaction toAdd;
    private final Logger logger = LogsCenter.getLogger(AddTransactionCommand.class);

    /**
     * @param index index of selected client in client list to add transaction to
     * @param transaction transaction to add
     */
    public AddTransactionCommand(Index index, Transaction transaction) {
        requireAllNonNull(index, transaction);

        this.index = index;
        toAdd = transaction;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        if (model.getIsViewTransactions()) {
            logger.fine("CommandException caused by attempt to use addt command in transaction view.");
            throw new CommandException(String.format(Messages.MESSAGE_MUST_BE_CLIENT_LIST, COMMAND_WORD));
        }

        List<Client> lastShownList = model.getFilteredClientList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(String.format(Messages.MESSAGE_EMPTY_CLIENT_LIST, COMMAND_WORD));
        }

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.fine("CommandException caused by invalid index.");
            throw new CommandException(Messages.MESSAGE_INVALID_CLIENT_DISPLAYED_INDEX);
        }

        Client clientToEdit = lastShownList.get(index.getZeroBased());
        assert clientToEdit != null : "Client should not be null";

        if (clientToEdit.checkIsOverflow(toAdd.getAmount())) {
            throw new CommandException(Messages.MESSAGE_DOUBLE_OVERFLOW);
        }

        List<Transaction> transactions = new ArrayList<>(clientToEdit.getTransactions());
        transactions.add(toAdd);
        transactions.sort(new TransactionDateComparator());

        Client editedClient = new Client(clientToEdit.getName(), clientToEdit.getCompany(), clientToEdit.getPhone(),
                clientToEdit.getEmail(), clientToEdit.getAddress(), clientToEdit.getTags(), transactions);

        model.setClient(clientToEdit, editedClient);

        return new CommandResult(String.format(MESSAGE_ADD_TRANSACTION_SUCCESS, Messages.format(toAdd),
                Messages.format(editedClient)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddTransactionCommand)) {
            return false;
        }

        AddTransactionCommand otherCommand = (AddTransactionCommand) other;
        return index.equals(otherCommand.index) && toAdd.equals(otherCommand.toAdd);
    }

}

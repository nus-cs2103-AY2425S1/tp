package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.IsSelectedPredicate;
import seedu.address.model.person.Person;

/**
 * Lists all transactions for the selected person.
 */
public class ListTransactionCommand extends Command {
    public static final String COMMAND_WORD = "listt";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists the transactions of the person identified by "
            + "the index number used in the displayed person list.\n"
            + "Parameter: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SUCCESS = "Listed %1$d transaction(s) for %2$s";

    private final Index index;

    /**
     * @param index the index of the person to view transactions of.
     */
    public ListTransactionCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getIsViewTransactions()) {
            throw new CommandException(String.format(Messages.MESSAGE_MUST_BE_PERSON_LIST, COMMAND_WORD));
        }

        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person selected = lastShownList.get(index.getZeroBased());
        model.updateFilteredPersonList(new IsSelectedPredicate(model, index));
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

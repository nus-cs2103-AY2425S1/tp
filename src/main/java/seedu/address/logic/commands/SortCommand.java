package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.logging.Logger;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Sorts all persons in the address book by given field.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the list by given predicate.\n"
            + "Parameters: "
            + "[name] "
            + "[role] "
            + "[phone] "
            + "[email] "
            + "[address] "
            + "Example: " + COMMAND_WORD
            + " name";

    public static final String MESSAGE_SORT_LIST_SUCCESS = "Sorted by %s";

    private static final Logger logger = Logger.getLogger(SortCommand.class.getName());
    private final String sortBy;

    private Comparator<Person> comparator;

    /**
     * Sets {@code comparator} to this object's {@code comparator}.
     * Sets {@code sortBy} to this object's {@code sortBy}.
     */
    public SortCommand(String sortBy, Comparator<Person> comparator) {
        this.comparator = comparator;
        this.sortBy = sortBy;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("----------------[EXECUTING][" + COMMAND_WORD + "]");

        requireNonNull(model);

        // Assertion to check that model is not null at runtime
        assert model != null : "Model should not be null";

        model.updateSortedPersonList(this.comparator);
        return new CommandResult(String.format(MESSAGE_SORT_LIST_SUCCESS, sortBy));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        return sortBy.equals(otherSortCommand.sortBy);
    }
}

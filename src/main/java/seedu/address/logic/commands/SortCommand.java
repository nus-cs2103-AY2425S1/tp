package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameComparator;
import seedu.address.model.person.Person;

/**
 * Sorts the list of contacts in Client Hub.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String SHORT_COMMAND_WORD = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " CRITERIA"
            + ": Sorts the list according to criteria given.\n"
            + "Usage: sort";

    public static final String MESSAGE_SUCCESS = "List sorted successfully!";

    private final Comparator<Person> comparator;

    /**
     * Creates an SortCommand to sort the current list of contacts.
     */
    public SortCommand() {
        this.comparator = new NameComparator();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateSortedPersonList(comparator);
        return new CommandResult(MESSAGE_SUCCESS);
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
        return comparator.equals(otherSortCommand.comparator);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("comparator", comparator)
                .toString();
    }
}

package seedu.hireme.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hireme.commons.util.ToStringBuilder;
import seedu.hireme.logic.Messages;
import seedu.hireme.model.Model;
import seedu.hireme.model.internshipapplication.DateComparator;
import seedu.hireme.model.internshipapplication.InternshipApplication;

/**
 * Sorts the internship applications list by either the earliest or latest application dates.
 */
public class SortCommand extends Command<InternshipApplication> {
    public static final String COMMAND_WORD = "/sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the internship applications list by the specified order (case-insensitive and either by "
            + "earliest or latest application dates) and displays the sorted list with index numbers.\n"
            + "Parameters: ORDER (earliest or latest)\n"
            + "Example: " + COMMAND_WORD + " latest";

    private final DateComparator comparator;

    public SortCommand(DateComparator comparator) {
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model<InternshipApplication> model) {
        requireNonNull(model);
        model.sortFilteredList(comparator);
        return new CommandResult(String.format(Messages.MESSAGE_INTERNSHIP_APPLICATIONS_SORTED_OVERVIEW,
                model.getFilteredList().size()));
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

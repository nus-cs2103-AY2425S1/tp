package seedu.hireme.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.hireme.commons.util.ToStringBuilder;
import seedu.hireme.logic.Messages;
import seedu.hireme.model.Model;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.model.internshipapplication.StatusPredicate;

/**
 * Filters and lists all internship applications with specified status
 */
public class FilterCommand extends Command<InternshipApplication> {

    public static final String COMMAND_WORD = "/filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters and lists all internship applications with specified status (case-insensitive)\n"
            + "Parameters: STATUS (must be PENDING, ACCEPTED or REJECTED)\n"
            + "Example: " + COMMAND_WORD + " PENDING";

    private final StatusPredicate statusPredicate;

    public FilterCommand(StatusPredicate statusPredicate) {
        this.statusPredicate = statusPredicate;
    }

    @Override
    public CommandResult execute(Model<InternshipApplication> model) {
        requireNonNull(model);
        model.updateFilteredList(statusPredicate);
        return new CommandResult(String.format(Messages.MESSAGE_INTERNSHIP_APPLICATIONS_LISTED_OVERVIEW,
                model.getFilteredList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return statusPredicate.equals(otherFilterCommand.statusPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("statusPredicate", statusPredicate)
                .toString();
    }
}

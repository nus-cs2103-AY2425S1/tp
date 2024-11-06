package seedu.edulog.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.edulog.commons.util.ToStringBuilder;
import seedu.edulog.logic.Messages;
import seedu.edulog.model.Model;
import seedu.edulog.model.student.StudentHasPaidPredicate;

/**
 * Filters the students based on their payment status.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the students and lists them based on "
            + "specified payment status. Extra trailing characters are ignored.\n"
            + "The command parameter is case insensitive.\n"
            + "Parameter:  P\n"
            + "OR U\n"
            + "Example: " + COMMAND_WORD + "paid";

    public static final char PAID = 'p';

    public static final char UNPAID = 'u';

    private final StudentHasPaidPredicate predicate;

    public FilterCommand(StudentHasPaidPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredStudentList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getFilteredStudentList().size()));
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
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

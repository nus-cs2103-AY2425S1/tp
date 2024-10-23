package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.predicate.StudentHasPaidPredicate;

/**
 * Finds and lists all persons in address book whose paid status matches the argument.
 */
public class FilterPaidCommand extends Command {
    public static final String COMMAND_WORD = "filterp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose paid status matches "
            + "the specified argument and displays them as a list with index numbers.\n"
            + "Parameters: HASPAID\n"
            + "Example: " + COMMAND_WORD + " true";

    private final StudentHasPaidPredicate predicate;

    public FilterPaidCommand(StudentHasPaidPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterPaidCommand)) {
            return false;
        }

        FilterPaidCommand otherFilterPaidCommand = (FilterPaidCommand) other;
        return predicate.equals(otherFilterPaidCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.JobContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose job contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterByJobCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String COMMAND_FUNCTION = COMMAND_WORD + ": Filters all persons whose jobs contain any of "
            + "the specified keywords (case-insensitive)\n"
            + " and displays them as a list with index numbers.";

    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " Photographer" + " OR " + COMMAND_WORD + " caterer";

    private final JobContainsKeywordsPredicate predicate;

    public FilterByJobCommand(JobContainsKeywordsPredicate predicate) {
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
        if (!(other instanceof FilterByJobCommand otherFilterByJobCommand)) {
            return false;
        }

        return predicate.equals(otherFilterByJobCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

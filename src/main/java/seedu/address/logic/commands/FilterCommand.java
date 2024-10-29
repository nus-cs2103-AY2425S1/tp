package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameOrJobContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name or job contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String COMMAND_FUNCTION = COMMAND_WORD
            + ": Filters all persons whose names or jobs contain any of "
            + "the specified keywords (case-insensitive)";

    public static final String MESSAGE_USAGE = COMMAND_FUNCTION
            + "\nParameters: n/KEYWORD or j/KEYWORD\n"
            + "Example: " + COMMAND_WORD + " n/John OR " + COMMAND_WORD + " j/Photographer";

    private final NameOrJobContainsKeywordsPredicate predicate;

    public FilterCommand(NameOrJobContainsKeywordsPredicate predicate) {
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
        if (!(other instanceof FilterCommand otherFilterCommand)) {
            return false;
        }

        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

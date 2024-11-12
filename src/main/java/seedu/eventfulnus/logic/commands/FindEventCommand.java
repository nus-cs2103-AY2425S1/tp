package seedu.eventfulnus.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eventfulnus.commons.util.ToStringBuilder;
import seedu.eventfulnus.logic.Messages;
import seedu.eventfulnus.model.Model;
import seedu.eventfulnus.model.event.EventContainsKeywordsPredicate;

/**
 * Finds and lists all events in address book whose name or attributes contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindEventCommand extends Command {

    public static final String COMMAND_WORD = "findevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds all events whose names or attributes contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " volleyball usc roy";

    private final EventContainsKeywordsPredicate predicate;

    public FindEventCommand(EventContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindEventCommand otherFindEventCommand)) {
            return false;
        }

        return predicate.equals(otherFindEventCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

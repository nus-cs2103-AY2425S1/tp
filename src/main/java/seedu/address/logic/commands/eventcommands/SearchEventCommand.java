package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.model.Model;
import seedu.address.model.types.common.EventTagContainsKeywordsPredicate;

/**
 * Finds and lists all events in address book who has a tag with a name containing any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchEventCommand extends SearchCommand {
    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches all events who have tags containing any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " e friends work";

    private final EventTagContainsKeywordsPredicate predicate;

    public SearchEventCommand(EventTagContainsKeywordsPredicate predicate) {
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
        if (!(other instanceof SearchEventCommand)) {
            return false;
        }

        SearchEventCommand otherSearchCommand = (SearchEventCommand) other;
        return predicate.equals(otherSearchCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

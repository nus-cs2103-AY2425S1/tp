package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.event.EventCelebrityMatchesKeywordPredicate;
import seedu.address.ui.CommandDetailChange;
import seedu.address.ui.CommandTabChange;

/**
 * Filters the events by the specified celebrity and displays it.
 * Keyword matching is case insensitive.
 */
public class FilterEventCommand extends FilterCommand {
    public static final String COMMAND_FIELD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the events by "
            + "the specified celebrity (case-insensitive) and displays it.\n"
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " " + COMMAND_FIELD + " Lebron James";

    private final EventCelebrityMatchesKeywordPredicate predicate;

    public FilterEventCommand(EventCelebrityMatchesKeywordPredicate predicate) {
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredEventList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_EVENTS_LISTED_OVERVIEW, model.getFilteredEventList().size()),
                false, false, CommandTabChange.EVENT, CommandDetailChange.SIMPLIFIED);

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FilterEventCommand)) {
            return false;
        }

        FilterEventCommand otherFilterEventCommand = (FilterEventCommand) other;
        return predicate.equals(otherFilterEventCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

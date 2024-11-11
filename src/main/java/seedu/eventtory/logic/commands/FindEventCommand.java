package seedu.eventtory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eventtory.logic.commands.util.PredicatePreviewUtil.getPreviewOfFilteredEvents;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_EVENT;

import javafx.collections.transformation.FilteredList;
import seedu.eventtory.commons.util.ToStringBuilder;
import seedu.eventtory.logic.Messages;
import seedu.eventtory.logic.commands.exceptions.CommandException;
import seedu.eventtory.model.Model;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.event.EventContainsKeywordsPredicate;
import seedu.eventtory.ui.UiState;

/**
 * Finds and lists all events in EventTory whose fields contain any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindEventCommand extends FindCommand {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all events whose fields contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: " + PREFIX_EVENT + " KEYWORD [MORE_KEYWORDS]... \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EVENT + " alice bob charlie\n";
    public static final String MESSAGE_FIND_EVENT_FAILURE_INVALID_VIEW =
            "You have to be viewing an event list to use the find event command";

    private final EventContainsKeywordsPredicate predicate;

    public FindEventCommand(EventContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        UiState uiState = model.getUiState().getValue();
        if (uiState.isEventDetails() || uiState.isVendorList()) {
            throw new CommandException(MESSAGE_FIND_EVENT_FAILURE_INVALID_VIEW);
        }

        FilteredList<Event> events = getPreviewOfFilteredEvents(model, predicate);
        if (events.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_EVENTS_FOUND);
        }

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
        if (!(other instanceof FindEventCommand)) {
            return false;
        }

        FindEventCommand otherFindCommand = (FindEventCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

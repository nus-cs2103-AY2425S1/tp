package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.AddressBookParser.EVENT_COMMAND_INDICATOR;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.EventNameContainsKeywordPredicate;

/**
 * Finds events whose names start with the specified prefix (case-insensitive).
 */
public class FindEventCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds events with names containing the specified keyword (case-insensitive).\n"
            + "Parameters: KEYWORD (must only contain between 1 and 50 alphanumeric characters or spaces)\n"
            + "Example: " + EVENT_COMMAND_INDICATOR + " " + COMMAND_WORD + " eventSearchString";

    public static final String MESSAGE_EVENT_FOUND = "Found %d event(s) containing '%s':";
    public static final String MESSAGE_EVENT_NOT_FOUND = "No events found containing '%s'.";

    private final EventNameContainsKeywordPredicate predicate;

    /**
     * Constructs a FindEventCommand that searches for events containing the given string.
     *
     * @param predicate The condition that searches the events.
     */
    public FindEventCommand(EventNameContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (predicate.getKeyword().trim().isEmpty()) {
            throw new CommandException(String.format(MESSAGE_EVENT_NOT_FOUND, predicate.getKeyword()));
        }
        boolean matchesFound = model.filterEventsByName(predicate);

        // Set the appropriate message based on whether matches were found
        String eventFoundOrNotFoundMessage = matchesFound
                ? String.format(MESSAGE_EVENT_FOUND, model.getFilteredEventList().size(), predicate.getKeyword())
                : String.format(MESSAGE_EVENT_NOT_FOUND, predicate.getKeyword());

        return new CommandResult(eventFoundOrNotFoundMessage);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindEventCommand)) {
            return false;
        }

        FindEventCommand otherFindCommand = (FindEventCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return "FindEventCommand[predicate=" + predicate + "]";
    }
}

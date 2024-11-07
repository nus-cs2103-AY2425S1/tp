package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Finds events whose names start with the specified prefix (case-insensitive).
 */
public class FindEventCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds events with names containing the specified keyword (case-insensitive).\n"
            + "Parameters: KEYWORD (must be a non-empty string)\n"
            + "Example: " + COMMAND_WORD + " eventSearchString";

    public static final String MESSAGE_EVENT_FOUND = "Found %d event(s) containing '%s':";
    public static final String MESSAGE_EVENT_NOT_FOUND = "No events found containing '%s'.";

    private final String searchString;

    /**
     * Constructs a FindEventCommand that searches for events containing the given string.
     *
     * @param searchString The string to search for.
     */
    public FindEventCommand(String searchString) {
        requireNonNull(searchString);
        this.searchString = searchString.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (searchString.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_EVENT_NOT_FOUND, searchString));
        }

        ObservableList<Event> filteredEvents = model.filterEventsByName(searchString);

        if (filteredEvents.isEmpty()) {
            model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
            return new CommandResult(String.format(MESSAGE_EVENT_NOT_FOUND, searchString));
        }

        String resultMessage = String.format(MESSAGE_EVENT_FOUND, filteredEvents.size(), searchString);
        return new CommandResult(resultMessage);
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
        return searchString.equals(otherFindCommand.searchString);
    }

    @Override
    public String toString() {
        return "FindEventCommand[searchString=" + searchString + "]";
    }
}

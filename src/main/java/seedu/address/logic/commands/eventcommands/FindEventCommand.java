package seedu.address.logic.commands.eventcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

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
            + ": Finds events whose names start with the specified prefix (case-insensitive).\n"
            + "Parameters: PREFIX (must be a non-empty string)\n"
            + "Example: " + COMMAND_WORD + " eventprefix";

    public static final String MESSAGE_EVENT_FOUND = "Found %d event(s) starting with '%s':";
    public static final String MESSAGE_EVENT_NOT_FOUND = "No events found starting with '%s'.";

    private final String prefix;

    /**
     * Constructs a FindEventCommand that searches for events starting with the given prefix.
     *
     * @param prefix The prefix to search for.
     */
    public FindEventCommand(String prefix) {
        requireNonNull(prefix);
        this.prefix = prefix.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        // Find events whose names start with the given prefix, ignoring case
        List<Event> matchingEvents = lastShownList.stream()
                .filter(event -> event.getName().toString().toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());

        if (matchingEvents.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_EVENT_NOT_FOUND, prefix));
        }

        String resultMessage = String.format(MESSAGE_EVENT_FOUND, matchingEvents.size(), prefix);
        for (Event event : matchingEvents) {
            resultMessage += "\n" + event.getName().toString();
        }

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
        return prefix.equals(otherFindCommand.prefix);
    }

    @Override
    public String toString() {
        return "FindEventCommand[prefix=" + prefix + "]";
    }
}

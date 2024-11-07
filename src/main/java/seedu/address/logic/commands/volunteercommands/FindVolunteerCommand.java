package seedu.address.logic.commands.volunteercommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VOLUNTEERS;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.volunteer.Volunteer;

/**
 * Finds volunteers whose names start with the specified prefix (case-insensitive).
 */
public class FindVolunteerCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds volunteers whose names start with the specified prefix (case-insensitive).\n"
            + "Parameters: PREFIX (must be a non-empty alphanumeric between 1 and 100 characters)\n"
            + "Example: " + COMMAND_WORD + " volunteerSearchString";

    public static final String MESSAGE_VOLUNTEER_FOUND = "Found %d volunteer(s) containing '%s':";
    public static final String MESSAGE_VOLUNTEER_NOT_FOUND = "No volunteers found containing '%s'.";

    private final String searchString;

    /**
     * Constructs a FindVolunteerCommand that searches for volunteers starting with the given prefix.
     *
     * @param prefix The prefix to search for.
     */
    public FindVolunteerCommand(String prefix) {
        requireNonNull(prefix);
        this.searchString = prefix.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (searchString.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_VOLUNTEER_NOT_FOUND, searchString));
        }

        ObservableList<Volunteer> filteredEvents = model.filterVolunteersByName(searchString);

        if (filteredEvents.isEmpty()) {
            model.updateFilteredVolunteerList(PREDICATE_SHOW_ALL_VOLUNTEERS);
            return new CommandResult(String.format(MESSAGE_VOLUNTEER_NOT_FOUND, searchString));
        }

        String resultMessage = String.format(MESSAGE_VOLUNTEER_FOUND, filteredEvents.size(), searchString);
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindVolunteerCommand)) {
            return false;
        }

        FindVolunteerCommand otherFindCommand = (FindVolunteerCommand) other;
        return searchString.equals(otherFindCommand.searchString);
    }

    @Override
    public String toString() {
        return "FindVolunteerCommand[searchString=" + searchString + "]";
    }
}

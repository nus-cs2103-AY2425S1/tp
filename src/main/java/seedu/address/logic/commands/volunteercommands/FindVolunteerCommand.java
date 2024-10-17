package seedu.address.logic.commands.volunteercommands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

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
            + "Parameters: PREFIX (must be a non-empty string)\n"
            + "Example: " + COMMAND_WORD + " volunteerprefix";

    public static final String MESSAGE_VOLUNTEER_FOUND = "Found %d volunteer(s) starting with '%s':";
    public static final String MESSAGE_VOLUNTEER_NOT_FOUND = "No volunteers found starting with '%s'.";

    private final String prefix;

    /**
     * Constructs a FindVolunteerCommand that searches for volunteers starting with the given prefix.
     *
     * @param prefix The prefix to search for.
     */
    public FindVolunteerCommand(String prefix) {
        requireNonNull(prefix);
        this.prefix = prefix.trim();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Volunteer> lastShownList = model.getFilteredVolunteerList();

        // Find volunteers whose names start with the given prefix, ignoring case
        List<Volunteer> matchingVolunteers = lastShownList.stream()
                .filter(volunteer -> volunteer.getName().toString().toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());

        if (matchingVolunteers.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_VOLUNTEER_NOT_FOUND, prefix));
        }

        String resultMessage = String.format(MESSAGE_VOLUNTEER_FOUND, matchingVolunteers.size(), prefix);
        for (Volunteer volunteer : matchingVolunteers) {
            resultMessage += "\n" + volunteer.getName().toString();
        }

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
        return prefix.equals(otherFindCommand.prefix);
    }

    @Override
    public String toString() {
        return "FindVolunteerCommand[prefix=" + prefix + "]";
    }
}

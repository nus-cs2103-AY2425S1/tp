package seedu.address.logic.commands.volunteercommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.AddressBookParser.VOLUNTEER_COMMAND_INDICATOR;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.volunteer.VolunteerNameContainsKeywordPredicate;

/**
 * Finds volunteers whose names start with the specified prefix (case-insensitive).
 */
public class FindVolunteerCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Finds volunteers with names containing the specified keyword (case-insensitive).\n"
            + "Parameters: KEYWORD (must only contain between 1 and 100 alphanumeric characters or spaces)\n"
            + "Example: " + VOLUNTEER_COMMAND_INDICATOR + " " + COMMAND_WORD + " volunteerSearchString";

    public static final String MESSAGE_VOLUNTEER_FOUND = "Found %d volunteer(s) containing '%s':";
    public static final String MESSAGE_VOLUNTEER_NOT_FOUND = "No volunteers found containing '%s'.";

    private final VolunteerNameContainsKeywordPredicate predicate;

    /**
     * Constructs a FindVolunteerCommand that searches for volunteers starting with the given prefix.
     *
     * @param predicate The condition that searches the volunteers.
     */
    public FindVolunteerCommand(VolunteerNameContainsKeywordPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (predicate.getKeyword().trim().isEmpty()) {
            throw new CommandException(String.format(MESSAGE_VOLUNTEER_NOT_FOUND, predicate.getKeyword()));
        }
        boolean matchesFound = model.filterVolunteersByName(predicate);

        // Set the appropriate message based on whether matches were found
        String volunteerFoundOrNotFoundMessage = matchesFound
                ? String.format(MESSAGE_VOLUNTEER_FOUND, model.getFilteredVolunteerList().size(),
                predicate.getKeyword())
                : String.format(MESSAGE_VOLUNTEER_NOT_FOUND, predicate.getKeyword());

        return new CommandResult(volunteerFoundOrNotFoundMessage);
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
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return "FindVolunteerCommand[predicate=" + predicate + "]";
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.ASSIGN_VOLUNTEER_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.ASSIGN_EVENT_PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.volunteercommands.VolunteerAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new VolunteerAddCommand object.
 */
public class AssignCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the VolunteerAddCommand
     * and returns a VolunteerAddCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public AssignCommand parseCommand(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, ASSIGN_VOLUNTEER_PREFIX_NAME, ASSIGN_EVENT_PREFIX_NAME);

        // Check if mandatory prefixes are present and preamble is empty
        if (!arePrefixesPresent(argMultimap, ASSIGN_VOLUNTEER_PREFIX_NAME, ASSIGN_EVENT_PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerAddCommand.MESSAGE_USAGE));
        }

        // Ensure no duplicate prefixes are provided
        argMultimap.verifyNoDuplicatePrefixesFor(ASSIGN_VOLUNTEER_PREFIX_NAME, ASSIGN_EVENT_PREFIX_NAME);

        int volunteerId;
        int eventId;

        try {
            volunteerId = Integer.parseInt(argMultimap.getValue(ASSIGN_VOLUNTEER_PREFIX_NAME).orElseThrow(
                    () -> new ParseException("Volunteer ID is required.")
            ));

            eventId = Integer.parseInt(argMultimap.getValue(ASSIGN_EVENT_PREFIX_NAME).orElseThrow(
                    () -> new ParseException("Event ID is required.")
            ));
        } catch (NumberFormatException e) {
            throw new ParseException("ID must be a valid integer.");
        }

        return new AssignCommand(volunteerId, eventId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

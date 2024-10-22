package seedu.address.logic.parser.volunteercommandparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_AVAILABLE_DATE;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_PHONE;

import java.util.stream.Stream;

import seedu.address.logic.commands.volunteercommands.VolunteerNewCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.VolunteerParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.volunteer.Date;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Volunteer;

/**
 * Parses input arguments and creates a new VolunteerNewCommand object.
 */
public class VolunteerNewCommandParser implements Parser<VolunteerNewCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the VolunteerNewCommand
     * and returns a VolunteerNewCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public VolunteerNewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, VOLUNTEER_PREFIX_NAME, VOLUNTEER_PREFIX_PHONE,
                        VOLUNTEER_PREFIX_EMAIL, VOLUNTEER_PREFIX_AVAILABLE_DATE);

        // Check if mandatory prefixes are present and preamble is empty
        if (!arePrefixesPresent(argMultimap, VOLUNTEER_PREFIX_NAME, VOLUNTEER_PREFIX_PHONE,
                VOLUNTEER_PREFIX_EMAIL, VOLUNTEER_PREFIX_AVAILABLE_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerNewCommand.MESSAGE_USAGE));
        }

        // Ensure no duplicate prefixes are provided
        argMultimap.verifyNoDuplicatePrefixesFor(VOLUNTEER_PREFIX_NAME, VOLUNTEER_PREFIX_PHONE,
                VOLUNTEER_PREFIX_EMAIL, VOLUNTEER_PREFIX_AVAILABLE_DATE);

        Name name = VolunteerParserUtil.parseName(argMultimap.getValue(VOLUNTEER_PREFIX_NAME).get());
        Phone phone = VolunteerParserUtil.parsePhone(argMultimap.getValue(VOLUNTEER_PREFIX_PHONE).get());
        Email email = VolunteerParserUtil.parseEmail(argMultimap.getValue(VOLUNTEER_PREFIX_EMAIL).get());
        Date availableDate = VolunteerParserUtil.parseDate(argMultimap.getValue(VOLUNTEER_PREFIX_AVAILABLE_DATE).get());

        Volunteer volunteer = new Volunteer(name, phone, email, availableDate);
        return new VolunteerNewCommand(volunteer);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

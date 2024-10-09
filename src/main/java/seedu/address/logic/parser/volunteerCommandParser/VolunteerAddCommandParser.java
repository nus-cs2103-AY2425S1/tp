package seedu.address.logic.parser.volunteerCommandParser;

import seedu.address.logic.commands.volunteerCommands.VolunteerAddCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.volunteer.Date;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.model.volunteer.Time;
import seedu.address.model.volunteer.UniqueVolunteerList;

import java.util.stream.Stream;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

public class VolunteerAddCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the VolunteerAddCommand
     * and returns a VolunteerAddCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public VolunteerAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, VOLUNTEER_PREFIX_NAME, VOLUNTEER_PREFIX_PHONE,
                        VOLUNTEER_PREFIX_EMAIL, VOLUNTEER_PREFIX_AVAILABLE_DATE,
                        VOLUNTEER_PREFIX_START_TIME, VOLUNTEER_PREFIX_END_TIME);

        // Check if mandatory prefixes are present and preamble is empty
        if (!arePrefixesPresent(argMultimap, VOLUNTEER_PREFIX_NAME, VOLUNTEER_PREFIX_PHONE,
                VOLUNTEER_PREFIX_EMAIL, VOLUNTEER_PREFIX_AVAILABLE_DATE,
                VOLUNTEER_PREFIX_START_TIME, VOLUNTEER_PREFIX_END_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerAddCommand.MESSAGE_USAGE));
        }

        // Ensure no duplicate prefixes are provided
        argMultimap.verifyNoDuplicatePrefixesFor(VOLUNTEER_PREFIX_NAME, VOLUNTEER_PREFIX_PHONE,
                VOLUNTEER_PREFIX_EMAIL, VOLUNTEER_PREFIX_AVAILABLE_DATE,
                VOLUNTEER_PREFIX_START_TIME, VOLUNTEER_PREFIX_END_TIME);

        Name name = ParserUtil.parseVolunteerName(argMultimap.getValue(VOLUNTEER_PREFIX_NAME).get());
        Phone phone = ParserUtil.parseVolunteerPhone(argMultimap.getValue(VOLUNTEER_PREFIX_PHONE).get());
        Email email = ParserUtil.parseVolunteerEmail(argMultimap.getValue(VOLUNTEER_PREFIX_EMAIL).get());
        Date availableDate = ParserUtil.parseVolunteerDate(argMultimap.getValue(VOLUNTEER_PREFIX_AVAILABLE_DATE).get());
        Time startTime = ParserUtil.parseVolunteerTime(argMultimap.getValue(VOLUNTEER_PREFIX_START_TIME).get());
        Time endTime = ParserUtil.parseVolunteerTime(argMultimap.getValue(VOLUNTEER_PREFIX_END_TIME).get());

        Volunteer volunteer = new Volunteer(name, phone, email, date, time);
        return new VolunteerAddCommand(volunteer);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

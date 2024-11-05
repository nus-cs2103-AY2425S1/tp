package seedu.address.logic.parser.volunteercommandparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_AVAILABLE_DATE;
import static seedu.address.logic.parser.CliSyntax.VOLUNTEER_PREFIX_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.volunteercommands.VolunteerRemoveDateCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.VolunteerParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and returns a VolunteerRemoveDateCommandObject
 */
public class VolunteerRemoveDateCommandParser implements Parser<VolunteerRemoveDateCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the VolunteerNewCommand
     * and returns a VolunteerNewCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public VolunteerRemoveDateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, VOLUNTEER_PREFIX_AVAILABLE_DATE, VOLUNTEER_PREFIX_INDEX);

        // Check if mandatory prefixes are present and preamble is empty
        if (!arePrefixesPresent(argMultimap, VOLUNTEER_PREFIX_AVAILABLE_DATE, VOLUNTEER_PREFIX_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    VolunteerRemoveDateCommand.MESSAGE_USAGE));
        }

        // Ensure no duplicate prefixes are provided
        argMultimap.verifyNoDuplicatePrefixesFor(VOLUNTEER_PREFIX_AVAILABLE_DATE, VOLUNTEER_PREFIX_INDEX);

        try {
            Index index = VolunteerParserUtil.parseIndex(argMultimap.getValue(VOLUNTEER_PREFIX_INDEX).get());
            String dateList = VolunteerParserUtil.checkStringListOfDates(argMultimap
                    .getValue(VOLUNTEER_PREFIX_AVAILABLE_DATE).get());
            return new VolunteerRemoveDateCommand(index, dateList);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerRemoveDateCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

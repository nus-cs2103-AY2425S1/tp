package seedu.address.logic.parser.volunteercommandparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.volunteercommands.FindVolunteerCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.VolunteerParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindVolunteerCommand object.
 */
public class FindVolunteerCommandParser implements Parser<FindVolunteerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindVolunteerCommand
     * and returns a FindVolunteerCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public FindVolunteerCommand parse(String args) throws ParseException {
        try {
            String searchTerm = VolunteerParserUtil.parseSearchTerm(args);
            return new FindVolunteerCommand(searchTerm);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindVolunteerCommand.MESSAGE_USAGE), pe);
        }
    }
}

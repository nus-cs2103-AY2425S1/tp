package seedu.address.logic.parser.volunteercommandparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.volunteercommands.VolunteerViewCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.VolunteerParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code EventViewCommand} object.
 */
public class VolunteerViewCommandParser implements Parser<VolunteerViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the VolunteerViewCommand
     * and returns a VolunteerViewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public VolunteerViewCommand parse(String args) throws ParseException {

        try {
            Index index = VolunteerParserUtil.parseIndex(args);
            return new VolunteerViewCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, VolunteerViewCommand.MESSAGE_USAGE), pe);
        }

    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MatchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a MatchCommand object.
 */
public class MatchCommandParser implements Parser<MatchCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MatchCommand
     * and returns a MatchCommand object for execution.
     * @throws ParseException If the user input does not conform the expected format.
     */
    public MatchCommand parse(String args) throws ParseException {


        String[] splitArgs = args.trim().split("\\s+");

        if (splitArgs.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }

        String inputContactIndex = splitArgs[MatchCommand.CONTACT_INDEX_POS];
        String inputJobIndex = splitArgs[MatchCommand.JOB_INDEX_POS];

        Index contactIndex;
        Index jobIndex;
        try {
            contactIndex = ParserUtil.parseIndex(inputContactIndex);
            jobIndex = ParserUtil.parseIndex(inputJobIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE), pe);
        }

        return new MatchCommand(contactIndex, jobIndex);
    }
}

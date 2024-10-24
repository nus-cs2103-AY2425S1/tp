package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnmatchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a UnmatchCommand object.
 */
public class UnmatchCommandParser implements Parser<UnmatchCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnmatchCommand
     * and returns a UnmatchCommand object for execution.
     *
     * @param args The input arguments provided by the user.
     * @return A new UnmatchCommand object based on the parsed input.
     * @throws ParseException If the user input does not conform the expected format.
     */
    public UnmatchCommand parse(String args) throws ParseException {


        String[] splitArgs = args.trim().split("\\s+");

        if (splitArgs.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmatchCommand.MESSAGE_USAGE));
        }

        String inputContactIndex = splitArgs[UnmatchCommand.CONTACT_INDEX_POS];
        String inputJobIndex = splitArgs[UnmatchCommand.JOB_INDEX_POS];

        Index contactIndex;
        Index jobIndex;
        try {
            contactIndex = ParserUtil.parseIndex(inputContactIndex);
            jobIndex = ParserUtil.parseIndex(inputJobIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmatchCommand.MESSAGE_USAGE), pe);
        }

        return new UnmatchCommand(contactIndex, jobIndex);
    }
}

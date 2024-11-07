package seedu.address.logic.parser;

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

        String[] splitArgs = ParserUtil.parseRequiredNumberOfArguments(args, 2, UnmatchCommand.MESSAGE_USAGE);

        String inputContactIndex = splitArgs[UnmatchCommand.CONTACT_INDEX_POS];
        String inputJobIndex = splitArgs[UnmatchCommand.JOB_INDEX_POS];

        Index contactIndex = ParserUtil.parseIndex(inputContactIndex);
        Index jobIndex = ParserUtil.parseIndex(inputJobIndex);

        return new UnmatchCommand(contactIndex, jobIndex);
    }
}

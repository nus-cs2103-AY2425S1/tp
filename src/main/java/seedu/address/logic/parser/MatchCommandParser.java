package seedu.address.logic.parser;

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

        String[] splitArgs = ParserUtil.parseRequiredNumberOfArguments(args, 2, MatchCommand.MESSAGE_USAGE);

        String inputContactIndex = splitArgs[MatchCommand.CONTACT_INDEX_POS];
        String inputJobIndex = splitArgs[MatchCommand.JOB_INDEX_POS];

        Index contactIndex = ParserUtil.parseIndex(inputContactIndex);
        Index jobIndex = ParserUtil.parseIndex(inputJobIndex);

        return new MatchCommand(contactIndex, jobIndex);
    }
}

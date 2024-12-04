package seedu.address.logic.parser.clientcommandparsers;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.clientcommands.MoreInfoCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MoreInfoCommand object
 */
public class MoreInfoCommandParser implements Parser<MoreInfoCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MoreInfoCommand
     * and returns a MoreInfoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MoreInfoCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args);

        Index index = ParserUtil.parseIndexWithInvalidCommandFormatMessage(argumentMultimap.getPreamble(),
                MoreInfoCommand.MESSAGE_USAGE);
        return new MoreInfoCommand(index);
    }
}

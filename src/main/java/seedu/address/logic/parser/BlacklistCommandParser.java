package seedu.address.logic.parser;

// import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.BlacklistCommand;
import seedu.address.logic.commands.BlacklistListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new BlacklistCommand object
 */
public class BlacklistCommandParser implements Parser<BlacklistCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BlacklistCommand
     * and returns a BlacklistCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BlacklistCommand parse(String args) throws ParseException {
        // requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        Index index;
        String preamble = argMultimap.getPreamble();

        try {
            index = ParserUtil.parseIndex(preamble);
        } catch (ParseException pe) {
            if (preamble.equals("")) {
                // only allow "blacklist" with no params as the valid command
                return new BlacklistListCommand();
            } else {
                // any non-empty non-index params throws an error
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        BlacklistCommand.MESSAGE_USAGE), pe);
            }
        }

        return new BlacklistCommand(index);
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SchemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SchemeCommand object
 */
public class SchemeCommandParser implements Parser<SchemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SchemeCommand
     * and returns a SchemeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public SchemeCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty() || !SchemeCommandParser.isNumber(trimmedArgs)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SchemeCommand.MESSAGE_USAGE));
            }
            Index index = ParserUtil.parseIndex(trimmedArgs);
            return new SchemeCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SchemeCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns true if a given string is a number.
     */
    public static boolean isNumber(String str) {
        return str.matches("-?\\d+");
    }
}

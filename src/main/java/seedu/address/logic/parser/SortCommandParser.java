package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommand.ASCENDING;
import static seedu.address.logic.commands.SortCommand.DESCENDING;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            trimmedArgs = ASCENDING;
        }
        String trimmedArgsLowerCase = trimmedArgs.toLowerCase();
        if (trimmedArgsLowerCase.equals("ascending")) {
            trimmedArgsLowerCase = ASCENDING;
        }
        if (trimmedArgsLowerCase.equals("descending")) {
            trimmedArgsLowerCase = DESCENDING;
        }
        if (!(trimmedArgsLowerCase.equals(ASCENDING) || trimmedArgsLowerCase.equals(DESCENDING))) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return new SortCommand(trimmedArgsLowerCase);
    }

}

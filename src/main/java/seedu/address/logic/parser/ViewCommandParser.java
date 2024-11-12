package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameMatchesKeywordPredicate;

/**
 * Parses input arguments and creates a new {@code ViewCommand} object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ViewCommand}
     * and returns a {@code ViewCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        if (args == null || args.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        String trimmedArgs = args.trim();

        try {
            if (isIndex(trimmedArgs)) {
                return new ViewCommand(ParserUtil.parseIndex(trimmedArgs), null);
            } else if (isValidName(trimmedArgs)) {
                String[] nameKeywords = trimmedArgs.split("\\s+");
                return new ViewCommand(null, new NameMatchesKeywordPredicate(Arrays.asList(nameKeywords)));
            } else {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns true if the argument is a valid index.
     */
    private boolean isIndex(String test) {
        return test.matches("^[1-9]\\d*$");
    }

    /**
     * Returns true if the argument contains only valid name characters.
     */
    private boolean isValidName(String test) {
        return test.matches("^[a-zA-Z'\\-\\s]+$")
                && !test.matches(".*\\d+.*");
    }
}

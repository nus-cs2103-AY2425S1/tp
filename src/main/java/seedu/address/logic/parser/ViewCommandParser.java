package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {
    private static final String WHITESPACE_SPLIT = "\\s+";
    private static final int NUM_EXPECTED_ARGUMENTS = 1;
    private static final int FIRST_ARGUMENT_INDEX = 0;

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public ViewCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] splitArgs = trimmedArgs.split(WHITESPACE_SPLIT);

        // if the number of indices given is not exactly 1, throw an exception
        if (!(splitArgs.length == NUM_EXPECTED_ARGUMENTS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        Index indexToView = parseViewIndex(splitArgs[FIRST_ARGUMENT_INDEX]);
        return new ViewCommand(indexToView);
    }

    /**
     * Catches possible parse exceptions and provides them the correct error message.
     */
    private Index parseViewIndex(String inputIndex) throws ParseException {
        try {
            return ParserUtil.parseIndex(inputIndex);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }
    }
}

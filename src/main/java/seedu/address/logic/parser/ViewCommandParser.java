package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * Parses input arguments and creates a new {@code ViewCommand} object.
 */
public class ViewCommandParser {
    /**
     * Parses the given {@code String} of user input and returns a {@code ViewCommand} object for execution.
     *
     * @param userInput the user input string containing the index of the person to view.
     * @return a {@code ViewCommand} object containing the parsed {@code Index}.
     * @throws ParseException if the user input does not contain a valid index.
     */
    public ViewCommand parse(String userInput) throws ParseException {
        String indexStr = userInput.trim();
        try {
            Index index = ParserUtil.parseIndex(userInput);
            return new ViewCommand(index);
        } catch (ParseException p) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewCommand.MESSAGE_USAGE), p);
        }
    }
}

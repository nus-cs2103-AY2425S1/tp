package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input to create a ExitCommand object.
 * <p>
 * This class implements the {@link Parser} interface and is responsible for
 * interpreting the user input specific to the exit command. It validates the
 * input format and ensures that it complies with the expected structure.
 * </p>
 */
public class ExitCommandParser implements Parser<ExitCommand> {
    /**
     * Parses user input to create a ExitCommand object.
     * <p>
     * This class implements the {@link Parser} interface and is responsible for
     * interpreting the user input specific to exit command. It validates the
     * input format and ensures that it complies with the expected structure.
     * </p>
     *
     * @author wnayar
     */
    public ExitCommand parse(String userInput) throws ParseException {
        String[] words = userInput.split(" ");
        if (words.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExitCommand.MESSAGE_USAGE));
        } else {
            return new ExitCommand();
        }
    }
}

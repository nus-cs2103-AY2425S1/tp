package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input to create an {@code ExitCommand} object.
 * The expected input is just the "exit" command without any additional arguments.
 * If extra arguments are provided, a {@code ParseException} is thrown.
 */
public class ExitCommandParser implements Parser<ExitCommand> {

    /**
     * Parses the given input arguments and creates an {@code ExitCommand} object.
     * The input is expected to contain only the "exit" command without any additional arguments.
     *
     * <p>If extra arguments are provided, a {@code ParseException} is thrown.</p>
     *
     * @param userInput The user input string containing the "exit" command.
     * @return An {@code ExitCommand} object.
     * @throws ParseException If extra arguments are provided or the input format is incorrect.
     */
    public ExitCommand parse(String userInput) throws ParseException {
        String[] words = userInput.trim().split(" ");
        if (words.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExitCommand.MESSAGE_USAGE));
        } else {
            return new ExitCommand();
        }
    }
}

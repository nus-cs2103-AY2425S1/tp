package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input to create a ClearCommand object.
 * <p>
 * This class implements the {@link Parser} interface and is responsible for
 * interpreting the user input specific to the clear command. It validates the
 * input format and ensures that it complies with the expected structure.
 * </p>
 */
public class ClearCommandParser implements Parser<ClearCommand> {
    /**
     * Parses the given user input and creates a {@code ClearCommand} object if the input is valid.
     * The input is considered valid if it contains exactly one word (the command itself).
     *
     * @param userInput The input string entered by the user.
     * @return A {@code ClearCommand} object if the input is valid.
     * @throws ParseException If the user input does not match the expected format.
     */
    public ClearCommand parse(String userInput) throws ParseException {
        String[] words = userInput.trim().split(" ");
        if (words.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        } else {
            return new ClearCommand();
        }
    }
}

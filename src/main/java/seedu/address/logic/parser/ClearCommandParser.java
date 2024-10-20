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
     * Parses user input to create a clear Command object.
     * <p>
     * This class implements the {@link Parser} interface and is responsible for
     * interpreting the user input specific to clear command. It validates the
     * input format and ensures that it complies with the expected structure.
     * </p>
     *
     * @author wnayar
     */
    public ClearCommand parse(String userInput) throws ParseException {
        String[] words = userInput.split(" ");
        if (words.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        } else {
            return new ClearCommand();
        }
    }
}

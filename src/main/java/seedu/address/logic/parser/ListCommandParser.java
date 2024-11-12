package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input to create a ListCommand object.
 * <p>
 * This class implements the {@link Parser} interface and is responsible for
 * interpreting the user input specific to listing commands. It validates the
 * input format and ensures that it complies with the expected structure.
 * </p>
 */
public class ListCommandParser implements Parser<ListCommand> {
    /**
     * Parses user input to create a ListCommand object.
     * <p>
     * This class implements the {@link Parser} interface and is responsible for
     * interpreting the user input specific to listing commands. It validates the
     * input format and ensures that it complies with the expected structure.
     * </p>
     *
     * @author gabriellegtw
     */
    public ListCommand parse(String userInput) throws ParseException {
        String[] words = userInput.trim().split(" ");
        if (words.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        } else {
            return new ListCommand();
        }
    }
}

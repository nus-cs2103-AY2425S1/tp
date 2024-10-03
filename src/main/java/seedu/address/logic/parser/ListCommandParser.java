package seedu.address.logic.parser;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class ListCommandParser implements Parser<ListCommand> {
    /**
     * Parses the given user input and creates a ListCommand object.
     *
     * @param userInput the input string provided by the user.
     * @throws ParseException if the user input is not in the expected format
     *                        or if it contains unexpected arguments.
     * @return a ListCommand object.
     */
    public ListCommand parse(String userInput) throws ParseException {
        String[] words = userInput.split(" ");
        if (words.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        } else {
            return new ListCommand();
        }
    }
}

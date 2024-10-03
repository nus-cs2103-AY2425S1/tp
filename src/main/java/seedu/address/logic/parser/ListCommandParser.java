package seedu.address.logic.parser;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class ListCommandParser implements Parser<ListCommand> {
    /**
     * 
     * @param userInput
     * @throws ParseException
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

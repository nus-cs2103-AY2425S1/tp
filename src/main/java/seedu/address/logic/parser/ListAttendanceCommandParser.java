package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_NO_ADDITIONAL_PARAMS;

import seedu.address.logic.commands.ListAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parse ListAttendanceCommand and check if it contains extra user input.
 */
public class ListAttendanceCommandParser implements Parser<ListAttendanceCommand> {
    @Override
    public ListAttendanceCommand parse(String userInput) throws ParseException {
        System.out.println("user input null? " + userInput + "    " + userInput.isEmpty());
        if (!userInput.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_NO_ADDITIONAL_PARAMS, ListAttendanceCommand.COMMAND_WORD));
        }
        return new ListAttendanceCommand();
    }
}

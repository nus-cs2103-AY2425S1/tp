package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new DeleteReminderCommand object.
 */
public class DeleteReminderCommandParser implements Parser<DeleteReminderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteReminderCommand
     * and returns a DeleteReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteReminderCommand parse(String args) throws ParseException {
        if (args == "") {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteReminderCommand.MESSAGE_USAGE));
        }
        Name name = ParserUtil.parseName(args);
        return new DeleteReminderCommand(name);
    }
}

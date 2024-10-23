package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_NAME_DISPLAYED;

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
        try {
            Name name = ParserUtil.parseName(args);
            return new DeleteReminderCommand(name);
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_NAME_DISPLAYED, pe);
        }
    }
}

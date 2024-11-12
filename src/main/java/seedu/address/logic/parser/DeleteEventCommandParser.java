package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteEventByIndexCommand;
import seedu.address.logic.commands.DeleteEventByNameCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventName;

/**
 * Parses input arguments and creates a new DeleteEventCommand object
 */
public class DeleteEventCommandParser implements Parser<DeleteEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteEventCommand
     * and returns a DeleteEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteEventCommand parse(String args) throws ParseException {
        char firstChar = args.trim().isEmpty() ? ' ' : args.trim().charAt(0);
        if (Character.isDigit(firstChar)) {
            return parseInt(args);
        }

        return parseName(args);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteEventCommand
     * and returns a {@code DeleteEventByIndexCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    private DeleteEventByIndexCommand parseInt(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteEventByIndexCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteEventCommand
     * and returns a {@code DeleteEventByNameCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    private DeleteEventByNameCommand parseName(String args) throws ParseException {
        try {
            EventName name = ParserUtil.parseEventName(args);
            return new DeleteEventByNameCommand(name);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE), pe);
        }
    }

}

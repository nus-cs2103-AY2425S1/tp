package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.eventCommands.DeleteEventCommand;
import seedu.address.logic.commands.personCommands.Command;
import seedu.address.logic.commands.personCommands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//TODO: Replace Command with DeleteCommand

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<Command> {

    /**
     * Parses the given {@code String} of arguments in the context of the Command
     * and returns a Command object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(ModelType modelType, String args) throws ParseException {
        if (modelType == ModelType.PERSON) {
            return parseForPerson(args);
        } else {
            return parseForEvent(args);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parseForPerson(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteEventCommand
     * and returns a DeleteEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteEventCommand parseForEvent(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteEventCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE), pe);
        }
    }

}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteByIndexCommand;
import seedu.address.logic.commands.DeleteByNameCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a {@code DeleteCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        char firstChar = args.trim().isEmpty() ? ' ' : args.trim().charAt(0);
        if (Character.isDigit(firstChar)) {
            return parseInt(args);
        }

        return parseName(args);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a {@code DeleteByIndexCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    private DeleteByIndexCommand parseInt(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteByIndexCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a {@code DeleteByNameCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    private DeleteByNameCommand parseName(String args) throws ParseException {
        try {
            Name name = ParserUtil.parseName(args);
            return new DeleteByNameCommand(name);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}

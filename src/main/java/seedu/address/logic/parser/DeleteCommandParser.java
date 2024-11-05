package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_NON_POSITIVE_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            List<Index> indices = ParserUtil.parseIndices(args);
            return new DeleteCommand(indices);
        } catch (ParseException | CommandException e) {
            throw new ParseException(
                    String.format(MESSAGE_NON_POSITIVE_INDEX, DeleteCommand.MESSAGE_USAGE), e);
        }
    }

}

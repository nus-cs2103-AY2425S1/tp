package seedu.sellsavvy.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.logic.commands.ordercommands.MarkOrderCommand;
import seedu.sellsavvy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkOrderCommand object.
 */
public class MarkOrderCommandParser implements Parser<MarkOrderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddOrderCommand
     * and returns a MarkOrderCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public MarkOrderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MarkOrderCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkOrderCommand.MESSAGE_USAGE), pe);
        }
    }
}

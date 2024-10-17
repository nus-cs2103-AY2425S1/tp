package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePropertyToSellCommand;
import seedu.address.logic.commands.DeletePropertyToSellCommand.EditPersonPropertyDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class DeletePropertyToSellCommandParser implements Parser<DeletePropertyToSellCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePropertyToSellCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index personIndex;
        Index propertyIndex;

        try {
            personIndex = ParserUtil.parsePersonIndex(args);
            propertyIndex = ParserUtil.parsePropertyIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePropertyToSellCommand.MESSAGE_USAGE), pe);
        }
        EditPersonPropertyDescriptor editPersonPropertyDescriptor = new EditPersonPropertyDescriptor();
        return new DeletePropertyToSellCommand(personIndex, propertyIndex, editPersonPropertyDescriptor);
    }

}

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePropertyToBuyCommand;
import seedu.address.logic.commands.DeletePropertyToBuyCommand.EditPersonPropertyDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class DeletePropertyToBuyCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePropertyToBuyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index personIndex;
        Index propertyIndex;

        try {
            personIndex = ParserUtil.parsePersonIndex(args);
            propertyIndex = ParserUtil.parsePropertyIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePropertyToBuyCommand.MESSAGE_USAGE), pe);
        }
        EditPersonPropertyDescriptor editPersonPropertyDescriptor = new EditPersonPropertyDescriptor();
        return new DeletePropertyToBuyCommand(personIndex, propertyIndex, editPersonPropertyDescriptor);
    }

}

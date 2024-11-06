package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePropertyToBuyCommand;
import seedu.address.logic.commands.DeletePropertyToBuyCommand.EditPersonPropertyToBuyDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class DeletePropertyToBuyCommandParser implements Parser<DeletePropertyToBuyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletePropertyToBuyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index personIndex;
        Index propertyIndex;
        ArrayList<String> parameters = ParserUtil.getParametersList(args);

        if (parameters.size() != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeletePropertyToBuyCommand.MESSAGE_USAGE));
        }
        try {
            personIndex = ParserUtil.parseIndex(parameters.get(0));
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                    DeletePropertyToBuyCommand.MESSAGE_USAGE), pe);
        }
        try {
            propertyIndex = ParserUtil.parseIndex(parameters.get(1));
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_PROPERTY_DISPLAYED_INDEX,
                    DeletePropertyToBuyCommand.MESSAGE_USAGE), pe);
        }
        EditPersonPropertyToBuyDescriptor editPersonPropertyDescriptor = new EditPersonPropertyToBuyDescriptor();
        return new DeletePropertyToBuyCommand(personIndex, propertyIndex, editPersonPropertyDescriptor);
    }

}

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.InspectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new InspectCommand object
 */
public class InspectCommandParser implements Parser<InspectCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the InspectCommand
     * and returns a InspectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public InspectCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;
        try {
            List<Index> indexList = ParserUtil.parseIndex(args);
            if (indexList.isEmpty()) {
                throw new ParseException(MESSAGE_INVALID_INDEX);
            }
            index = indexList.get(0);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    InspectCommand.MESSAGE_USAGE), ive);
        }

        return new InspectCommand(index);
    }
}

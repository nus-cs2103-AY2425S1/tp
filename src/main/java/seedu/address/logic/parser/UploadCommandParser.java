package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UploadCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UploadCommand object
 */
public class UploadCommandParser implements Parser<UploadCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UploadCommand
     * and returns a UploadCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UploadCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UploadCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

}

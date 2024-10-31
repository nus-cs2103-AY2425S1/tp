package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
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
        String[] stringArrayToParse = args.trim().split(" ");
        Index[] indicesToDelete = new Index[stringArrayToParse.length];
        try {
            for (int i = 0; i < stringArrayToParse.length; i++) {
                indicesToDelete[i] = ParserUtil.parseIndex(stringArrayToParse[i]);
            }
            return new DeleteCommand(indicesToDelete);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage() + "\nUsage:\n" + DeleteCommand.MESSAGE_USAGE);
        }
    }

}

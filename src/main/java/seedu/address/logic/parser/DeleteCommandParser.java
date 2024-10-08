package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteContactCommand object
 */
public class DeleteCommandParser implements Parser<DeleteContactCommand> {

    public static final String COMMAND_USAGE = "Invalid delete command. Specify 'company', 'contact' or 'job'.";

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteContactCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteContactCommand parse(String args) throws ParseException {


        String[] splitArgs = args.trim().split("\\s+");

        if (args.length() < 1) {
            throw new ParseException(COMMAND_USAGE);
        } else if (splitArgs.length < 2) {
            throw new ParseException("Missing index");
        }


        String entityType = splitArgs[0]; // either "contact, "job" or "company"
        String indexString = splitArgs[1];
        try {
            Index index = ParserUtil.parseIndex(indexString);

            switch (entityType) {
            case "contact":
                return new DeleteContactCommand(index);
            default:
                throw new ParseException(COMMAND_USAGE);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}

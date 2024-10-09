package seedu.address.logic.parser;

import static seedu.address.logic.Messages.DELETE_COMMAND_USAGE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_INDEX;

import seedu.address.commons.core.index.Index;
<<<<<<< HEAD
import seedu.address.logic.commands.DeleteCommand;
=======
import seedu.address.logic.commands.DeleteContactCommand;
>>>>>>> 1aecc3cb543c836c8b932029dec7533644924ea3
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
<<<<<<< HEAD
public class DeleteCommandParser implements Parser<DeleteCommand> {

=======
public class DeleteCommandParser implements Parser<DeleteContactCommand> {
>>>>>>> 1aecc3cb543c836c8b932029dec7533644924ea3
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
<<<<<<< HEAD
    public DeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);
=======
    public DeleteContactCommand parse(String args) throws ParseException {


        String[] splitArgs = args.trim().split("\\s+");

        if (args.length() < 1) {
            throw new ParseException(DELETE_COMMAND_USAGE);
        } else if (splitArgs.length < 2) {
            throw new ParseException(MESSAGE_MISSING_INDEX);
        }


        String entityType = splitArgs[0]; // either "contact, "job" or "company"
        String indexString = splitArgs[1];
        try {
            Index index = ParserUtil.parseIndex(indexString);

            switch (entityType) {
            case "contact":
                return new DeleteContactCommand(index);
            default:
                throw new ParseException(DELETE_COMMAND_USAGE);
            }
>>>>>>> 1aecc3cb543c836c8b932029dec7533644924ea3
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteContactCommand.MESSAGE_USAGE), pe);
        }
    }

}

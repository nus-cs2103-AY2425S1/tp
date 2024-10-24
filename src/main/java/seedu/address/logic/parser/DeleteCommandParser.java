package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

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
            String[] splitArgs = args.trim().split("\\s+");
            Index targetIndex = ParserUtil.parseIndex(splitArgs[0]);

            if (splitArgs.length > 1 && splitArgs[1].startsWith("po/")) {
                Index policyIndex = ParserUtil.parseIndex(splitArgs[1].substring(3));
                return new DeleteCommand(targetIndex, policyIndex);
            } else {
                return new DeleteCommand(targetIndex);
            }
        } catch (ParseException pe) {
            try {
                Name name = ParserUtil.parseName(args, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteCommand.MESSAGE_USAGE));
                return new DeleteCommand(name);
            } catch (ParseException pe2) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe2);
            }
        }
    }
}

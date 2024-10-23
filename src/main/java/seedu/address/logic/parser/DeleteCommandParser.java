package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        args = args.trim();

        String argumentType = getArgumentType(args);

        switch(argumentType) {

        case "PHONE":
            Phone phone = ParserUtil.parsePhone(args);
            return new DeleteCommand(phone);

        case "INDEX":
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);

        default:
            if (args.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }

            NameContainsKeywordsPredicate predicate =
                    new NameContainsKeywordsPredicate(args);
            return new DeleteCommand(predicate);


        }
    }

    public String getArgumentType(String args) {
        try {
            ParserUtil.parsePhone(args);
            return "PHONE";
        } catch (ParseException e1) {
            // Not a phone number, continue to other checks
            try {
                ParserUtil.parseIndex(args);
                return "INDEX";
            } catch (ParseException e2) {
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        DeleteCommand.MESSAGE_USAGE, e2);

            }
        }

        return "STRING";

    }
}

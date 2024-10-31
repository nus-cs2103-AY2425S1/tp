package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewWeddingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ViewWeddingCommand object.
 */
public class ViewWeddingCommandParser implements Parser<ViewWeddingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewWeddingCommand
     * and returns a ViewWeddingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewWeddingCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewWeddingCommand.MESSAGE_USAGE));
        }

        String weddingNameKeyword = trimmedArgs.toLowerCase();

        return new ViewWeddingCommand(new TagContainsKeywordsPredicate(weddingNameKeyword));
    }
}

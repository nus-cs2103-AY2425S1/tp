package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.TrackCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.CategoryContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class TrackCommandParser implements Parser<TrackCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TrackCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrackCommand.MESSAGE_USAGE));
        }

        return new TrackCommand(new CategoryContainsKeywordsPredicate(trimmedArgs));
    }

}

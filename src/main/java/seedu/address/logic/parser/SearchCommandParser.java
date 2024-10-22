package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.eventcommands.SearchEventCommand;
import seedu.address.logic.commands.personcommands.SearchPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.types.common.EventTagContainsKeywordsPredicate;
import seedu.address.model.types.common.PersonTagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the Search Person or Event
     * and returns the appropriate SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(ModelType modelType, String args) throws ParseException {
        if (modelType == ModelType.PERSON) {
            return parseForPerson(args);
        } else if (modelType == ModelType.EVENT) {
            return parseForEvent(args);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code String} of the arguments in the context of the SearchPersonCommand
     * and returns an SearchPersonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchPersonCommand parseForPerson(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchPersonCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new SearchPersonCommand(new PersonTagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

    /**
     * Parses the given {@code String} of the arguments in the context of the SearchEventCommand
     * and returns an SearchEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchEventCommand parseForEvent(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchPersonCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new SearchEventCommand(new EventTagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }
}

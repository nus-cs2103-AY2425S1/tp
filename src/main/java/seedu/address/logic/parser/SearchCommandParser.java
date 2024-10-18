package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.eventcommands.SearchEventCommand;
import seedu.address.logic.commands.personcommands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.types.common.EventTagContainsKeywordsPredicate;
import seedu.address.model.types.common.PersonTagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchCommand object
 */
public class SearchCommandParser implements Parser<Command> {
    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns a Command object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(ModelType modelType, String args) throws ParseException {
        if (modelType == ModelType.PERSON) {
            return parseForPerson(args);
        } else {
            return parseForEvent(args);
        }
    }

    /**
     * Parses the given {@code String} of the arguments in the context of the SearchCommand
     * and returns an SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parseForPerson(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new SearchCommand(new PersonTagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
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
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new SearchEventCommand(new EventTagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }
}

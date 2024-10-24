package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.SearchTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchTagCommand object
 */
public class SearchTagCommandParser implements Parser<SearchTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchTagCommand
     * and returns a SearchTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchTagCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new SearchTagCommand(new TagContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}

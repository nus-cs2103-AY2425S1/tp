package seedu.address.logic.parser;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsTagsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        // Collect all tag keywords provided with multiple `t/` prefixes
        List<String> tagKeywords = argMultimap.getAllValues(PREFIX_TAG);

        if (tagKeywords.isEmpty()) {
            throw new ParseException("At least one tag must be provided.");
        }

        return new FilterCommand(new PersonContainsTagsPredicate(tagKeywords));
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ModuleContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MODULE);

        if (!argMultimap.getPreamble().isEmpty()
                || argMultimap.getValue(PREFIX_NAME).isPresent()
                    && argMultimap.getValue(PREFIX_NAME).get().isEmpty()
                || argMultimap.getValue(PREFIX_MODULE).isPresent()
                    && argMultimap.getValue(PREFIX_MODULE).get().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            List<String> nameKeywords = Arrays.asList(argMultimap.getValue(PREFIX_NAME).get().split("\\s+"));
            return new FilterCommand(new NameContainsKeywordsPredicate(nameKeywords));
        } else if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
            String courseKeyword = argMultimap.getValue(PREFIX_MODULE).get();
            return new FilterCommand(new ModuleContainsKeywordsPredicate(courseKeyword));
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }
}

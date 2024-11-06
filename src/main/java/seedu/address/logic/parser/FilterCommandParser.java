package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.CourseContainsKeywordsPredicate;
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MODULE, PREFIX_COURSE);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_MODULE, PREFIX_COURSE);
        validateArguments(argMultimap);

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            return createFilterCommandByName(argMultimap);
        } else if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
            return createFilterCommandByModule(argMultimap);
        } else if (argMultimap.getValue(PREFIX_COURSE).isPresent()) {
            return createFilterCommandByCourse(argMultimap);
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    private void validateArguments(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getPreamble().isEmpty()
                || (argMultimap.getValue(PREFIX_NAME).isPresent()
                && argMultimap.getValue(PREFIX_NAME).get().isEmpty())
                || (argMultimap.getValue(PREFIX_MODULE).isPresent()
                && argMultimap.getValue(PREFIX_MODULE).get().isEmpty())
                || (argMultimap.getValue(PREFIX_COURSE).isPresent()
                && argMultimap.getValue(PREFIX_COURSE).get().isEmpty())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        int prefixCount = 0;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            prefixCount++;
        }
        if (argMultimap.getValue(PREFIX_MODULE).isPresent()) {
            prefixCount++;
        }
        if (argMultimap.getValue(PREFIX_COURSE).isPresent()) {
            prefixCount++;
        }

        if (prefixCount > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
    }

    private FilterCommand createFilterCommandByName(ArgumentMultimap argMultimap) {
        List<String> nameKeywords = Arrays.asList(argMultimap.getValue(PREFIX_NAME).get().split("\\s+"));
        return new FilterCommand(new NameContainsKeywordsPredicate(nameKeywords));
    }

    private FilterCommand createFilterCommandByModule(ArgumentMultimap argMultimap) {
        String moduleKeyword = argMultimap.getValue(PREFIX_MODULE).get();
        return new FilterCommand(new ModuleContainsKeywordsPredicate(moduleKeyword));
    }

    private FilterCommand createFilterCommandByCourse(ArgumentMultimap argMultimap) {
        List<String> courseKeywords = Arrays.asList(argMultimap.getValue(PREFIX_COURSE).get().split("\\s+"));
        return new FilterCommand(new CourseContainsKeywordsPredicate(courseKeywords));
    }
}

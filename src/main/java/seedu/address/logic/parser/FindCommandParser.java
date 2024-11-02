package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_FIND_KEYWORD;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNEXPECTED_PREAMBLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.ParserUtil.areAnyPrefixesPresent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ModuleRoleContainsKeywordsPredicate;
import seedu.address.model.person.ModuleRoleMap;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    public static final String MESSAGE_MISSING_SEARCH_KEYWORD = "At least one search parameter is required.";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MODULE);

        if (!argMultimap.getPreamble().isEmpty() && !argMultimap.getPreamble().equals(FindCommand.CHAINED)) {
            throw new ParseException(Messages.getErrorMessageWithUsage(MESSAGE_UNEXPECTED_PREAMBLE,
                    FindCommand.MESSAGE_USAGE));
        }

        // for this command, NAME_PREFIX or MODULE_PREFIX is mandatory; preamble is not allowed (except for "chained")
        if (!areAnyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_MODULE)) {
            throw new ParseException(Messages.getErrorMessageWithUsage(MESSAGE_MISSING_SEARCH_KEYWORD,
                    FindCommand.MESSAGE_USAGE));
        }

        List<String> nameKeywords = argMultimap.getAllValues(PREFIX_NAME);
        List<String> moduleRoleKeywords = argMultimap.getAllValues(PREFIX_MODULE);
        boolean isChained = argMultimap.getPreamble().equals(FindCommand.CHAINED);

        // all keywords must be non-empty and contain no whitespace
        if (nameKeywords.stream().anyMatch(String::isBlank) || moduleRoleKeywords.stream().anyMatch(String::isBlank)) {
            throw new ParseException(MESSAGE_EMPTY_FIND_KEYWORD);
        }

        ModuleRoleMap moduleRoleMapKeywords = ParserUtil.parseModuleRolePairs(moduleRoleKeywords);

        List<Predicate<Person>> predicates = new ArrayList<>();
        if (!nameKeywords.isEmpty()) {
            predicates.add(new NameContainsKeywordsPredicate(nameKeywords));
        }
        if (!moduleRoleKeywords.isEmpty()) {
            predicates.add(new ModuleRoleContainsKeywordsPredicate(moduleRoleMapKeywords));

        }

        return new FindCommand(predicates, isChained);
    }
}

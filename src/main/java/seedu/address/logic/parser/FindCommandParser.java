package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_FIND_KEYWORD;
import static seedu.address.logic.Messages.MESSAGE_UNEXPECTED_PREAMBLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.areAnyPrefixesPresent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ModuleRoleContainsKeywordsPredicate;
import seedu.address.model.person.ModuleRoleMap;
import seedu.address.model.person.ModuleRolePair;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.TagContainsKeywordsPredicate;

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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_MODULE, PREFIX_DESCRIPTION);

        if (!argMultimap.getPreamble().isEmpty() && !argMultimap.getPreamble().equals(FindCommand.CHAINED)) {
            throw new ParseException(Messages.getErrorMessageWithUsage(MESSAGE_UNEXPECTED_PREAMBLE,
                    FindCommand.MESSAGE_USAGE));
        }

        Prefix[] unexpectedPrefixes =
                Stream.of(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_DESCRIPTION)
                        .filter(prefix -> argMultimap.getValue(prefix).isPresent())
                        .toArray(Prefix[]::new);

        if (unexpectedPrefixes.length > 0) {
            throw new ParseException(Messages.getErrorMessageWithUsage(
                    Messages.getErrorMessageForUnexpectedPrefixes(unexpectedPrefixes),
                    FindCommand.MESSAGE_USAGE));
        }

        // for this command, NAME_PREFIX TAG_PREFIX or MODULE_PREFIX is mandatory;
        // preamble is not allowed (except for "chained")
        if (!areAnyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TAG, PREFIX_MODULE)) {
            throw new ParseException(Messages.getErrorMessageWithUsage(MESSAGE_MISSING_SEARCH_KEYWORD,
                    FindCommand.MESSAGE_USAGE));
        }

        List<String> nameKeywords = argMultimap.getAllValues(PREFIX_NAME);
        List<String> tagKeywords = argMultimap.getAllValues(PREFIX_TAG);
        List<String> moduleRoleKeywords = argMultimap.getAllValues(PREFIX_MODULE);
        boolean isChained = argMultimap.getPreamble().equals(FindCommand.CHAINED);

        // all keywords must be non-empty and contain no whitespace
        if (nameKeywords.stream().anyMatch(String::isBlank)
            || tagKeywords.stream().anyMatch(String::isBlank)
            || moduleRoleKeywords.stream().anyMatch(String::isBlank)) {
            throw new ParseException(Messages.getErrorMessageWithUsage(MESSAGE_EMPTY_FIND_KEYWORD,
                    FindCommand.MESSAGE_USAGE));
        }

        ModuleRoleMap moduleRoleMapKeywords = ParserUtil.parseModuleRoleMap(moduleRoleKeywords);
//        Set<ModuleRolePair> moduleRoleMapKeywords = new HashSet<>();



        List<Predicate<Person>> predicates = new ArrayList<>();
        if (!nameKeywords.isEmpty()) {
            predicates.add(new NameContainsKeywordsPredicate(nameKeywords));
        }
        if (!tagKeywords.isEmpty()) {
            predicates.add(new TagContainsKeywordsPredicate(tagKeywords));
        }
        if (!moduleRoleKeywords.isEmpty()) {
            predicates.add(new ModuleRoleContainsKeywordsPredicate(moduleRoleMapKeywords));
        }

        return new FindCommand(predicates, isChained);
    }
}

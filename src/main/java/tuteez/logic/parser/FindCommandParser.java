package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON;
import static tuteez.logic.parser.CliSyntax.PREFIX_NAME;
import static tuteez.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import tuteez.logic.commands.FindCommand;
import tuteez.logic.parser.exceptions.ParseException;
import tuteez.model.person.Person;
import tuteez.model.person.predicates.AddressContainsKeywordsPredicate;
import tuteez.model.person.predicates.CombinedPredicate;
import tuteez.model.person.predicates.LessonContainsKeywordsPredicate;
import tuteez.model.person.predicates.NameContainsKeywordsPredicate;
import tuteez.model.person.predicates.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS,
                PREFIX_TAG, PREFIX_LESSON);

        List<Predicate<Person>> predicates = createPredicates(argMultimap);
        CombinedPredicate combinedPredicate = new CombinedPredicate(predicates);
        return new FindCommand(combinedPredicate);
    }

    private List<Predicate<Person>> createPredicates(ArgumentMultimap argMultimap) {
        List<Predicate<Person>> predicates = new ArrayList<>();

        addPredicateIfPresent(argMultimap, predicates, PREFIX_NAME, NameContainsKeywordsPredicate::new);
        addPredicateIfPresent(argMultimap, predicates, PREFIX_ADDRESS, AddressContainsKeywordsPredicate::new);
        addPredicateIfPresent(argMultimap, predicates, PREFIX_TAG, TagContainsKeywordsPredicate::new);
        addPredicateIfPresent(argMultimap, predicates, PREFIX_LESSON, LessonContainsKeywordsPredicate::new);

        return predicates;
    }

    private <T extends Predicate<Person>> void addPredicateIfPresent(ArgumentMultimap argMultimap,
        List<Predicate<Person>> predicates, Prefix prefix, Function<List<String>, T> predicateConstructor) {
        if (argMultimap.getValue(prefix).isPresent()) {
            predicates.add(parseWithPredicate(argMultimap, prefix, predicateConstructor));
        }
    }

    private <T> T parseWithPredicate(
            ArgumentMultimap argMultimap, Prefix prefix, Function<List<String>, T> predicateConstructor) {
        return argMultimap.getValue(prefix)
                .map(value -> predicateConstructor.apply(Arrays.asList(value.split("\\s+"))))
                .orElseGet(() -> predicateConstructor.apply(Collections.emptyList()));
    }
}

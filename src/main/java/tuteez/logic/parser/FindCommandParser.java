package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_EMPTY_KEYWORD;
import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.Messages.MESSAGE_MISSING_PREFIX_FOR_FIND;
import static tuteez.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON_DAY;
import static tuteez.logic.parser.CliSyntax.PREFIX_LESSON_TIME;
import static tuteez.logic.parser.CliSyntax.PREFIX_NAME;
import static tuteez.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import tuteez.logic.commands.FindCommand;
import tuteez.logic.parser.exceptions.ParseException;
import tuteez.model.person.Person;
import tuteez.model.person.predicates.AddressContainsKeywordsPredicate;
import tuteez.model.person.predicates.CombinedPredicate;
import tuteez.model.person.predicates.LessonDayContainsKeywordsPredicate;
import tuteez.model.person.predicates.LessonTimeContainsKeywordsPredicate;
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
                PREFIX_TAG, PREFIX_LESSON_DAY, PREFIX_LESSON_TIME);

        boolean hasAtLeastOnePrefix = ArgumentTokenizer.checkHasAtLeastOnePrefix(argMultimap, PREFIX_NAME,
                PREFIX_ADDRESS, PREFIX_TAG, PREFIX_LESSON_DAY, PREFIX_LESSON_TIME);
        if (!hasAtLeastOnePrefix) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_MISSING_PREFIX_FOR_FIND));
        }

        List<Predicate<Person>> predicates = createPredicates(argMultimap);
        CombinedPredicate combinedPredicate = new CombinedPredicate(predicates);
        return new FindCommand(combinedPredicate);
    }

    private List<Predicate<Person>> createPredicates(ArgumentMultimap argMultimap) throws ParseException {
        List<Predicate<Person>> predicates = new ArrayList<>();

        addPredicateIfPresent(argMultimap, predicates, PREFIX_NAME, NameContainsKeywordsPredicate::new);
        addPredicateIfPresent(argMultimap, predicates, PREFIX_ADDRESS, AddressContainsKeywordsPredicate::new);
        addPredicateIfPresent(argMultimap, predicates, PREFIX_TAG, TagContainsKeywordsPredicate::new);
        addLessonDayPredicateIfPresent(argMultimap, predicates);
        addLessonTimePredicateIfPresent(argMultimap, predicates);

        return predicates;
    }

    private <T extends Predicate<Person>> void addPredicateIfPresent(ArgumentMultimap argMultimap,
        List<Predicate<Person>> predicates, Prefix prefix, Function<List<String>, T> predicateConstructor)
            throws ParseException {
        if (argMultimap.getValue(prefix).isPresent()) {
            predicates.add(parseWithPredicate(argMultimap, prefix, predicateConstructor));
        }
    }

    private void addLessonDayPredicateIfPresent(ArgumentMultimap argMultimap,
                                                List<Predicate<Person>> predicates) throws ParseException {
        if (argMultimap.getValue(PREFIX_LESSON_DAY).isPresent()) {
            predicates.add(parseWithLessonDayPredicate(argMultimap));
        }
    }

    private void addLessonTimePredicateIfPresent(ArgumentMultimap argMultimap,
                                                List<Predicate<Person>> predicates) throws ParseException {
        if (argMultimap.getValue(PREFIX_LESSON_TIME).isPresent()) {
            predicates.add(parseWithLessonTimePredicate(argMultimap));
        }
    }

    private String parseValue(ArgumentMultimap argMultimap, Prefix prefix) throws ParseException {
        Optional<String> value = argMultimap.getValue(prefix);
        String keywords = value.get();
        if (keywords.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_EMPTY_KEYWORD, prefix));
        }
        return keywords;
    }

    private <T> T parseWithPredicate(ArgumentMultimap argMultimap, Prefix prefix,
                                      Function<List<String>, T> predicateConstructor) throws ParseException {
        String keywords = parseValue(argMultimap, prefix);
        return predicateConstructor.apply(Arrays.asList(keywords.split("\\s+")));
    }

    private Predicate<Person> parseWithLessonDayPredicate(ArgumentMultimap argMultimap) throws ParseException {
        String keywords = parseValue(argMultimap, PREFIX_LESSON_DAY);
        List<String> dayKeywords = Arrays.asList(keywords.split("\\s+"));
        for (String dayKeyword : dayKeywords) {
            try {
                ParserUtil.checkValidLessonDayInfo(dayKeyword);
            } catch (ParseException e) {
                throw new ParseException("Invalid day keyword inputted after " + PREFIX_LESSON_DAY + ": "
                        + dayKeyword + "\n" + e.getMessage());
            }
        }
        return new LessonDayContainsKeywordsPredicate(dayKeywords);
    }

    private Predicate<Person> parseWithLessonTimePredicate(ArgumentMultimap argMultimap) throws ParseException {
        String keywords = parseValue(argMultimap, PREFIX_LESSON_TIME);
        List<String> timeKeywords = Arrays.asList(keywords.split("\\s+"));
        for (String timeKeyword : timeKeywords) {
            try {
                ParserUtil.checkValidLessonTimeInfo(timeKeyword);
            } catch (ParseException e) {
                throw new ParseException("Invalid time keyword inputted after " + PREFIX_LESSON_TIME + ": "
                        + timeKeyword + "\n" + e.getMessage());
            }
        }
        return new LessonTimeContainsKeywordsPredicate(timeKeywords);
    }
}

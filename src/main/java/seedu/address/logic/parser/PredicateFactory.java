package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FieldContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentHasPaidPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * A factory class for creating a list of predicates to filter Person objects based on
 * various fields such as name, phone, email, address, payment status, and tags.
 */
public class PredicateFactory {

    /**
     * Creates a list of predicates based on the input fields provided in ArgumentMultimap.
     * Fields could include name, phone, email, address, payment, attendance and tags.
     *
     * @param argMultimap The mapping of prefixes to user inputs.
     * @return A unmodifiable list of predicates used to filter Person objects.
     * @throws ParseException If any of the input values cannot be parsed.
     */
    public static List<Predicate<Person>> createPredicates(ArgumentMultimap argMultimap) throws ParseException {
        List<Predicate<Person>> predicates = new ArrayList<>();
        processFieldPredicates(argMultimap, predicates);
        processPaymentPredicate(argMultimap, predicates);
        processTagPredicates(argMultimap, predicates);
        return Collections.unmodifiableList(predicates);
    }

    private static void processFieldPredicates(ArgumentMultimap argMultimap, List<Predicate<Person>> predicates)
            throws ParseException {
        addFieldPredicate(argMultimap, PREFIX_NAME, Person::getFullName, predicates, true);
        addFieldPredicate(argMultimap, PREFIX_PHONE, Person::getPhoneValue, predicates, false);
        addFieldPredicate(argMultimap, PREFIX_EMAIL, Person::getEmailValue, predicates, false);
        addFieldPredicate(argMultimap, PREFIX_ADDRESS, Person::getAddressValue, predicates, true);
    }

    private static void addFieldPredicate(
            ArgumentMultimap argMultimap, Prefix prefix,
            Function<Person, String> fieldExtractor,
            List<Predicate<Person>> predicates, boolean hasMultipleKeywords) throws ParseException {
        if (argMultimap.getValue(prefix).isPresent()) {
            String value = argMultimap.getValue(prefix).get();
            String trimmedValue = hasMultipleKeywords
                    ? ParserUtil.parseMultipleWordsFromFindCommand(value)
                    : ParserUtil.parseSingleWordFromFindCommand(value);
            predicates.add(new FieldContainsKeywordsPredicate<>(Arrays.asList(trimmedValue.split("\\s+")),
                    fieldExtractor, hasMultipleKeywords));
        }
    }

    private static void processPaymentPredicate(ArgumentMultimap argMultimap, List<Predicate<Person>> predicates)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_PAYMENT).isPresent()) {
            boolean isPaymentUpToDate = ParserUtil.parseBoolean(argMultimap.getValue(PREFIX_PAYMENT).get());
            predicates.add(new StudentHasPaidPredicate(isPaymentUpToDate));
        }
    }

    private static void processTagPredicates(ArgumentMultimap argMultimap, List<Predicate<Person>> predicates)
            throws ParseException {
        if (!argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            for (String eachtagKeyword : argMultimap.getAllValues(PREFIX_TAG)) {
                String trimmedTag = ParserUtil.parseMultipleWordsFromFindCommand(eachtagKeyword);
                predicates.add(new TagContainsKeywordsPredicate(Arrays.asList(trimmedTag.split("\\s+"))));
            }
        }
    }

}

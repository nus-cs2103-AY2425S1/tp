package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;
import static seedu.address.model.predicate.FieldContainsKeywordsPredicate.ADDRESS_IDENTIFIER;
import static seedu.address.model.predicate.FieldContainsKeywordsPredicate.EMAIL_IDENTIFIER;
import static seedu.address.model.predicate.FieldContainsKeywordsPredicate.NAME_IDENTIFIER;
import static seedu.address.model.predicate.FieldContainsKeywordsPredicate.PHONE_IDENTIFIER;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.predicate.FieldContainsKeywordsPredicate;
import seedu.address.model.predicate.StudentAttendedTutorialPredicate;
import seedu.address.model.predicate.StudentHasPaidPredicate;
import seedu.address.model.predicate.SubjectMatchesKeywordsPredicate;
import seedu.address.model.predicate.TagContainsKeywordPredicate;

/**
 * A factory class for creating a list of predicates to filter Person objects based on
 * various fields such as name, phone, email, address, payment status, and tags.
 */
public class PredicateFactory {
    private static final Logger logger = LogsCenter.getLogger(PredicateFactory.class);
    /**
     * Creates a list of predicates based on the input fields provided in ArgumentMultimap.
     * Fields could include name, phone, email, address, payment, attendance and tags.
     *
     * @param argMultimap The mapping of prefixes to user inputs.
     * @return FindCommand with a new list of participation predicates and list of person predicates.
     * @throws ParseException If any of the input values cannot be parsed.
     */
    public static FindCommand createPredicates(ArgumentMultimap argMultimap) throws ParseException {
        List<Predicate<Person>> personPredicates = new ArrayList<>();
        List<Predicate<Participation>> participationPredicates = new ArrayList<>();
        processFieldPredicates(argMultimap, personPredicates);
        processPaymentPredicate(argMultimap, personPredicates);
        processTagPredicate(argMultimap, personPredicates);
        processTutorialPredicate(argMultimap, participationPredicates);
        processAttendancePredicate(argMultimap, participationPredicates);
        logger.info(" - Predicates successfully created, creating FindCommand object now. ");
        return new FindCommand(personPredicates, participationPredicates);
    }

    private static void processFieldPredicates(ArgumentMultimap argMultimap, List<Predicate<Person>> personPredicates)
            throws ParseException {
        addFieldPredicate(argMultimap, PREFIX_NAME, Person::getFullName, personPredicates, true, NAME_IDENTIFIER);
        addFieldPredicate(argMultimap, PREFIX_PHONE, Person::getPhoneValue, personPredicates, false, PHONE_IDENTIFIER);
        addFieldPredicate(argMultimap, PREFIX_EMAIL, Person::getEmailValue, personPredicates, false, EMAIL_IDENTIFIER);
        addFieldPredicate(argMultimap, PREFIX_ADDRESS, Person::getAddressValue, personPredicates,
                true, ADDRESS_IDENTIFIER);
    }

    private static void addFieldPredicate(
            ArgumentMultimap argMultimap, Prefix prefix,
            Function<Person, String> fieldExtractor,
            List<Predicate<Person>> personPredicates,
            boolean hasMultipleKeywords,
            String fieldIdentifier) throws ParseException {
        if (argMultimap.getValue(prefix).isPresent()) {
            String value = argMultimap.getValue(prefix).get();
            String trimmedValue = hasMultipleKeywords
                    ? ParserUtil.parseMultipleWordsFromFindCommand(value)
                    : ParserUtil.parseSingleWordFromFindCommand(value);
            personPredicates.add(new FieldContainsKeywordsPredicate<>(Arrays.asList(trimmedValue.split("\\s+")),
                    fieldExtractor, hasMultipleKeywords, fieldIdentifier));
        }
    }

    private static void processPaymentPredicate(ArgumentMultimap argMultimap, List<Predicate<Person>> personPredicates)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_PAYMENT).isPresent()) {
            boolean isPaymentUpToDate = ParserUtil.parseBoolean(argMultimap.getValue(PREFIX_PAYMENT).get());
            personPredicates.add(new StudentHasPaidPredicate(isPaymentUpToDate));
        }
    }

    private static void processAttendancePredicate(ArgumentMultimap argMultimap,
                                                   List<Predicate<Participation>> participationPredicates)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_ATTENDANCE).isPresent()) {
            LocalDate[] datesArray = ParserUtil.parseAttendanceDateRange(argMultimap.getValue(PREFIX_ATTENDANCE).get());
            participationPredicates.add(new StudentAttendedTutorialPredicate(datesArray[0], datesArray[1]));
        }

    }

    private static void processTagPredicate(ArgumentMultimap argMultimap, List<Predicate<Person>> personPredicates)
            throws ParseException {
        if (!argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            for (String eachTagKeyword : argMultimap.getAllValues(PREFIX_TAG)) {
                String trimmedTag = ParserUtil.parseSingleWordFromFindCommand(eachTagKeyword);
                personPredicates.add(new TagContainsKeywordPredicate(trimmedTag));
            }
        }
    }

    private static void processTutorialPredicate(ArgumentMultimap argMultimap,
                                                List<Predicate<Participation>> participationPredicates)
            throws ParseException {
        if (!argMultimap.getAllValues(PREFIX_TUTORIAL).isEmpty()) {
            for (String eachSubjectWords : argMultimap.getAllValues(PREFIX_TUTORIAL)) {
                String trimmedSubject = ParserUtil.parseMultipleWordsFromFindCommand(eachSubjectWords);
                participationPredicates.add(new SubjectMatchesKeywordsPredicate(trimmedSubject));
            }
        }
    }

}

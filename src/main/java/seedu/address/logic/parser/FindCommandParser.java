package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.PostalContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object.
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");

        List<String> phoneKeywords = Arrays.stream(keywords)
                .filter(this::isNumeric)
                .collect(Collectors.toList());

        List<String> nameKeywords = Arrays.stream(keywords)
                .filter(keyword -> !isNumeric(keyword) && !isPostalCode(keyword))
                .collect(Collectors.toList());

        List<String> postalKeywords = Arrays.stream(keywords)
                .filter(this::isPostalCode)
                .collect(Collectors.toList());

        Predicate<Person> namePredicate = new NameContainsKeywordsPredicate(nameKeywords);
        Predicate<Person> phonePredicate = new PhoneContainsKeywordsPredicate(phoneKeywords);
        Predicate<Person> postalPredicate = new PostalContainsKeywordsPredicate(postalKeywords);

        List<Predicate<Person>> predicates = new ArrayList<>();

        if (!nameKeywords.isEmpty()) {
            predicates.add(namePredicate);
        }
        if (!phoneKeywords.isEmpty()) {
            predicates.add(phonePredicate);
        }
        if (!postalKeywords.isEmpty()) {
            predicates.add(postalPredicate);
        }

        if (!predicates.isEmpty()) {
            Predicate<Person> combinedPredicate;
            if (predicates.size() == 1) {
                combinedPredicate = predicates.get(0);
            } else {
                combinedPredicate = predicates.stream().reduce(x -> false, Predicate::or);
            }
            return new FindCommand(combinedPredicate);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Utility method to check if a string is numeric (i.e., contains only phone digits and not 6 digits long).
     * @param str The string to check.
     * @return True if the string is numeric, false otherwise.
     */
    private boolean isNumeric(String str) {
        return str.matches("\\d+") && str.length() != 6;
    }

    /**
     * Utility method to check if a string is a postal code (i.e., 6 digit number).
     * @param str The string to check.
     * @return True if the string is a postal code, false otherwise.
     */
    private boolean isPostalCode(String str) {
        // checks that the postal code is exactly 6 digits
        return str.matches("\\d{6}");
    }
}

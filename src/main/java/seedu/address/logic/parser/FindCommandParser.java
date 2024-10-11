package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

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

        // Separate keywords into phone and name
        List<String> phoneKeywords = Arrays.stream(keywords)
                .filter(this::isNumeric) // Names removed
                .collect(Collectors.toList());

        List<String> nameKeywords = Arrays.stream(keywords)
                .filter(keyword -> !isNumeric(keyword)) // Only keep non-numeric keywords ie names
                .collect(Collectors.toList());

//        Predicate<Person> namePredicate = nameKeywords.isEmpty()
//                ? person -> false // no name keywords, hence ignore search
//                : new NameContainsKeywordsPredicate(nameKeywords);
//
//        Predicate<Person> phonePredicate = phoneKeywords.isEmpty()
//                ? person -> false // no phone keywords, hence ignore search
//                : new PhoneContainsKeywordsPredicate(phoneKeywords);
        NameContainsKeywordsPredicate namePredicate = new NameContainsKeywordsPredicate(nameKeywords);
        PhoneContainsKeywordsPredicate phonePredicate = new PhoneContainsKeywordsPredicate(phoneKeywords);

        if (!nameKeywords.isEmpty() && !phoneKeywords.isEmpty()) {
            return new FindCommand(namePredicate.and(phonePredicate)); // Explicit combination
        } else if (!nameKeywords.isEmpty()) {
            return new FindCommand(namePredicate);  // Only names
        } else {
            return new FindCommand(phonePredicate);  // Only phones
        }
//
//        Predicate<Person> combinedPredicate = namePredicate.or(phonePredicate);
//
//        return new FindCommand(combinedPredicate);

//        // Check if all keywords are numeric (for phone search)
//        if (Arrays.stream(keywords).allMatch(this::isNumeric)) {
//            return new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(keywords)));
//        } else {
//            // Otherwise, treat it as a name search
//            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
//            }
    }

    /**
     * Utility method to check if a string is numeric (i.e., contains only digits).
     * @param str The string to check.
     * @return True if the string is numeric, false otherwise.
     */
    private boolean isNumeric(String str) {
        return str.matches("\\d+"); // Matches any string that contains only digits
    }
}

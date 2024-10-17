package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POLICY_TYPE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.CompositePredicate;
import seedu.address.model.person.EmailMatchesPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneMatchesPredicate;
import seedu.address.model.person.PolicyTypeMatchesPredicate;
import seedu.address.model.policy.PolicyType;

/**
 * Parses input arguments and creates a new FindCommand object.
 */
public class FindCommandParser implements Parser<FindCommand> {

    // Define email validation regex at the class level
    private static final String EMAIL_VALIDATION_REGEX =
            "[^\\W_]+([+_.-][^\\W_]+)*@([^\\W_]+(-[^\\W_]+)*\\.)+([^\\W_]+(-[^\\W_]+)*){2,}$";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        // Tokenize the input arguments using prefixes
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_POLICY_TYPE);

        // Validate that at least one field is provided and no extra preamble is present
        if (!areAnyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_POLICY_TYPE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        // Initialize the list of predicates
        List<Predicate<Person>> predicatesList = new ArrayList<>();

        // Process name parameter
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String nameInput = argMultimap.getValue(PREFIX_NAME).get().trim();

            // Validate name length
            if (nameInput.length() < 1 || nameInput.length() > 100) {
                throw new ParseException("Error: Name must be between 1 and 100 characters.");
            }

            String[] nameKeywords = nameInput.split("\\s+");
            predicatesList.add(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

        // Process phone parameter
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String phoneInput = argMultimap.getValue(PREFIX_PHONE).get().trim();

            // Validate phone format
            if (!phoneInput.matches("\\d{8}")) {
                throw new ParseException("Error: Phone number must be exactly 8 digits.");
            }

            predicatesList.add(new PhoneMatchesPredicate(phoneInput));
        }

        // Process email parameter
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String emailInput = argMultimap.getValue(PREFIX_EMAIL).get().trim();

            // Validate email format
            if (!emailInput.matches(EMAIL_VALIDATION_REGEX)) {
                throw new ParseException("Error: Invalid email format. Please provide a valid email address.");
            }

            predicatesList.add(new EmailMatchesPredicate(emailInput));
        }

        // Process address parameter
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String addressInput = argMultimap.getValue(PREFIX_ADDRESS).get().trim();

            // Validate address length
            if (addressInput.length() < 1 || addressInput.length() > 255) {
                throw new ParseException("Error: Address must be between 1 and 255 characters.");
            }

            String[] addressKeywords = addressInput.split("\\s+");
            predicatesList.add(new AddressContainsKeywordsPredicate(Arrays.asList(addressKeywords)));
        }

        // Process policy type parameter
        if (argMultimap.getValue(PREFIX_POLICY_TYPE).isPresent()) {
            String policyTypeInput = argMultimap.getValue(PREFIX_POLICY_TYPE).get().trim();

            // Validate policy type
            PolicyType policyType;
            try {
                policyType = PolicyType.fromString(policyTypeInput);
            } catch (IllegalArgumentException e) {
                throw new ParseException(
                        "Error: Invalid policy type. Please provide a valid policy type (e.g., Life, Health, Education).");
            }

            predicatesList.add(new PolicyTypeMatchesPredicate(policyType));
        }

        // Combine all predicates into a CompositePredicate
        Predicate<Person> combinedPredicate = new CompositePredicate(predicatesList);

        return new FindCommand(combinedPredicate);
    }

    /**
     * Returns true if at least one of the prefixes contains values in the given ArgumentMultimap.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Arrays.stream(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

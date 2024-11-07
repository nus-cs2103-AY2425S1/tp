package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneNumberMatchesPredicate;
import seedu.address.model.person.Role;
import seedu.address.model.person.RoleMatchesPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {
    public static final String MESSAGE_EMPTY_SEARCH_PREFIX = "The prefix cannot be empty. Please input a prefix.";
    public static final String MESSAGE_INVALID_SEARCH_ROLE_INPUT = "Invalid role";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG,
                PREFIX_GROUP_NAME, PREFIX_PHONE, PREFIX_ROLE);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TAG, PREFIX_GROUP_NAME, PREFIX_PHONE, PREFIX_ROLE);

        String nameArgs = argMultimap.getValue(PREFIX_NAME).orElse("");
        String tagArgs = argMultimap.getValue(PREFIX_TAG).orElse("");
        String groupArgs = argMultimap.getValue(PREFIX_GROUP_NAME).orElse("");
        String phoneArgs = argMultimap.getValue(PREFIX_PHONE).orElse("");
        String roleArgs = argMultimap.getValue(PREFIX_ROLE).orElse("");

        // Create a map of prefixes and associated values to check for empty inputs
        Map<Prefix, String> prefixValueMap = new HashMap<>();
        prefixValueMap.put(PREFIX_NAME, nameArgs);
        prefixValueMap.put(PREFIX_TAG, tagArgs);
        prefixValueMap.put(PREFIX_GROUP_NAME, groupArgs);
        prefixValueMap.put(PREFIX_PHONE, phoneArgs);
        prefixValueMap.put(PREFIX_ROLE, roleArgs);

        checkForEmptyInputs(argMultimap, prefixValueMap);
        Predicate<Person> combinedPredicate = combinePredicates(Arrays.asList(
                createPredicate("name", nameArgs),
                createPredicate("tag", tagArgs),
                createPredicate("phone", phoneArgs),
                createPredicate("role", roleArgs)
        ));

        if (combinedPredicate == null && groupArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        return new SearchCommand(combinedPredicate, groupArgs);
    }

    /**
     * Checks for any empty input values for the specified prefixes.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the user's input and associated prefixes.
     * @param prefixValueMap A map of {@code Prefix} and associated values to check for empty input.
     * @throws ParseException If any prefix is present but its associated value is empty.
     */
    private void checkForEmptyInputs(ArgumentMultimap argMultimap, Map<Prefix, String> prefixValueMap)
            throws ParseException {
        for (Map.Entry<Prefix, String> entry : prefixValueMap.entrySet()) {
            validatePrefixNotEmpty(argMultimap, entry.getKey(), entry.getValue());
        }
    }

    /**
     * Checks if the value for a given prefix is empty in the {@code ArgumentMultimap}.
     * If the prefix is present but its associated value is empty, a {@code ParseException} is thrown.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the user's input and associated prefixes.
     * @param prefix The {@code Prefix} to check for empty input.
     * @param value The value associated with the {@code prefix} to check.
     * @throws ParseException If the prefix is present but its value is empty.
     */
    private void validatePrefixNotEmpty(ArgumentMultimap argMultimap, Prefix prefix, String value)
            throws ParseException {
        if (argMultimap.getValue(prefix).isPresent() && value.trim().isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_SEARCH_PREFIX);
        }
    }


    private Predicate<Person> createPredicate(String type, String args) throws ParseException {
        if (args.isEmpty()) {
            return null;
        }

        switch (type.toLowerCase()) {
        case "name":
            return new NameContainsKeywordsPredicate(Arrays.asList(args.split("\\s+")));
        case "tag":
            return new TagContainsKeywordsPredicate(Arrays.asList(args.split("\\s+")));
        case "phone":
            return new PhoneNumberMatchesPredicate(args);
        case "role":
            try {
                Role role = Role.fromString(args);
                return new RoleMatchesPredicate(role);
            } catch (IllegalArgumentException e) {
                throw new ParseException(MESSAGE_INVALID_SEARCH_ROLE_INPUT);
            }
        default:
            throw new IllegalArgumentException("Invalid predicate type: " + type);
        }
    }

    private Predicate<Person> combinePredicates(List<Predicate<Person>> predicates) {
        return predicates.stream()
                .filter(Objects::nonNull) // Filter out null predicates
                .reduce(Predicate::and)
                .orElse(null); // Return null if all predicates are null
    }

}

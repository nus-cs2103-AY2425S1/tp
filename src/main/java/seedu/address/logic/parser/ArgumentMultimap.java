package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Stores mapping of prefixes to their respective arguments.
 * Each key may be associated with multiple argument values.
 * Values for a given key are stored in a list, and the insertion ordering is maintained.
 * Keys are unique, but the list of argument values may contain duplicate argument values, i.e. the same argument value
 * can be inserted multiple times for the same prefix.
 */
public class ArgumentMultimap {

    /** Prefixes mapped to their respective arguments**/
    private final Map<Prefix, List<String>> argMultimap = new HashMap<>();

    /**
     * Associates the specified argument value with {@code prefix} key in this map.
     * If the map previously contained a mapping for the key, the new value is appended to the list of existing values.
     *
     * @param prefix   Prefix key with which the specified argument value is to be associated
     * @param argValue Argument value to be associated with the specified prefix key
     */
    public void put(Prefix prefix, String argValue) {
        List<String> argValues = getAllValues(prefix);
        argValues.add(argValue);
        argMultimap.put(prefix, argValues);
    }

    /**
     * Returns the last value of {@code prefix}.
     */
    public Optional<String> getValue(Prefix prefix) {
        List<String> values = getAllValues(prefix);
        return values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
    }

    /**
     * Returns all values of {@code prefix}.
     * If the prefix does not exist or has no values, this will return an empty list.
     * Modifying the returned list will not affect the underlying data structure of the ArgumentMultimap.
     */
    public List<String> getAllValues(Prefix prefix) {
        if (!argMultimap.containsKey(prefix)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(argMultimap.get(prefix));
    }

    /**
     * Returns the preamble (text before the first valid prefix). Trims any leading/trailing spaces.
     */
    public String getPreamble() {
        return getValue(new Prefix("")).orElse("");
    }

    /**
     * Throws a {@code ParseException} if any of the prefixes given in {@code prefixes} appeared more than
     * once among the arguments.
     */
    public void verifyNoDuplicatePrefixesFor(Prefix... prefixes) throws ParseException {
        Prefix[] duplicatedPrefixes = Stream.of(prefixes).distinct()
                .filter(prefix -> argMultimap.containsKey(prefix) && argMultimap.get(prefix).size() > 1)
                .toArray(Prefix[]::new);

        if (duplicatedPrefixes.length > 0) {
            throw new ParseException(Messages.getErrorMessageForDuplicatePrefixes(duplicatedPrefixes));
        }
    }

    /**
     * Checks if the {@code argumentMultimap} contains none of the prefixes required for FindCommand.
     * Prefixes required for FindCommand includes "n/", "p/", "e/", "d/", "/r".
     * @return True if it contains none of the prefixes, False otherwise.
     */
    public boolean hasNoFindCommandPrefix() {
        return getValue(PREFIX_NAME).isEmpty() && getValue(PREFIX_PHONE).isEmpty()
                && getValue(PREFIX_EMAIL).isEmpty() && getValue(PREFIX_DEPARTMENT).isEmpty()
                && getValue(PREFIX_ROLE).isEmpty();
    }

    /**
     * Checks if the {@code argumentMultimap} contains any empty string mapped to any of the prefixes
     * required for FindCommand.
     * Prefix required for FindCommand includes "n/", "p/", "e/", "d/", "/r".
     *
     * @return True if at least one empty string is mapped to a prefix.
     */
    public boolean hasEmptyCommandPrefix() {
        List<String> listOfNameKeywords = getAllValues(PREFIX_NAME);
        if (!listOfNameKeywords.isEmpty()) {
            String firstNameKeyword = listOfNameKeywords.get(0);
            return firstNameKeyword.trim().isEmpty();
        }
        List<String> listOfPhoneKeywords = getAllValues(PREFIX_PHONE);
        if (!listOfPhoneKeywords.isEmpty()) {
            String firstPhoneKeyword = listOfPhoneKeywords.get(0);
            return firstPhoneKeyword.trim().isEmpty();
        }
        List<String> listOfEmailKeywords = getAllValues(PREFIX_EMAIL);
        if (!listOfEmailKeywords.isEmpty()) {
            String firstEmailKeyword = listOfEmailKeywords.get(0);
            return firstEmailKeyword.trim().isEmpty();
        }
        List<String> listOfDepartmentKeywords = getAllValues(PREFIX_DEPARTMENT);
        if (!listOfDepartmentKeywords.isEmpty()) {
            String firstDepartmentKeyword = listOfDepartmentKeywords.get(0);
            return firstDepartmentKeyword.trim().isEmpty();
        }
        List<String> listOfRoleKeywords = getAllValues(PREFIX_ROLE);
        if (!listOfRoleKeywords.isEmpty()) {
            String firstRoleKeyword = listOfRoleKeywords.get(0);
            return firstRoleKeyword.trim().isEmpty();
        }
        return false;
    }
}

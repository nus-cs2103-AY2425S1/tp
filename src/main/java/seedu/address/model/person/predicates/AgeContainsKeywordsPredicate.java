package seedu.address.model.person.predicates;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Age} matches any of the ages given.
 */
public class AgeContainsKeywordsPredicate implements Predicate<Person> {
    public static final String VALIDATION_REGEX = "\\d+|(\\d+-\\d+)";
    public static final String MESSAGE_CONSTRAINTS = "Each age criteria should contain non-negative integers, "
            + "between 0 and 150 (both inclusive),\n"
            + "and be in the format 'number' or 'number-number'!\n"
            + "Example: a/23 30-34";

    private static final Pattern RANGE_PATTERN = Pattern.compile("(\\d+)-(\\d+)");
    private final Set<String> keywords;

    /**
     * Constructs a {@code AgeContainsKeywordsPredicate}.
     *
     * @param keywords A list of valid age keywords
     */
    public AgeContainsKeywordsPredicate(Set<String> keywords) {
        requireNonNull(keywords);
        for (String k : keywords) {
            checkArgument(isValidInput(k), MESSAGE_CONSTRAINTS);
        }
        this.keywords = keywords;
    }

    /**
     * Returns true if a given string is a valid input.
     * A valid input should be a non-negative integer, in the format
     * 'number' or 'number-number'.
     *
     * @return true if valid, otherwise false
     */
    public static boolean isValidInput(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        } else {
            var matcher = RANGE_PATTERN.matcher(test);
            if (matcher.matches()) {
                try {
                    return Integer.parseInt(matcher.group(1)) <= 150
                            && Integer.parseInt(matcher.group(1)) >= 0
                            && Integer.parseInt(matcher.group(2)) <= 150
                            && Integer.parseInt(matcher.group(2)) >= 0;
                } catch (NumberFormatException e) {
                    return false;
                }
            } else {
                return Integer.parseInt(test) <= 150 && Integer.parseInt(test) >= 0;
            }
        }

    }

    @Override
    public boolean test(Person person) {
        int age = Integer.parseInt(person.getAge().value);
        return keywords.stream().anyMatch(keyword -> {
            // Check for range
            var matcher = RANGE_PATTERN.matcher(keyword);
            if (matcher.matches()) {
                int bound1 = Integer.parseInt(matcher.group(1));
                int bound2 = Integer.parseInt(matcher.group(2));
                int lowerBound = Math.min(bound1, bound2);
                int upperBound = Math.max(bound1, bound2);
                return age >= lowerBound && age <= upperBound;
            } else {
                // Check for individual age
                return StringUtil.containsWordIgnoreCase(String.valueOf(age),
                        keyword.replaceFirst("0{0," + (keyword.length() - 1) + "}", ""));
            }
        });
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AgeContainsKeywordsPredicate)) {
            return false;
        }

        AgeContainsKeywordsPredicate otherAgeContainsKeywordsPredicate = (AgeContainsKeywordsPredicate) other;
        return keywords.equals(otherAgeContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

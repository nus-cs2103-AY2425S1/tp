package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Age} matches any of the ages given.
 */
public class AgeContainsKeywordsPredicate implements Predicate<Person> {
    private static final Pattern RANGE_PATTERN = Pattern.compile("(\\d+)-(\\d+)");
    private final List<String> keywords;

    public AgeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        int age = Integer.parseInt(person.getAge().value);
        return keywords.stream().anyMatch(keyword -> {
            // Check for range
            var matcher = RANGE_PATTERN.matcher(keyword);
            if (matcher.matches()) {
                int lowerBound = Integer.parseInt(matcher.group(1));
                int upperBound = Integer.parseInt(matcher.group(2));
                return age >= lowerBound && age <= upperBound;
            } else {
                // Check for individual age
                return StringUtil.containsWordIgnoreCase(String.valueOf(age), keyword);
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

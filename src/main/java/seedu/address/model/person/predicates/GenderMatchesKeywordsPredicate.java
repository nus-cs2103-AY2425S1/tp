package seedu.address.model.person.predicates;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Gender} matches any of the keywords given.
 */
public class GenderMatchesKeywordsPredicate implements Predicate<Person> {
    public static final String VALIDATION_REGEX = "^[FfMm]$";
    public static final String MESSAGE_CONSTRAINTS = "Each gender criteria should only be 'F' / 'f' (Female) or "
            + "'M' / 'm' (Male)";

    private final Set<String> keywords;

    /**
     * Constructs a {@code GenderMatchesKeywordsPredicate}.
     *
     * @param keywords A list of valid gender keywords
     */
    public GenderMatchesKeywordsPredicate(Set<String> keywords) {
        requireNonNull(keywords);
        for (String k : keywords) {
            checkArgument(isValidInput(k), MESSAGE_CONSTRAINTS);
        }
        this.keywords = keywords;
    }

    /**
     * Returns true if a given string is a valid gender.
     *
     * @return true if valid, otherwise false
     */
    public static boolean isValidInput(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getGender().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GenderMatchesKeywordsPredicate)) {
            return false;
        }

        GenderMatchesKeywordsPredicate otherGenerderMatchesKeywordsPredicate = (GenderMatchesKeywordsPredicate) other;
        return keywords.equals(otherGenerderMatchesKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

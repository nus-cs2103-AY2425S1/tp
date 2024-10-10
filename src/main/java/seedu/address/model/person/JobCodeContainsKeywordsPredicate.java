package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code JobCode} matches any of the keywords given.
 */
public class JobCodeContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public JobCodeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getJobCode().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof JobCodeContainsKeywordsPredicate)) {
            return false;
        }
        JobCodeContainsKeywordsPredicate otherPredicate = (JobCodeContainsKeywordsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }
}

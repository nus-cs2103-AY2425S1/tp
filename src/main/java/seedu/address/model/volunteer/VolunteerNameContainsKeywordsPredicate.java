package seedu.address.model.volunteer;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Volunteer's} {@code Name} matches any of the keywords given.
 */
public class VolunteerNameContainsKeywordsPredicate implements Predicate<Volunteer> {
    private final List<String> keywords;

    public VolunteerNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Volunteer volunteer) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(volunteer.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VolunteerNameContainsKeywordsPredicate)) {
            return false;
        }

        VolunteerNameContainsKeywordsPredicate otherVolunteerNameContainsKeywordsPredicate =
                (VolunteerNameContainsKeywordsPredicate) other;
        return keywords.equals(otherVolunteerNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

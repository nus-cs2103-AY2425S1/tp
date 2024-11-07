package seedu.address.model.volunteer;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Volunteer}'s {@code Name} matches the given keyword.
 */
public class VolunteerNameContainsKeywordPredicate implements Predicate<Volunteer> {
    private final String keyword;

    public VolunteerNameContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Volunteer volunteer) {
        return volunteer.getName().fullName.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VolunteerNameContainsKeywordPredicate)) {
            return false;
        }

        VolunteerNameContainsKeywordPredicate otherPredicate = (VolunteerNameContainsKeywordPredicate) other;
        return keyword.equals(otherPredicate.keyword);
    }

    public String getKeyword() {
        return this.keyword;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}

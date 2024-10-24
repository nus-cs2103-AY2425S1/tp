package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Guest;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Guest}'s {@code Rsvp} matches any of the keywords given.
 */
public class RsvpContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public RsvpContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (!(person instanceof Guest)) {
            return false;
        }
        Guest guest = (Guest) person;
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(guest.getRsvp().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RsvpContainsKeywordsPredicate)) {
            return false;
        }

        RsvpContainsKeywordsPredicate otherRsvpContainsKeywordsPredicate = (RsvpContainsKeywordsPredicate) other;
        return keywords.equals(otherRsvpContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

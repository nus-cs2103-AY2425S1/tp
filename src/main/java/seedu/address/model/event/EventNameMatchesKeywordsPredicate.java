package seedu.address.model.event;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Event}'s {@code EventName} strictly matches the keywords given.
 */
public class EventNameMatchesKeywordsPredicate implements Predicate<Event> {
    private final String keywords;

    public EventNameMatchesKeywordsPredicate(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        return keywords.equalsIgnoreCase(event.getName().toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventNameMatchesKeywordsPredicate)) {
            return false;
        }

        EventNameMatchesKeywordsPredicate otherNameContainsKeywordsPredicate =
                (EventNameMatchesKeywordsPredicate) other;
        return keywords.equals(otherNameContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

package seedu.address.model.event;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Event}'s {@code Name} matches the given keyword.
 */
public class EventNameContainsKeywordPredicate implements Predicate<Event> {
    private final String keyword;

    public EventNameContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Event event) {
        return event.getName().eventName.toLowerCase().contains(keyword.toLowerCase());
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventNameContainsKeywordPredicate)) {
            return false;
        }

        EventNameContainsKeywordPredicate otherPredicate = (EventNameContainsKeywordPredicate) other;
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

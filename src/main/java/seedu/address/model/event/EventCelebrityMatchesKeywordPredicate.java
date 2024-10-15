package seedu.address.model.event;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Event}'s {@code Celebrity} matches the keyword given.
 */
public class EventCelebrityMatchesKeywordPredicate implements Predicate<Event> {
    private final String keyword;

    public EventCelebrityMatchesKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Event event) {
        return keyword.equalsIgnoreCase(event.getCelebrity().getName().toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCelebrityMatchesKeywordPredicate)) {
            return false;
        }

        EventCelebrityMatchesKeywordPredicate otherEventCelebrityContainsKeywordPredicate =
                (EventCelebrityMatchesKeywordPredicate) other;
        return keyword.equals(otherEventCelebrityContainsKeywordPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}

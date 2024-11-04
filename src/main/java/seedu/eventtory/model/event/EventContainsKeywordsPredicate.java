package seedu.eventtory.model.event;

import java.util.List;
import java.util.function.Predicate;

import seedu.eventtory.commons.util.StringUtil;
import seedu.eventtory.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Event}'s {@code Name}, {@code Date} or {@code Tag} matches any of the keywords given.
 */
public class EventContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public EventContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        return keywords.stream().anyMatch(keyword ->
                StringUtil.containsPartialWordIgnoreCase(event.getName().fullName, keyword)
                || StringUtil.containsPartialWordIgnoreCase(event.getDate().toString(), keyword)
                || StringUtil.containsPartialWordIgnoreCase(event.getTags().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventContainsKeywordsPredicate)) {
            return false;
        }

        EventContainsKeywordsPredicate otherEventContainsKeywordsPredicate =
            (EventContainsKeywordsPredicate) other;
        return keywords.equals(otherEventContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

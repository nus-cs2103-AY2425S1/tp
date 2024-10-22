package seedu.address.model.event;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that an {@link Event}'s {@link EventName} matches any of the keywords given.
 */
public class EventContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public EventContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        return keywords.stream()
                .anyMatch(keyword -> (StringUtil.containsSubstringIgnoreCase(event.getName().toString(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventContainsKeywordsPredicate otherEventContainsKeywordsPredicate)) {
            return false;
        }

        return keywords.equals(otherEventContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

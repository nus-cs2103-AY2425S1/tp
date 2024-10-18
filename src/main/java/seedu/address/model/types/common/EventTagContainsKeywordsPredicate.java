package seedu.address.model.types.common;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.types.event.Event;

/**
 * Tests that a {@code Event}'s {@code Tag Name} matches any of the keywords given.
 */
public class EventTagContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public EventTagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        return keywords.stream()
                .anyMatch(keyword -> event.getTags().stream()
                        .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.getTagName(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventTagContainsKeywordsPredicate)) {
            return false;
        }

        EventTagContainsKeywordsPredicate otherTagContainsKeywordsPredicate =
                (EventTagContainsKeywordsPredicate) other;
        return keywords.equals(otherTagContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

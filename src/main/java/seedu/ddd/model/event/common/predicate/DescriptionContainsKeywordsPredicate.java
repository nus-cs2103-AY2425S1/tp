package seedu.ddd.model.event.common.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.ddd.commons.util.StringUtil;
import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.event.common.Event;

/**
 * Tests that a {@code Event}'s {@code Description} matches any of the keywords given.
 */
public class DescriptionContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public DescriptionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(event.getDescription().description, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DescriptionContainsKeywordsPredicate)) {
            return false;
        }

        DescriptionContainsKeywordsPredicate otherDescriptionContainsKeywordsPredicate =
                (DescriptionContainsKeywordsPredicate) other;
        return keywords.equals(otherDescriptionContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}



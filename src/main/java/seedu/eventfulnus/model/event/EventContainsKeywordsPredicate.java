package seedu.eventfulnus.model.event;

import java.util.List;
import java.util.function.Predicate;

import seedu.eventfulnus.commons.util.StringUtil;
import seedu.eventfulnus.commons.util.ToStringBuilder;
import seedu.eventfulnus.model.person.PersonContainsKeywordsPredicate;

/**
 * Tests that an {@link Event}'s {@link EventName} matches any of the keywords given.
 */
public class EventContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;
    private final PersonContainsKeywordsPredicate personContainsKeywordsPredicate;

    /**
     * Constructs an {@code EventContainsKeywordsPredicate} with the given {@code keywords}.
     */
    public EventContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.personContainsKeywordsPredicate = new PersonContainsKeywordsPredicate(keywords);
    }

    @Override
    public boolean test(Event event) {
        return keywords.stream()
                .anyMatch(keyword -> (StringUtil.containsSubstringIgnoreCase(event.getName().toString(), keyword))
                        || (StringUtil.containsSubstringIgnoreCase(event.getSport().toString(), keyword))
                        || (StringUtil.containsSubstringIgnoreCase(event.getFirstTeam().toString(), keyword))
                        || (StringUtil.containsSubstringIgnoreCase(event.getSecondTeam().toString(), keyword))
                        || (StringUtil.containsSubstringIgnoreCase(event.getVenue().toString(), keyword))
                        || (StringUtil.containsSubstringIgnoreCase(event.getDateTime().toString(), keyword))
                        || event.getParticipants().stream()
                            .anyMatch(personContainsKeywordsPredicate));
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

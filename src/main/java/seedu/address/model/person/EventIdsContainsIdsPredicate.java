package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

public class EventIdsContainsIdsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public EventIdsContainsIdsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        Set<Integer> eventIds = person.getEventIds();
        return keywords.stream()
                .anyMatch(keyword -> eventIds.stream().anyMatch(eventId ->
                        eventId.equals(Integer.valueOf(keyword))));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventIdsContainsIdsPredicate)) {
            return false;
        }

        EventIdsContainsIdsPredicate otherEventIdsContainsIdsPredicate =
                (EventIdsContainsIdsPredicate) other;
        return keywords.equals(otherEventIdsContainsIdsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

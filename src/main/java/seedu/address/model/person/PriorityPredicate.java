package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Address} matches any of the keywords given.
 */
public class PriorityPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PriorityPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream().map(String::toUpperCase)
                .anyMatch(keyword -> keyword.equals(person.getPriority().name()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PriorityPredicate)) {
            return false;
        }

        PriorityPredicate otherPriorityPredicate = (PriorityPredicate) other;
        return keywords.equals(otherPriorityPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

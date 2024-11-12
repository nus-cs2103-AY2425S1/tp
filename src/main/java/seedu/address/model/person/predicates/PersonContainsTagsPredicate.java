package seedu.address.model.person.predicates;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Tag} contains all the given tags.
 */
public class PersonContainsTagsPredicate implements Predicate<Person> {
    private final Set<Tag> tags;

    public PersonContainsTagsPredicate(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().containsAll(tags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonContainsTagsPredicate)) {
            return false;
        }

        PersonContainsTagsPredicate otherPersonContainsTagsPredicate = (PersonContainsTagsPredicate) other;
        return tags.equals(otherPersonContainsTagsPredicate.tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tags", tags).toString();
    }
}

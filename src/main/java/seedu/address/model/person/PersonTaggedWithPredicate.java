package seedu.address.model.person;

import java.util.function.Predicate;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person} is tagged with all tags in {@code Set<Tag>}
 */
public class PersonTaggedWithPredicate implements Predicate<Person> {
    private final Set<Tag> tagSet;

    public PersonTaggedWithPredicate(Set<Tag> tagSet) {
        this.tagSet = tagSet;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().containsAll(tagSet);
    }
}

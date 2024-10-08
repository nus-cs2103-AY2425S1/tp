package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person} is tagged with {@code Tag}
 */
public class PersonTaggedWithPredicate implements Predicate<Person> {
    private final Tag tag;

    public PersonTaggedWithPredicate(Tag tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().stream().anyMatch(t -> t.equals(tag));
    }
}

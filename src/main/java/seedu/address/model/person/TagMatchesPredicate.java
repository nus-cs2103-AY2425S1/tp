package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Tag} matches the specified keyword.
 */
public class TagMatchesPredicate implements Predicate<Person> {
    private final String tag;

    public TagMatchesPredicate(String tag) {
        this.tag = tag.toLowerCase();
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().stream()
                .anyMatch(t -> t.tagName.toLowerCase().contains(tag));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TagMatchesPredicate
                && tag.equals(((TagMatchesPredicate) other).tag));
    }
}

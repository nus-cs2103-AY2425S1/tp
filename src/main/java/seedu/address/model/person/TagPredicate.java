package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Tag} matches the given keyword.
 */
public class TagPredicate implements Predicate<Person> {
    private final String keyword;

    public TagPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return person.getTag().tagCode.contains(keyword.toUpperCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TagPredicate)) {
            return false;
        }
        TagPredicate otherPredicate = (TagPredicate) other;
        return keyword.equals(otherPredicate.keyword);
    }
}


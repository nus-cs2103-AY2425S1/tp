package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person} is tagged with the corresponding tag.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final Tag tag;

    public TagContainsKeywordsPredicate(Tag tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(Person person) {
        for (Tag personTag: person.getTags()) {
            if (personTag.equals(this.tag)) {
                return true;
            }
        }
        return false;
    }

    public Tag getTag() {
        return this.tag;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagContainsKeywordsPredicate)) {
            return false;
        }

        TagContainsKeywordsPredicate otherTagContainsKeywordsPredicate = (TagContainsKeywordsPredicate) other;
        return tag.equals(otherTagContainsKeywordsPredicate.tag);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tag", tag).toString();
    }
}

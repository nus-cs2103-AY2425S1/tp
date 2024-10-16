package seedu.address.model.person;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

import java.util.function.Predicate;

public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final String tag;

    public TagContainsKeywordsPredicate(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(Person person) {
        for (Tag personTag: person.getTags()) {
            if (personTag.getTagName().equals(this.tag)) {
                return true;
            }
        }
        return false;
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

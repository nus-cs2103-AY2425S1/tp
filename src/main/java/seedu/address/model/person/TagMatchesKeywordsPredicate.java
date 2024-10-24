package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code tag} strictly matches the keywords given.
 */
public class TagMatchesKeywordsPredicate implements Predicate<Person> {
    private final String keywords;

    public TagMatchesKeywordsPredicate(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().contains(new Tag(keywords));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagMatchesKeywordsPredicate)) {
            return false;
        }

        TagMatchesKeywordsPredicate otherTagMatchesKeywordsPredicate = (TagMatchesKeywordsPredicate) other;
        return keywords.equals(otherTagMatchesKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }

}

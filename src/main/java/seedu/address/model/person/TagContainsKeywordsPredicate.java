package seedu.address.model.person;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Tags} contain any of the specified keyword tags.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final Set<Tag> keyTags;

    public TagContainsKeywordsPredicate(Set<Tag> keyTags) {
        this.keyTags = keyTags;
    }

    @Override
    public boolean test(Person person) {
        return keyTags.stream().anyMatch(
                keyTag -> person.getTagNames().stream().anyMatch(
                        tag -> StringUtil.containsCharactersInWordIgnoreCase(tag, keyTag.tagName)));
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
        return keyTags.equals(otherTagContainsKeywordsPredicate.keyTags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyTags", keyTags).toString();
    }
}

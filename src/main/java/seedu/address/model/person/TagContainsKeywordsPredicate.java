package seedu.address.model.person;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the tags given.
 */
// Solution structure inspired by ChatGPT
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final Set<Tag> tagsToMatch;

    public TagContainsKeywordsPredicate(Set<Tag> tagsToMatch) {
        this.tagsToMatch = tagsToMatch;
    }

    @Override
    public boolean test(Person person) {
        //@@author tayxuenye-reused
        // Written by ChatGPT
        return person.getTags().stream()
                .anyMatch(personTag ->
                        tagsToMatch.stream().anyMatch(tag -> tag.tagName.equalsIgnoreCase(personTag.tagName)));
        //@@author
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
        return tagsToMatch.equals(otherTagContainsKeywordsPredicate.tagsToMatch);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tagsToMatch", tagsToMatch).toString();
    }
}

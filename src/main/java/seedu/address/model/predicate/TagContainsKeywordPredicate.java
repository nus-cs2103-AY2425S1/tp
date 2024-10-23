package seedu.address.model.predicate;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Tag}s contain any of the specified keywords.
 */
public class TagContainsKeywordPredicate implements Predicate<Person> {
    private final String keyword;

    public TagContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        Set<Tag> personTags = person.getTags(); // Get the person's tags

        // Check if any keyword matches any tag's name (case-insensitive)
        return personTags.stream()
                        .anyMatch(tag -> StringUtil.isWordInSentenceIgnoreCase(tag.tagName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagContainsKeywordPredicate)) {
            return false;
        }

        TagContainsKeywordPredicate otherPredicate = (TagContainsKeywordPredicate) other;
        return this.keyword.equals(otherPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}


package seedu.address.model.person;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Tags} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        Set<Tag> tags = person.getTags();
        return tags.stream().anyMatch(tag ->
            keywords.stream().anyMatch(keyword ->
                StringUtil.containsSubstringIgnoreCase(tag.tagName, keyword))
        );
    }

    public List<String> getTagKeywords() {
        return Collections.unmodifiableList(keywords);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof TagContainsKeywordsPredicate)) {
            return false;
        }

        TagContainsKeywordsPredicate otherPredicate =
            (TagContainsKeywordsPredicate) other;
        return this.keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

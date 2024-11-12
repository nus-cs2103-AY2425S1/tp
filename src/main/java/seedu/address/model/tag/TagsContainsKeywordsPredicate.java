package seedu.address.model.tag;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.employee.Employee;

/**
 * Tests that a {@code Employee}'s {@code Tags} matches any of the keywords given.
 */
public class TagsContainsKeywordsPredicate implements Predicate<Employee> {
    private final Set<String> keywords;

    /**
     * Constructs a {@code TagsContainsKeywordsPredicate}.
     *
     * @param keywords A valid list of tags.
     */
    public TagsContainsKeywordsPredicate(List<String> keywords) {
        requireAllNonNull(keywords);
        this.keywords = new HashSet<String>(keywords);
    }

    @Override
    public boolean test(Employee employee) {
        // Null objects should not have been added to addressbook
        assert employee != null;

        // Tag matches any of the keywords given
        return keywords.stream()
                .anyMatch(keyword -> employee.getTags().stream()
                .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagsContainsKeywordsPredicate)) {
            return false;
        }

        TagsContainsKeywordsPredicate otherTagsContainsKeywordsPredicate =
                (TagsContainsKeywordsPredicate) other;
        return keywords.equals(otherTagsContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

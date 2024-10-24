package seedu.address.model.person.keywordspredicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate extends TraitContainsKeywordsPredicate<Tag> {

    public TagContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Tag tag) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsPartialWordIgnoreCase(tag.getTagName().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagContainsKeywordsPredicate otherTagContainsKeywordsPredicate)) {
            return false;
        }

        return keywords.equals(otherTagContainsKeywordsPredicate.keywords);
    }
}

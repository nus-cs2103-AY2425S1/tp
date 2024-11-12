package seedu.address.model.person.keywordspredicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.wedding.Wedding;

/**
 * Tests that a {@code Person}'s {@code Wedding} matches any of the keywords given.
 */
public class WeddingContainsKeywordsPredicate extends TraitContainsKeywordsPredicate<Wedding> {

    public WeddingContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Wedding wedding) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsPartialWordIgnoreCase(wedding.getWeddingName().toString(), keyword));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof WeddingContainsKeywordsPredicate otherWeddingContainsKeywordsPredicate)) {
            return false;
        }

        return keywords.equals(otherWeddingContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

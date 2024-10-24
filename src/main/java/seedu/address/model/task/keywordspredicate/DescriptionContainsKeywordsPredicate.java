package seedu.address.model.task.keywordspredicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.keywordspredicate.TraitContainsKeywordsPredicate;
import seedu.address.model.task.Task;

/**
 * Tests that a {@code Task}'s {@code Description} matches any of the keywords given.
 */
public class DescriptionContainsKeywordsPredicate extends TraitContainsKeywordsPredicate<Task> {

    public DescriptionContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsPartialWordIgnoreCase(task.getDescription(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DescriptionContainsKeywordsPredicate otherDescriptionContainsKeywordsPredicate)) {
            return false;
        }

        return keywords.equals(otherDescriptionContainsKeywordsPredicate.keywords);
    }
}

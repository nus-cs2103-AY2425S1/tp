package seedu.address.model.person.keywordspredicate;

import java.util.List;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.Task;

/**
 * Tests that a {@code Person}'s {@code Task} matches any of the keywords given.
 */
public class TaskContainsKeywordsPredicate extends TraitContainsKeywordsPredicate<Task> {
    public TaskContainsKeywordsPredicate(List<String> keywords) {
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
        if (!(other instanceof TaskContainsKeywordsPredicate otherTaskContainsKeywordsPredicate)) {
            return false;
        }

        return keywords.equals(otherTaskContainsKeywordsPredicate.keywords);
    }
}

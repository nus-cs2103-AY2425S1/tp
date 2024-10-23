package seedu.address.model.task;

import static seedu.address.model.UserPrefs.MATCH_RATIO;

import java.util.List;
import java.util.function.Predicate;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Task}'s {@code TaskName} matches any of the keywords given.
 */
public class TaskNameContainsKeywordsPredicate implements Predicate<Task> {

    private final List<String> keywords;

    public TaskNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream().anyMatch(keyword ->
            FuzzySearch.tokenSetPartialRatio(task.getTaskName().getTaskName().toLowerCase(), keyword) > MATCH_RATIO);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskNameContainsKeywordsPredicate)) {
            return false;
        }

        TaskNameContainsKeywordsPredicate otherTask = (TaskNameContainsKeywordsPredicate) other;
        return keywords.equals(otherTask.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("keywords", keywords)
            .toString();
    }

}

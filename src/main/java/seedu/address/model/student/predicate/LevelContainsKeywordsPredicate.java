package seedu.address.model.student.predicate;

import java.util.List;

import seedu.address.model.student.Student;

/**
 * Tests that a {@code Student}'s {@code Level} matches any of the keywords given.
 */
public class LevelContainsKeywordsPredicate extends ContainsKeywordsPredicate {
    public LevelContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Student student) {
        return keywords.stream()
                .anyMatch(keyword -> student.getLevel().levelName.equals(keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LevelContainsKeywordsPredicate)) {
            return false;
        }

        LevelContainsKeywordsPredicate otherLevelContainsKeywordsPredicate = (LevelContainsKeywordsPredicate) other;
        return keywords.equals(otherLevelContainsKeywordsPredicate.keywords);
    }
}

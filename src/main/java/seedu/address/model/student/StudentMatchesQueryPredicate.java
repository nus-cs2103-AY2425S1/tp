package seedu.address.model.student;

import static seedu.address.model.UserPrefs.MATCH_RATIO;

import java.util.function.Predicate;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Student}'s fields matches any of the keywords given
 */
public class StudentMatchesQueryPredicate implements Predicate<Student> {

    private final String keyword;

    public StudentMatchesQueryPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Student student) {
        return FuzzySearch.partialRatio(student.getStudentNumber().toString().toLowerCase(), keyword) > MATCH_RATIO
            || FuzzySearch.partialRatio(student.getName().toString().toLowerCase(), keyword) > MATCH_RATIO
            || FuzzySearch.partialRatio(student.getEmail().toString().toLowerCase(), keyword) > MATCH_RATIO
            || FuzzySearch.partialRatio(student.getGroupName().toString().toLowerCase(), keyword) > MATCH_RATIO
            || student.getTags().stream().anyMatch(tag ->
            FuzzySearch.partialRatio(tag.toString().toLowerCase(), keyword) > MATCH_RATIO);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentMatchesQueryPredicate)) {
            return false;
        }

        StudentMatchesQueryPredicate otherPredicate = (StudentMatchesQueryPredicate) other;
        return keyword.equals(otherPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("keyword", keyword)
            .toString();
    }
}

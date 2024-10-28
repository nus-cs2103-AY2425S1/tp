package seedu.address.model.predicate;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.participation.Participation;

/**
 * Predicate to test if any of the {@code Person}'s tutorial subjects matches a inputted tutorial subject.
 * This tests for a case-insensitive match between the tutorial subject and the keyword.
 */
public class SubjectMatchesKeywordsPredicate implements Predicate<Participation> {
    private final String keyword;

    /**
     * Constructs a {@code SubjectMatchesKeywordsPredicate} with the given keyword.
     *
     * @param keyword The keyword to match against tutorial subjects.
     */
    public SubjectMatchesKeywordsPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Participation participation) {
        return StringUtil.areMatchingStringsIgnoreCase(participation.getTutorialSubject(), this.keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SubjectMatchesKeywordsPredicate)) {
            return false;
        }

        SubjectMatchesKeywordsPredicate otherPredicate = (SubjectMatchesKeywordsPredicate) other;
        return this.keyword.equals(otherPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}

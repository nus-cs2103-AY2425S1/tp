package seedu.address.model.predicate;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Predicate to test if any of the {@code Person}'s tutorial subjects matches a inputted tutorial subject.
 * This tests for a case-insensitive match between the tutorial subject and the keyword.
 */
public class SubjectMatchesKeywordsPredicate implements Predicate<Person> {
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
    public boolean test(Person person) {
        return person.getParticipation().stream()
                .anyMatch(eachParticipation ->
                        StringUtil.areMatchingStringsIgnoreCase(eachParticipation.getTutorialSubject(), this.keyword));
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

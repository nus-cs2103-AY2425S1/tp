package seedu.address.model.predicate;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

public class SubjectMatchesKeywordsPredicate implements Predicate<Person> {
    private final String keyword;

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

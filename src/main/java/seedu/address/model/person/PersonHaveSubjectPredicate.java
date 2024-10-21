package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code person} has the same subject as the subject to be found
 */
public class PersonHaveSubjectPredicate implements Predicate<Person> {
    private final String subjectToFind;

    public PersonHaveSubjectPredicate(String subjectToFind) {
        this.subjectToFind = subjectToFind;
    }
    @Override
    public boolean test(Person person) {
        return person.getSubjects().stream()
                .anyMatch(subject -> StringUtil.containsWordIgnoreCase(subjectToFind, subject.toString()));

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonHaveSubjectPredicate personHaveSubjectPredicate)) {
            return false;
        }

        return subjectToFind.equals(personHaveSubjectPredicate.subjectToFind);
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this).add("subject to find", subjectToFind).toString();
    }

}

package seedu.academyassist.model.person;

import java.util.function.Predicate;

import seedu.academyassist.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Subjects} contain the given subject.
 */
public class PersonTakeSubjectPredicate implements Predicate<Person> {
    private final Subject keySubject;

    public PersonTakeSubjectPredicate(Subject keySubject) {
        this.keySubject = keySubject;
    }

    @Override
    public boolean test(Person person) {
        return person.getSubjects().contains(keySubject);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        PersonTakeSubjectPredicate otherPersonTakeSubjectPredicate = (PersonTakeSubjectPredicate) other;
        return keySubject.equals(otherPersonTakeSubjectPredicate.keySubject);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keySubject", keySubject).toString();
    }
}

package seedu.academyassist.model.person;

import java.util.function.Predicate;

import seedu.academyassist.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code YearGroup} matches the given year group.
 */
public class PersonInYearPredicate implements Predicate<Person> {
    private final YearGroup keyYearGroup;

    public PersonInYearPredicate(YearGroup keyYearGroup) {
        this.keyYearGroup = keyYearGroup;
    }

    @Override
    public boolean test(Person person) {
        return person.getYearGroup().equals(keyYearGroup);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonInYearPredicate)) {
            return false;
        }

        PersonInYearPredicate otherPersonInYearPredicate = (PersonInYearPredicate) other;
        return keyYearGroup.equals(otherPersonInYearPredicate.keyYearGroup);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyYearGroup", keyYearGroup).toString();
    }
}

package seedu.address.model.patient;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s is a patient and {@code Name} matches any of the keywords given.
 */
public class FindPatientPredicate implements Predicate<Person> {
    private final String name;

    public FindPatientPredicate(String name) {
        this.name = name;
    }

    @Override
    public boolean test(Person person) {
        if (!(person instanceof Patient)) {
            return false;
        }
        return StringUtil.containsNameIgnoreCase(person.getName().fullName, name);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindPatientPredicate)) {
            return false;
        }

        FindPatientPredicate findPatientPredicate = (FindPatientPredicate) other;
        return name.equals(findPatientPredicate.name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", name).toString();
    }
}

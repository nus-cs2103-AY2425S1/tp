package seedu.address.model.doctor;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person} is an instanceof Doctor and contains any of the keywords given.
 */
public class FindDoctorPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public FindDoctorPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (!(person instanceof Doctor)) {
            return false;
        }
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindDoctorPredicate)) {
            return false;
        }

        FindDoctorPredicate findDoctorPredicate = (FindDoctorPredicate) other;
        return keywords.equals(findDoctorPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

package seedu.address.model.patient;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

public class FindPatientPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public FindPatientPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (!(person instanceof Patient)) {
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

        if (!(other instanceof FindPatientPredicate)) {
            return false;
        }

        FindPatientPredicate findPatientPredicate = (FindPatientPredicate) other;
        return keywords.equals(findPatientPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

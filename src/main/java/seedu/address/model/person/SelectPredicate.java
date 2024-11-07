package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person} matches any of the selected persons given.
 */
public class SelectPredicate implements Predicate<Person> {
    private final List<Person> persons;
    public SelectPredicate(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public boolean test(Person person) {
        return persons.stream()
                .anyMatch(phone -> phone.equals(person));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instance of handles nulls
        if (!(other instanceof SelectPredicate)) {
            return false;
        }

        SelectPredicate otherSelectPredicate = (SelectPredicate) other;
        return persons.equals(otherSelectPredicate.persons);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("Persons", persons).toString();
    }


}

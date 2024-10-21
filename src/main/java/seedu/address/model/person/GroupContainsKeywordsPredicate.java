package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Group;
import seedu.address.model.person.GroupList;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.GroupNotFoundException;

public class GroupContainsKeywordsPredicate implements Predicate<Person> {
    private final Group group;

    public GroupContainsKeywordsPredicate(Group group) {
        this.group = group;
    }

    @Override
    public boolean test(Person person) {
        return group.containsExact(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupContainsKeywordsPredicate // instanceof handles nulls
                && group.equals(((GroupContainsKeywordsPredicate) other).group)); // Compare based on Group object
    }
}

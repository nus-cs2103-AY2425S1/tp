package seedu.address.model;

import seedu.address.model.person.Person;

public interface AddressBookComparable<T> {
    boolean isSame(T other);
}

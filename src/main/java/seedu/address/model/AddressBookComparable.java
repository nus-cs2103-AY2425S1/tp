package seedu.address.model;

public interface AddressBookComparable<T> {
    boolean isSame(T other);
}

package seedu.hireme.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import seedu.hireme.commons.util.CollectionUtil;
import seedu.hireme.commons.util.ToStringBuilder;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.model.internshipapplication.Status;
import seedu.hireme.model.internshipapplication.UniqueList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSame comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueList items;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        items = new UniqueList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the items in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the list with {@code internship applications}.
     * {@code internship applications} must not contain duplicate items.
     */
    public void setItems(List<InternshipApplication> items) {
        CollectionUtil.requireAllNonNull(items);
        this.items.setItems(items);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setItems(newData.getList());
    }

    /**
     * Returns true if a item with the same identity as {@code item} exists in the address book.
     */
    public boolean hasItem(InternshipApplication item) {
        requireNonNull(item);
        return items.contains(item);
    }

    /**
     * Adds an item to the address book.
     * The item must not already exist in the address book.
     */
    public void addItem(InternshipApplication item) {
        requireNonNull(item);
        items.add(item);
    }

    /**
     * Replaces the given item {@code target} in the list with {@code edited}.
     * {@code target} must exist in the address book.
     * The item identity of {@code edited} must not be the same as another existing item in the address book.
     */
    public void setItem(InternshipApplication target, InternshipApplication edited) {
        requireNonNull(edited);
        items.setItem(target, edited);
    }

    /**
     * Removes {@code target} from this {@code AddressBook}.
     * {@code target} must exist in the address book.
     */
    public void removeItem(InternshipApplication target) {
        requireNonNull(target);
        items.remove(target);
    }

    /**
     * Sorts the items in the address book according to the {@code comparator}.
     */
    public void sortItems(Comparator<InternshipApplication> comparator) {
        requireNonNull(comparator);
        items.sortItems(comparator);
    }

    /**
     * Provides status insights of items in the list
     */
    public Map<Status, Integer> getChartData() {
        Map<Status, Integer> map = new HashMap<>();
        for (Status status : Status.values()) {
            int count = items.countItems(i -> i.getStatus().equals(status));
            map.put(status, count);
        }

        return map;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("items", items)
                .toString();
    }

    @Override
    public ObservableList<InternshipApplication> getList() {
        return items.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook otherAddressBook)) {
            return false;
        }

        return items.equals(otherAddressBook.items);
    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }
}

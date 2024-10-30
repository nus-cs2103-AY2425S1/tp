package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateVendorException;
import seedu.address.model.person.exceptions.VendorNotFoundException;

/**
 * A list of vendors that enforces uniqueness between its elements and does not allow nulls.
 * A vendor is considered unique by comparing using {@code Vendor#isSameVendor(Vendor)}. As such, adding and updating of
 * vendors uses Vendor#isSameVendor(Vendor) for equality to ensure that the vendor being added or updated is
 * unique in terms of identity in the UniqueVendorList. However, the removal of a vendor uses Vendor#equals(Object)
 * to ensure that the vendor with exactly the same fields will be removed.
 * Supports a minimal set of list operations.
 *
 * @see Vendor#isSameVendor(Vendor)
 */
public class UniqueVendorList implements Iterable<Vendor> {

    private final ObservableList<Vendor> internalList = FXCollections.observableArrayList();
    private final ObservableList<Vendor> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the vendor list contains an equivalent person as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Returns the vendor if the vendor list contains that person as a vendor.
     */
    public Vendor checkVendor(Person toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().filter(toCheck::isSamePerson).findFirst().orElse(null);
    }

    /**
     * Adds a person as a vendor to the vendor list.
     * The person must not already exist in the vendor list.
     */
    public void add(Person toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateVendorException();
        }
        Vendor vendor = new Vendor(toAdd.getName(), toAdd.getPhone(), toAdd.getEmail(),
                toAdd.getAddress(), toAdd.getTags(), toAdd.getWeddings(), toAdd.getTasks());
        internalList.add(vendor);
    }

    /**
     * Replaces the vendor {@code target} in the list with {@code editedVendor}.
     * {@code target} must exist in the list.
     * The identity of {@code editedVendor} must not be the same as another existing vendor in the list.
     */
    public void setVendor(Vendor target, Vendor editedVendor) {
        requireAllNonNull(target, editedVendor);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new VendorNotFoundException();
        }

        if (!target.isSameVendor(editedVendor) && contains(editedVendor)) {
            throw new DuplicateVendorException();
        }

        internalList.set(index, editedVendor);
    }

    /**
     * Removes the equivalent vendor from the list.
     * The vendor must exist in the list.
     */
    public void remove(Person toRemove) {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new VendorNotFoundException();
        }
        internalList.remove(checkVendor(toRemove));
    }

    public void setVendors(UniqueVendorList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code vendors}.
     * {@code vendors} must not contain duplicate vendors.
     */
    public void setVendors(List<Vendor> vendors) {
        requireAllNonNull(vendors);
        if (!vendorsAreUnique(vendors)) {
            throw new DuplicateVendorException();
        }

        internalList.setAll(vendors);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Vendor> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Vendor> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueVendorList)) {
            return false;
        }

        UniqueVendorList otherUniqueVendorList = (UniqueVendorList) other;
        return internalList.equals(otherUniqueVendorList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code vendors} contains only unique vendors.
     */
    private boolean vendorsAreUnique(List<Vendor> vendors) {
        for (int i = 0; i < vendors.size() - 1; i++) {
            for (int j = i + 1; j < vendors.size(); j++) {
                if (vendors.get(i).isSameVendor(vendors.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

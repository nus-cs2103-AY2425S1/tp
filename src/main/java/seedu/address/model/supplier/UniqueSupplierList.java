package seedu.address.model.supplier;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.supplier.exceptions.DuplicateSupplierException;
import seedu.address.model.supplier.exceptions.SupplierNotFoundException;

/**
 * A list of suppliers that enforces uniqueness between its elements and does not allow nulls.
 * A supplier is considered unique by comparing using {@code Supplier#isSameSupplier(Supplier)}.
 * As such, adding and updating of suppliers uses Supplier#isSameSupplier(Supplier) for equality so as to ensure that
 * the supplier being added or updated is unique in terms of identity in the UniqueSupplierList.
 * However, the removal of a supplier uses Supplier#equals(Object) so
 * as to ensure that the supplier with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Supplier#isSameSupplier(Supplier)
 */
public class UniqueSupplierList implements Iterable<Supplier> {

    private final ObservableList<Supplier> internalList = FXCollections.observableArrayList();
    private final ObservableList<Supplier> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent supplier as the given argument.
     */
    public boolean contains(Supplier toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameSupplier);
    }

    /**
     * Adds a supplier to the list.
     * The Supplier must not already exist in the list.
     */
    public void add(Supplier toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateSupplierException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the supplier {@code target} in the list with {@code editedSupplier}.
     * {@code target} must exist in the list.
     * The supplier identity of {@code editedSupplier} must not be the same as another existing supplier in the list.
     */
    public void setSupplier(Supplier target, Supplier editedSupplier) {
        requireAllNonNull(target, editedSupplier);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new SupplierNotFoundException();
        }

        if (!target.isSameSupplier(editedSupplier) && contains(editedSupplier)) {
            throw new DuplicateSupplierException();
        }

        internalList.set(index, editedSupplier);
    }

    /**
     * Removes the equivalent supplier from the list.
     * The supplier must exist in the list.
     */
    public void remove(Supplier toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new SupplierNotFoundException();
        }
    }

    public void setSuppliers(UniqueSupplierList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Suppliers}.
     * {@code suppliers} must not contain duplicate suppliers.
     */
    public void setSuppliers(List<Supplier> suppliers) {
        requireAllNonNull(suppliers);
        if (!suppliersAreUnique(suppliers)) {
            throw new DuplicateSupplierException();
        }

        internalList.setAll(suppliers);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Supplier> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Supplier> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueSupplierList)) {
            return false;
        }

        UniqueSupplierList otherUniqueSupplierList = (UniqueSupplierList) other;
        return internalList.equals(otherUniqueSupplierList.internalList);
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
     * Returns true if {@code suppliers} contains only unique suppliers.
     */
    private boolean suppliersAreUnique(List<Supplier> suppliers) {
        for (int i = 0; i < suppliers.size() - 1; i++) {
            for (int j = i + 1; j < suppliers.size(); j++) {
                if (suppliers.get(i).isSameSupplier(suppliers.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

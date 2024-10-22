package seedu.address.model.owner;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.owner.exceptions.DuplicateOwnerException;
import seedu.address.model.owner.exceptions.OwnerNotFoundException;

/**
 * A list of owner that enforces uniqueness between its elements and does not allow nulls.
 * An owner is considered unique by comparing using {@code Owner#isSameOwner(Owner)}. As such, adding and updating of
 * owners uses Owner#isSameOwner(Owner) for equality so as to ensure that the owner being added or updated is
 * unique in terms of identity in the UniqueOwnerList. However, the removal of an owner uses Owner#equals(Object) so
 * as to ensure that the owner with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Owner#isSameOwner(Owner)
 */
public class UniqueOwnerList implements Iterable<Owner> {

    private final ObservableList<Owner> internalList = FXCollections.observableArrayList();
    private final ObservableList<Owner> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent owner as the given argument.
     */
    public boolean contains(Owner toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameOwner);
    }

    /**
     * Adds an owner to the list.
     * The owner must not already exist in the list.
     */
    public void add(Owner toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateOwnerException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the owner  {@code target} in the list with {@code editedOwner}.
     * {@code target} must exist in the list.
     * The owner identity of {@code editedOwner} must not be the same as another existing owner in the list.
     */
    public void setOwner(Owner target, Owner editedOwner) {
        requireAllNonNull(target, editedOwner);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new OwnerNotFoundException();
        }

        if (!target.isSameOwner(editedOwner) && contains(editedOwner)) {
            throw new DuplicateOwnerException();
        }

        internalList.set(index, editedOwner);
    }

    /**
     * Removes the equivalent owner from the list.
     * The owner must exist in the list.
     */
    public void remove(Owner toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new OwnerNotFoundException();
        }
    }

    public void setOwners(UniqueOwnerList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code owners}.
     * {@code owners} must not contain duplicate owners.
     */
    public void setOwners(List<Owner> owners) {
        requireAllNonNull(owners);
        if (!ownersAreUnique(owners)) {
            throw new DuplicateOwnerException();
        }

        internalList.setAll(owners);
    }

    /**
     * sorts the list of owners by
     */
    public void sort() {
        internalList.sort(Comparator
                .comparing((Owner owner) -> owner.getName().fullName.toLowerCase())); //
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Owner> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Owner> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueOwnerList)) {
            return false;
        }

        UniqueOwnerList otherUniqueOwnerList = (UniqueOwnerList) other;
        return internalList.equals(otherUniqueOwnerList.internalList);
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
     * Returns true if {@code owners} contains only unique owners.
     */
    private boolean ownersAreUnique(List<Owner> owners) {
        for (int i = 0; i < owners.size() - 1; i++) {
            for (int j = i + 1; j < owners.size(); j++) {
                if (owners.get(i).isSameOwner(owners.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

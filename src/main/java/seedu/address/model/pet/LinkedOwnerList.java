package seedu.address.model.pet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.link.Linkable;
import seedu.address.model.owner.Owner;
import seedu.address.model.owner.exceptions.DuplicateOwnerException;
import seedu.address.model.owner.exceptions.OwnerNotFoundException;

/**
 * A list of Owners that enforces uniqueness between its elements and does not allow nulls.
 * A Owner is considered unique by comparing using {@code Owner#isSameOwner(Owner)}. As such, adding and updating of
 * Owners uses Owner#isSameOwner(Owner) for equality so as to ensure that the Owner being added or updated is
 * unique in terms of identity in the UniqueOwnerList. However, the removal of a Owner uses Owner#equals(Object) so
 * as to ensure that the Owner with exactly the same fields will be removed. This list should only contain 1 owner
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Owner#isSameOwner(Owner)
 */
public class LinkedOwnerList implements Iterable<Owner> {

    private final ObservableList<Owner> internalList = FXCollections.observableArrayList();
    private final ObservableList<Owner> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Owner as the given argument.
     */
    public boolean contains(Owner toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameOwner);
    }

    /**
     * Returns the observable list
     */
    public ObservableList<Owner> getList() {
        return this.internalList;
    }

    /**
     * clears the list
     */
    public void resetList() {
        this.internalList.clear();
    }

    /**
     * Adds a Owner to the list.
     * The Owner must not already exist in the list.
     */
    public void add(Linkable target) {
        requireNonNull(target);
        Owner toAdd = (Owner) target;
        if (contains(toAdd)) {
            throw new DuplicateOwnerException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Owner {@code target} in the list with {@code editedOwner}.
     * {@code target} must exist in the list.
     * The Owner identity of {@code editedOwner} must not be the same as another existing Owner in the list.
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
     * Removes the equivalent Owner from the list.
     * The Owner must exist in the list.
     */
    public void remove(Linkable target) {
        requireNonNull(target);
        Owner toRemove = (Owner) target;
        if (!internalList.remove(toRemove)) {
            throw new OwnerNotFoundException();
        }
    }

    public void setOwners(LinkedOwnerList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Owners}.
     * {@code Owners} must not contain duplicate Owners.
     */
    public void setOwners(List<Owner> owners) {
        requireAllNonNull(owners);
        if (!ownersAreUnique(owners)) {
            throw new DuplicateOwnerException();
        }

        internalList.setAll(owners);
    }

    /**
     * sorts the list of Owners by name
     */
    public void sort() {
        internalList.sort(Comparator
                .comparing((Owner Owner) -> Owner.getName().fullName.toLowerCase())); //
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
        if (!(other instanceof LinkedOwnerList)) {
            return false;
        }

        LinkedOwnerList otherUniqueOwnerList = (LinkedOwnerList) other;
        return internalList.equals(otherUniqueOwnerList.internalList);
    }

    public String getAsField() {
        StringBuilder formattedOwner = new StringBuilder();

        formattedOwner.append("Owner(s): ");

        if (internalList.isEmpty()) {
            formattedOwner.append("Warning! This pet is not linked to any owner");
            return formattedOwner.toString();
        }

        formattedOwner.append(String.join(" | ", internalList.stream().map(o -> o.getName().toString()).toList()));

        return formattedOwner.toString();
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
     * Returns true if {@code Owners} contains only unique Owners.
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

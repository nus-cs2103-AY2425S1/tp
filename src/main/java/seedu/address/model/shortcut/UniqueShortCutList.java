package seedu.address.model.shortcut;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.shortcut.exceptions.DuplicateShortCutException;
import seedu.address.model.shortcut.exceptions.ShortCutNotFoundException;

/**
 * A list of shortcuts that enforces uniqueness between its elements and does not allow nulls.
 * A shortcut is considered unique by comparing its {@code Alias}.
 */
public class UniqueShortCutList implements Iterable<ShortCut> {

    private final ObservableList<ShortCut> internalList = FXCollections.observableArrayList();
    private final ObservableList<ShortCut> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent shortcut as the given argument.
     */
    public boolean contains(ShortCut toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(shortcut -> shortcut.equals(toCheck));
    }

    /**
     * Returns true if the list contains a shortcut with the same alias as the given argument.
     */
    public boolean containsAlias(Alias aliasToCheck) {
        requireNonNull(aliasToCheck);
        return internalList.stream().anyMatch(shortcut -> shortcut.getAlias().equals(aliasToCheck));
    }

    /**
     * Adds a shortcut to the list.
     * The shortcut must not already exist in the list.
     */
    public void add(ShortCut toAdd) {
        requireNonNull(toAdd);
        if (containsAlias(toAdd.getAlias())) {
            throw new DuplicateShortCutException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent shortcut from the list.
     * The shortcut must exist in the list.
     */
    public void remove(ShortCut toRemove) {
        requireNonNull(toRemove);
        if (!internalList.removeIf(shortcut -> shortcut.getAlias().equals(toRemove.getAlias()))) {
            throw new ShortCutNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     * {@code replacement} must not contain duplicate shortcuts.
     */
    public void setShortCuts(UniqueShortCutList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code shortcuts}.
     * {@code shortcuts} must not contain duplicate shortcuts.
     */
    public void setShortCuts(List<ShortCut> shortcuts) {
        requireAllNonNull(shortcuts);
        if (!shortcutsAreUnique(shortcuts)) {
            throw new DuplicateShortCutException();
        }

        internalList.setAll(shortcuts);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ShortCut> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<ShortCut> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueShortCutList)) {
            return false;
        }

        UniqueShortCutList otherUniqueShortCutList = (UniqueShortCutList) other;
        return internalList.equals(otherUniqueShortCutList.internalList);
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
     * Returns true if {@code shortcuts} contains only unique shortcuts.
     */
    private boolean shortcutsAreUnique(List<ShortCut> shortcuts) {
        Set<Alias> aliasSet = new HashSet<>();
        for (ShortCut shortcut : shortcuts) {
            if (!aliasSet.add(shortcut.getAlias())) {
                return false;
            }
        }
        return true;
    }
}

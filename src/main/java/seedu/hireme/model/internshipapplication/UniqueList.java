package seedu.hireme.model.internshipapplication;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.hireme.commons.util.CollectionUtil;
import seedu.hireme.model.internshipapplication.exceptions.DuplicateInternshipException;
import seedu.hireme.model.internshipapplication.exceptions.InternshipNotFoundException;

/**
 * A list that enforces uniqueness between its elements and does not allow nulls.
 * An element is considered unique by using {@code HireMeComparable#isSame(Object)}.
 * As such, adding and updating of elements uses {@code HireMeComparable#isSame(Object)}
 * for equality to ensure that the element being added or updated is unique in terms of identity in
 * the {@code UniqueList}.
 * However, the removal of an element uses {@code Object#equals(Object)} to ensure that
 * the element with exactly the same fields will be removed.
 *
 */
public class UniqueList implements Iterable<InternshipApplication> {

    private final ObservableList<InternshipApplication> internalList = FXCollections.observableArrayList();
    private final ObservableList<InternshipApplication> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an element equivalent to the given argument.
     *
     * @param toCheck The element to check for existence.
     * @return True if the list contains the element, false otherwise.
     */
    public boolean contains(InternshipApplication toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSame);
    }

    /**
     * Adds an element to the list.
     * The element must not already exist in the list.
     *
     * @param toAdd The element to add.
     * @throws DuplicateInternshipException if the element already exists in the list.
     */
    public void add(InternshipApplication toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateInternshipException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the element {@code target} in the list with {@code edited}.
     * {@code target} must exist in the list, and the {@code edited} element
     * must not be the same as another existing element in the list.
     *
     * @param target The element to replace.
     * @param edited The edited element to replace with.
     * @throws InternshipNotFoundException if {@code target} does not exist in the list.
     * @throws DuplicateInternshipException if {@code edited} is the same as another existing element in the list.
     */
    public void setItem(InternshipApplication target, InternshipApplication edited) {
        CollectionUtil.requireAllNonNull(target, edited);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new InternshipNotFoundException();
        }

        if (!target.isSame(edited) && contains(edited)) {
            throw new DuplicateInternshipException();
        }

        internalList.set(index, edited);
    }

    /**
     * Removes the equivalent element from the list.
     * The element must exist in the list.
     *
     * @param toRemove The element to remove.
     * @throws InternshipNotFoundException if the element does not exist in the list.
     */
    public void remove(InternshipApplication toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new InternshipNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     * The {@code replacement} list must not contain duplicate elements.
     *
     * @param replacement The new list to replace the existing contents.
     */
    public void setItems(UniqueList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code items}.
     * The {@code items} list must not contain duplicate elements.
     *
     * @param items The new list to replace the existing contents.
     * @throws DuplicateInternshipException if the {@code items} list contains duplicates.
     */
    public void setItems(List<InternshipApplication> items) {
        CollectionUtil.requireAllNonNull(items);
        if (!areUnique(items)) {
            throw new DuplicateInternshipException();
        }
        internalList.setAll(items);
    }

    /**
     * Sorts the contents of this list according to the comparator.
     *
     * @param comparator The sorting order of the list of items.
     */
    public void sortItems(Comparator<InternshipApplication> comparator) {
        requireNonNull(comparator);
        internalList.sort(comparator);
    }

    /**
     * Counts items in the list that satisfy the given predicate
     * @param predicate The predicate applied on the items
     */
    public int countItems(Predicate<InternshipApplication> predicate) {
        return (int) internalList.stream().filter(predicate).count();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     *
     * @return The unmodifiable list.
     */
    public ObservableList<InternshipApplication> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<InternshipApplication> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueList otherUniqueList)) {
            return false;
        }

        return internalList.equals(otherUniqueList.internalList);
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
     * Returns true if {@code items} contains only unique elements.
     *
     * @param items The list to check for uniqueness.
     * @return True if all elements are unique, false otherwise.
     */
    private boolean areUnique(List<InternshipApplication> items) {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = i + 1; j < items.size(); j++) {
                if (items.get(i).isSame(items.get(j))) {
                    return false;
                }
            }
        }

        return true;
    }
}

package seedu.address.model.property;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A list of properties that enforces uniqueness between its elements and does not allow nulls.
 * A property is considered unique by comparing using {@code Property#isSameProperty(Property)}.
 * As such, adding and updating of properties uses {@code Property#isSameProperty(Property)}
 * for equality to ensure uniqueness.
 *
 * Supports a minimal set of list operations.
 *
 * @see Property#isSameProperty(Property)
 */
public class UniquePropertiesList implements Iterable<Property> {

    private final ObservableList<Property> internalList = FXCollections.observableArrayList();
    private final ObservableList<Property> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Property> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns an iterator over the properties in the list.
     *
     * @return An iterator over the properties.
     */
    @Override
    public Iterator<Property> iterator() {
        return internalList.iterator();
    }

    /**
     * Replaces the contents of this list with {@code properties}.
     * {@code properties} must not contain duplicate properties.
     */
    public void setProperties(List<Property> properties) {
        requireAllNonNull(properties);
        if (!propertiesAreUnique(properties)) {
            throw new DuplicatePersonException();
        }
        internalList.setAll(properties);
    }

    /**
     * Adds a property to the list.
     * The property must not already exist in the list.
     */
    public void add(Property toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns true if the list contains an equivalent property as the given argument.
     */
    public boolean contains(Property toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameProperty);
    }

    /**
     * Returns true if {@code properties} contains only unique properties.
     */
    private boolean propertiesAreUnique(List<Property> properties) {
        for (int i = 0; i < properties.size() - 1; i++) {
            for (int j = i + 1; j < properties.size(); j++) {
                if (properties.get(i).isSameProperty(properties.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

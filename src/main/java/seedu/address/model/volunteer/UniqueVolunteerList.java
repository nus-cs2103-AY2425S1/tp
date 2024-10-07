package seedu.address.model.volunteer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.model.volunteer.exceptions.DuplicateVolunteerException;
import seedu.address.model.volunteer.exceptions.VolunteerNotFoundException;

/**
 * A list of volunteer that enforces uniqueness between its elements and does not allow nulls.
 * A volunteer is considered unique by comparing using {@code Volunteer#isSameVolunteer(Volunteer)}. As such, adding and updating of
 * volunteers uses Volunteer#isSameVolunteer(Volunteer) for equality so as to ensure that the volunteer being added or updated is
 * unique in terms of identity in the UniqueVolunteerList. However, the removal of a volunteer uses Volunteer#equals(Object) so
 * as to ensure that the volunteer with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Volunteer#isSameVolunteer(Volunteer)
 */
public class UniqueVolunteerList implements Iterable<Volunteer> {

    private final ObservableList<Volunteer> internalList = FXCollections.observableArrayList();
    private final ObservableList<Volunteer> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent volunteer as the given argument.
     */
    public boolean contains(Volunteer toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameVolunteer);
    }

    /**
     * Adds a volunteer to the list.
     * The volunteer must not already exist in the list.
     */
    public void add(Volunteer toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateVolunteerException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the volunteer {@code target} in the list with {@code editedVolunteer}.
     * {@code target} must exist in the list.
     * The volunteer identity of {@code editedVolunteer} must not be the same as another existing volunteer in the list.
     */
    public void setVolunteer(Volunteer target, Volunteer editedVolunteer) {
        requireAllNonNull(target, editedVolunteer);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new VolunteerNotFoundException();
        }

        if (!target.isSameVolunteer(editedVolunteer) && contains(editedVolunteer)) {
            throw new DuplicateVolunteerException();
        }

        internalList.set(index, editedVolunteer);
    }

    /**
     * Removes the equivalent volunteer from the list.
     * The volunteer must exist in the list.
     */
    public void remove(Volunteer toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new VolunteerNotFoundException();
        }
    }

    public void setVolunteers(UniqueVolunteerList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code volunteers}.
     * {@code volunteers} must not contain duplicate volunteers.
     */
    public void setVolunteers(List<Volunteer> volunteers) {
        requireAllNonNull(volunteers);
        if (!volunteersAreUnique(volunteers)) {
            throw new DuplicateVolunteerException();
        }

        internalList.setAll(volunteers);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Volunteer> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Volunteer> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueVolunteerList)) {
            return false;
        }

        UniqueVolunteerList otherUniqueVolunteerList = (UniqueVolunteerList) other;
        return internalList.equals(otherUniqueVolunteerList.internalList);
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
     * Returns true if {@code volunteers} contains only unique volunteers.
     */
    private boolean volunteersAreUnique(List<Volunteer> volunteers) {
        for (int i = 0; i < volunteers.size() - 1; i++) {
            for (int j = i + 1; j < volunteers.size(); j++) {
                if (volunteers.get(i).isSameVolunteer(volunteers.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
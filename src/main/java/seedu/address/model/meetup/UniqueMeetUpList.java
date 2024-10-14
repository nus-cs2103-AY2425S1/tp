package seedu.address.model.meetup;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;

/**
 * TODO JAVADOC + ENTIRE DOCUMENTATION
 * A list of meet ups that enforces uniqueness between its elements and does not allow nulls.
 * A meet ups is considered unique by comparing using {@code Person#isSamePerson(Person)}. As such, adding and updating
 * of persons uses Person#isSamePerson(Person) for equality so as to ensure that the person being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a person uses Person#equals(Object) so
 * as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Person#isSamePerson(Person)
 */
public class UniqueMeetUpList implements Iterable<MeetUp> {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ObservableList<MeetUp> internalList = FXCollections.observableArrayList();
    private final ObservableList<MeetUp> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public boolean contains(MeetUp toCheck) {
        // TODO
        return true;
        // requireNonNull(toCheck);
        // return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public void add(MeetUp toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            // TODO
            // throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public void setMeetUp(MeetUp target, MeetUp editedPerson) {
        requireAllNonNull(target, editedPerson);

        int index = internalList.indexOf(target);
        if (index == -1) {
            // TODO
            return;
            // throw new PersonNotFoundException();
        }

        // if (!target.isSamePerson(editedPerson) && contains(editedPerson)) {
        //     throw new DuplicatePersonException();
        // }

        internalList.set(index, editedPerson);
    }

    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public void remove(MeetUp toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            // TODO
            //throw new PersonNotFoundException();
            return;
        }
    }

    public void setMeetUps(UniqueMeetUpList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setMeetUps(List<MeetUp> meetUps) {
        requireAllNonNull(meetUps);
        // if (!meetUpsAreUnique(meetUps)) {
        //     throw new DuplicatePersonException();
        // }

        internalList.setAll(meetUps);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<MeetUp> asUnmodifiableObservableList() {
        logger.info("the unmodifible list is here" + internalUnmodifiableList);
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<MeetUp> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueMeetUpList)) {
            return false;
        }

        UniqueMeetUpList otherUniquePersonList = (UniqueMeetUpList) other;
        return internalList.equals(otherUniquePersonList.internalList);
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
     * Returns true if {@code persons} contains only unique persons.
     */
    private boolean personsAreUnique(List<MeetUp> meetUps) {
        // TODO
        // for (int i = 0; i < meetUps.size() - 1; i++) {
        //     for (int j = i + 1; j < meetUps.size(); j++) {
        //         if (meetUps.get(i).isSamePerson(meetUps.get(j))) {
        //             return false;
        //         }
        //     }
        // }
        // return true;
        return true;
    }
}

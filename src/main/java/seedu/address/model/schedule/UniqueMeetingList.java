package seedu.address.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.ConflictMeetingException;
import seedu.address.model.person.exceptions.DuplicateMeetingException;
import seedu.address.model.person.exceptions.MeetingNotFoundException;

/**
 * A list of meetings that enforces uniqueness between its elements and does not allow nulls.
 * A meeting is considered unique by comparing using {@code Meeting#isSameMeeting(Meeting)}.
 * As such, adding and updating of meetings uses Meeting#isSameMeeting(Meeting) for equality to ensure that
 * the meeting being added or updated is unique in terms of identity in the UniqueMeetingList.
 * However, the removal of a meeting uses Meeting#equals(Object) to ensure that the meeting with exactly the same
 * fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Meeting#isSameMeeting(Meeting)
 */
public class UniqueMeetingList implements Iterable<Meeting> {
    private final ObservableList<Meeting> internalList = FXCollections.observableArrayList();
    private final ObservableList<Meeting> internalUnmodifiableList = FXCollections
            .unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent meeting as the given argument.
     *
     * @param toCheck The meeting to check for.
     * @return True if the meeting exists in the list, false otherwise.
     */
    public boolean contains(Meeting toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMeeting);
    }

    /**
     * Returns true if the list contains a meeting that conflicts with the given meeting.
     *
     * @param toCheck The meeting to check for conflicts.
     * @return True if a conflict exists, false otherwise.
     */
    public boolean conflictMeeting(Meeting toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::hasConflictMeeting);
    }

    /**
     * Adds a meeting to the list.
     * The meeting must not already exist in the list and must not conflict with an existing meeting.
     *
     * @param toAdd The meeting to add.
     * @throws DuplicateMeetingException If the meeting already exists in the list.
     * @throws ConflictMeetingException  If the meeting conflicts with another meeting in the list.
     */
    public void add(Meeting toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMeetingException();
        }
        if (conflictMeeting(toAdd)) {
            throw new ConflictMeetingException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the meeting {@code target} in the list with {@code editedMeeting}.
     * The {@code target} must exist in the list.
     * The identity of {@code editedMeeting} must not be the same as another existing meeting in the list.
     *
     * @param target        The meeting to replace.
     * @param editedMeeting The new meeting to set.
     * @throws MeetingNotFoundException  If the target meeting is not found in the list.
     * @throws DuplicateMeetingException If the edited meeting conflicts with an existing meeting in the list.
     */
    public void setMeeting(Meeting target, Meeting editedMeeting) {
        requireAllNonNull(target, editedMeeting);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MeetingNotFoundException();
        }

        if (!target.isSameMeeting(editedMeeting) && contains(editedMeeting)) {
            throw new DuplicateMeetingException();
        }

        internalList.set(index, editedMeeting);
    }

    /**
     * Removes the equivalent meeting from the list.
     * The meeting must exist in the list.
     *
     * @param toRemove The meeting to remove.
     * @throws MeetingNotFoundException If the meeting is not found in the list.
     */
    public void remove(Meeting toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MeetingNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code meetings}.
     * {@code meetings} must not contain duplicate meetings.
     *
     * @param meetings The new list of meetings to set.
     * @throws DuplicateMeetingException If there are duplicate meetings in the provided list.
     */
    public void setMeetings(List<Meeting> meetings) {
        requireAllNonNull(meetings);
        if (!meetingsAreUnique(meetings)) {
            throw new DuplicateMeetingException();
        }

        internalList.setAll(meetings);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     *
     * @return An unmodifiable view of the meeting list.
     */
    public ObservableList<Meeting> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Meeting> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueMeetingList)) {
            return false;
        }

        UniqueMeetingList otherUniqueMeetingList = (UniqueMeetingList) other;
        return internalList.equals(otherUniqueMeetingList.internalList);
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
     * Returns true if {@code meetings} contains only unique meetings.
     *
     * @param meetings The list of meetings to check.
     * @return True if all meetings are unique, false otherwise.
     */
    private boolean meetingsAreUnique(List<Meeting> meetings) {
        for (int i = 0; i < meetings.size() - 1; i++) {
            for (int j = i + 1; j < meetings.size(); j++) {
                if (meetings.get(i).isSameMeeting(meetings.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

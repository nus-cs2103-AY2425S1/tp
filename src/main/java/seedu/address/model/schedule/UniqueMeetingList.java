package seedu.address.model.schedule;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.ConflictMeetingException;
import seedu.address.model.person.exceptions.DuplicateMeetingException;
import seedu.address.model.person.exceptions.MeetingNotFoundException;

import java.util.Iterator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class UniqueMeetingList implements Iterable<Meeting> {
    private final ObservableList<Meeting> internalList = FXCollections.observableArrayList();
    private final ObservableList<Meeting> internalUnmodifiableList = FXCollections.unmodifiableObservableList(internalList);
    
    public boolean contains(Meeting toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMeeting);
    }
    
    public boolean conflictMeeting(Meeting toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::hasConflictMeeting);
    }
    
    /**
     * Adds a meeting to the list.
     * The meeting must not already exist in the list.
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
     * {@code target} must exist in the list.
     * The meeting of {@code editedMeeting} must not be the same as another existing meeting in the list.
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
     * Removes the equivalent person from the list.
     * The person must exist in the list.
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
        
        // instanceof handles nulls
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
     * Returns true if {@code persons} contains only unique persons.
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
    
    private boolean meetingsAreNotConflict(List<Meeting> meetings) {
        for (int i = 0; i < meetings.size() - 1; i++) {
            for (int j = i + 1; j < meetings.size(); j++) {
                if (meetings.get(i).hasConflictMeeting(meetings.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

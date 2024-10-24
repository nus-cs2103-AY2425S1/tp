package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateMeetingException;
import seedu.address.model.person.exceptions.MeetingNotFoundException;
import seedu.address.model.person.exceptions.TimeClashException;

/**
 * A list of meetings that ensures no overlaps in timings of all the meetings and does not allow nulls.
 * A meeting clashes if the start time and end time overlaps with any meetings in the list.
 * A meeting clash is determined using the {@code Meeting#isOverlap(Meeting)}. Thus, before every meeting is added to
 * the list, Meeting#isOverlap(Meeting) is used to check for overlap.
 *
 * @see Meeting#isOverlap(Meeting)
 */
public class Meetings {

    private final ObservableList<Meeting> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the meeting clashes with any of the meetings in the list.
     */
    public boolean isClash(Meeting toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isOverlap);
    }

    /**
     * Finds the index of where the meeting should be slotted inside the list.
     *
     * @param toAdd Meeting to be added.
     * @return A valid index from 0 to size of list.
     */
    public int findIndex(Meeting toAdd) {
        for (int i = 0; i < internalList.size() - 1; i++) {
            Meeting temp = internalList.get(i);
            LocalDateTime endTimeTemp = temp.endTime;

            if (toAdd.startTime.isAfter(endTimeTemp)) {
                return i;
            }
        }
        return internalList.size();
    }

    /**
     * Finds the index of the meeting to delete from the list.
     *
     * @param toDelete Meeting to be deleted.
     * @return A valid index from 0 to size of list.
     */
    public int findIndexOfMeetingToDelete(Meeting toDelete) {
        int index = -1;
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).equals(toDelete)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new MeetingNotFoundException();
        }
        return index;
    }

    /**
     * Sorts the list of meetings by startTime.
     */
    public void sortMeetingsByStartTime() {
        FXCollections.sort(internalList, Comparator.comparing(Meeting::getStartTime));
    }

    /**
     * Adds a meeting to the list.
     * The meeting must not clash with other meetings in the list.
     */
    public void addMeeting(Meeting toAdd) {
        requireNonNull(toAdd);
        System.out.println(internalList);
        System.out.println(toAdd);
        if (isClash(toAdd)) {
            throw new TimeClashException();
        }

        int index = findIndex(toAdd);

        // find the index to slot the meeting in.
        internalList.add(index, toAdd);

        sortMeetingsByStartTime();
    }

    /**
     * Delete's a meeting from the list.
     *
     * @param toDelete The meeting to be deleted.
     */
    public void deleteMeeting(Meeting toDelete) {
        requireNonNull(toDelete);
        int index = findIndexOfMeetingToDelete(toDelete);
        internalList.remove(index);
        sortMeetingsByStartTime();
    }

    public Meeting getMeeting(int index) {
        return internalList.get(index);
    }

    /**
     * Returns true if the internal list contains a given (@code meeting).
     */
    public boolean contains(Meeting meeting) {
        requireNonNull(meeting);
        return internalList.stream().anyMatch(meeting::equals);
    }

    /**
     * Replaces the meeting {@code target} in the list with {@code editedMeeting}.
     * {@code target} must exist in the list.
     * The meeting identity of {@code editedMeeting} must not be the same as another existing meeting in the list.
     */
    public void setMeeting(Meeting target, Meeting editedMeeting) {
        requireAllNonNull(target, editedMeeting);

        boolean isClash = internalList.stream()
                .filter(meeting -> !meeting.equals(target))
                .anyMatch(editedMeeting::isOverlap);

        if (isClash) {
            throw new TimeClashException();
        }

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MeetingNotFoundException();
        }

        if (!target.equals(editedMeeting) && contains(editedMeeting)) {
            throw new DuplicateMeetingException();
        }

        internalList.set(index, editedMeeting);
        sortMeetingsByStartTime();
    }

    public int getMeetingsCount() {
        return internalList.size();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Meetings)) {
            return false;
        }

        Meetings otherMeetings = (Meetings) other;
        return internalList.equals(otherMeetings.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder meetingList = new StringBuilder();

        for (int i = 0; i < internalList.size(); i++) {
            meetingList.append(i + 1).append(". Meeting with ").append(getMeeting(i).getPersonToMeet().fullName)
                    .append(" ").append(getMeeting(i).toString()).append("\n");
        }

        return meetingList.toString();
    }

    public ObservableList<Meeting> getInternalList() {
        return internalList;
    }

    public void setInternalList(List<Meeting> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement);
    }
}

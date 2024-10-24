package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.TimeClashException;

/**
 * A list of meetings that ensures no    overlaps in timings of all the meetings and does not allow nulls.
 * A meeting clashes if the start time and end time overlaps with any meetings in the list.
 * A meeting clash is determined using the {@code Meeting#isOverlap(Meeting)}. Thus, before every meeting is added to
 * the list, Meeting#isOverlap(Meeting) is used to check for overlap.
 *
 * @see Meeting#isOverlap(Meeting)
 */
public class Meetings {

    private final ObservableList<Meeting> internalList = FXCollections.observableArrayList();

    public final static String MESSAGE_NO_MEETINGS = "You don't have a meeting arranged with this Udder";
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
        if (isClash(toAdd)) {
            throw new TimeClashException();
        }

        int index = findIndex(toAdd);

        // find the index to slot the meeting in.
        internalList.add(index, toAdd);

        sortMeetingsByStartTime();
    }

    public Meeting getMeeting(int index) {
        return internalList.get(index);
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

    /**
     * Formats the string output of the meetings list to be displayed in the DetailPanel.
     * @return String output of meetings list
     */
    public String toDetailPanelString() {
        if (internalList.isEmpty()) {
            return MESSAGE_NO_MEETINGS;
        }

        StringBuilder meetingList = new StringBuilder();

        for (int i = 0; i < internalList.size(); i++) {
            meetingList.append(i + 1).append(". ").append(getMeeting(i).toString().substring(0, 1).toUpperCase())
                    .append(getMeeting(i).toString().substring(1)).append("\n");
        }

        return meetingList.toString();
    }


    /**
     * @return an {@code ObservableList} object for meetings, such that it is displayable in the UI.
     */
    public ObservableList<Meeting> getInternalList() {
        return internalList;
    }

    /**
     * Replaces the contents of the meeting list with {@code meetings}.
     */
    public void setInternalList(List<Meeting> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement);
    }
}

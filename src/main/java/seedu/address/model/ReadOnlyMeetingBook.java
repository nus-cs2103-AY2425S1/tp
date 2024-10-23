package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.meeting.Meeting;

/**
 * Unmodifiable view of a meeting book
 */
public interface ReadOnlyMeetingBook {
    /**
     * Returns an unmodifiable view of the meeting list.
     * This list will not contain any duplicate meeting.
     */
    ObservableList<Meeting> getMeetingList();
}

package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.schedule.Meeting;

/**
 * Unmodifiable view of a schedule list.
 */
public interface ReadOnlyScheduleList {
    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Meeting> getMeetingList();
}

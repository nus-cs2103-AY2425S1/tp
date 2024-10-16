package seedu.address.model;

import seedu.address.model.schedule.Meeting;
import javafx.collections.ObservableList;

public interface ReadOnlyScheduleList {
    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Meeting> getMeetingList();
}
package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.meetup.MeetUp;

/**
 * Unmodifiable view of a buyer list
 */
public interface ReadOnlyMeetUpList {

    /**
     * Returns an unmodifiable view of the buyers list.
     * This list will not contain any duplicate buyers.
     */
    ObservableList<MeetUp> getMeetUpList();

}

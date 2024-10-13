package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.meetup.MeetUp;;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyMeetUpList {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<MeetUp> getMeetUpList();

}
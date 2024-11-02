package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.LogicManager;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.meetup.UniqueMeetUpList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameMeetUp comparison)
 */
public class MeetUpList implements ReadOnlyMeetUpList {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private final UniqueMeetUpList meetUps;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        meetUps = new UniqueMeetUpList();
    }

    public MeetUpList() {}

    /**
     * Creates an MeetUpList using the meetUps in the {@code toBeCopied}
     */
    public MeetUpList(ReadOnlyMeetUpList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the meetUp list with {@code meetUps}.
     * {@code meetUps} must not contain duplicate meetUps.
     */
    public void setMeetUps(List<MeetUp> meetUps) {
        this.meetUps.setMeetUps(meetUps);
    }

    /**
     * Resets the existing data of this {@code MeetUpList} with {@code newData}.
     */
    public void resetData(ReadOnlyMeetUpList newData) {
        requireNonNull(newData);
        setMeetUps(newData.getMeetUpList());
    }

    //// meetUp-level operations

    /**
     * Returns true if a meetUp with the same identity as {@code meetUp} exists in the meet up list.
     */
    public boolean hasMeetUp(MeetUp meetUp) {
        requireNonNull(meetUp);
        return meetUps.contains(meetUp);
    }

    /**
     * Adds a meetUp to the meet up list.
     * The meetUp must not already exist in the meet up list.
     */
    public void addMeetUp(MeetUp m) {
        meetUps.add(m);
    }

    /**
     * Replaces the given meetUp {@code target} in the list with {@code editedmeetUp}.
     * {@code target} must exist in the meet up list.
     * The meetUp identity of {@code editedmeetUp} must not be the same as another existing meetUp in the meet up list.
     */
    public void setMeetUp(MeetUp target, MeetUp editedMeetUp) {
        requireNonNull(editedMeetUp);

        meetUps.setMeetUp(target, editedMeetUp);
    }

    /**
     * Removes {@code key} from this {@code MeetUpList}.
     * {@code key} must exist in the meet up list.
     */
    public void removeMeetUp(MeetUp key) {
        meetUps.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("meetUps", meetUps)
                .toString();
    }

    @Override
    public ObservableList<MeetUp> getMeetUpList() {
        return meetUps.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MeetUpList)) {
            return false;
        }

        MeetUpList otherMeetUpList = (MeetUpList) other;
        return meetUps.equals(otherMeetUpList.meetUps);
    }

    @Override
    public int hashCode() {
        return meetUps.hashCode();
    }
}

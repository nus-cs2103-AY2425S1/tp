package seedu.address.testutil.meetup;

import seedu.address.model.MeetUpList;
import seedu.address.model.meetup.MeetUp;

/**
 * A utility class to help with building MeetUpList objects.
 * Example usage: <br>
 *     {@code MeetUpList ml = new MeetUpListBuilder().withMeetUp("FirstMeetUp", "SecondMeetUp").build();}
 */
public class MeetUpListBuilder {

    private MeetUpList meetUpList;

    public MeetUpListBuilder() {
        meetUpList = new MeetUpList();
    }

    public MeetUpListBuilder(MeetUpList meetUpList) {
        this.meetUpList = meetUpList;
    }

    /**
     * Adds a new {@code MeetUp} to the {@code MeetUpList} that we are building.
     */
    public MeetUpListBuilder withMeetUp(MeetUp meetUp) {
        meetUpList.addMeetUp(meetUp);
        return this;
    }

    public MeetUpList build() {
        return meetUpList;
    }
}

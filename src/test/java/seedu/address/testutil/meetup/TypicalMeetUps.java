package seedu.address.testutil.meetup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.MeetUpList;
import seedu.address.model.meetup.MeetUp;

/**
 * A utility class containing a list of {@code MeetUp} objects to be used in tests.
 */
public class TypicalMeetUps {

    public static final MeetUp FIRST_MEETUP = new MeetUpBuilder()
            .withName("Client Meet Up")
            .withInfo("Talk to clients and gather requirements")
            .withFrom("2024-12-01 12:00")
            .withTo("2024-12-01 14:00").build();
    public static final MeetUp SECOND_MEETUP = new MeetUpBuilder()
            .withName("Contract Negotiation")
            .withInfo("Finalise contract")
            .withFrom("2024-10-01 08:00")
            .withTo("2024-10-01 11:00").build();
    public static final MeetUp THIRD_MEETUP = new MeetUpBuilder()
            .withName("Sales Closure")
            .withInfo("Discuss compensation and settle on payments")
            .withFrom("2024-10-01 15:00")
            .withTo("2024-10-01 17:00").build();
    public static final MeetUp FOURTH_MEETUP = new MeetUpBuilder()
            .withName("Meet Up with Amelia")
            .withInfo("Discuss potential sale with her")
            .withFrom("2024-11-01 07:00")
            .withTo("2024-11-01 10:00").build();

    public static final MeetUp FITH_MEETUP = new MeetUpBuilder()
            .withName("Property Pitch Superday")
            .withInfo("Pitching to potential clients on current property listing during event")
            .withFrom("2024-10-02 07:00")
            .withTo("2024-10-04 19:00").build();

    // Manually added - MeetUp's details found in {@code CommandTestUtil}
    public static final MeetUp NETWORKING_MEETUP = new MeetUpBuilder()
            .withName("Networking Session")
            .withInfo("Networking with real estate agents.")
            .withFrom("2024-10-12 17:30")
            .withTo("2024-10-12 19:45").build();
    public static final MeetUp PITCH_MEETUP = new MeetUpBuilder()
            .withName("Sales Pitch")
            .withInfo("Pitching property at Bukit Timah.")
            .withFrom("2024-09-11 12:00")
            .withTo("2024-09-11 12:59").build();

    private TypicalMeetUps() {} // prevents instantiation

    /**
     * Returns an {@code MeetUpList} with all the typical meetUpList.
     */
    public static MeetUpList getTypicalMeetUpList() {
        MeetUpList ml = new MeetUpList();
        for (MeetUp meetUp : getTypicalMeetUps()) {
            ml.addMeetUp(meetUp);
        }
        return ml;
    }

    public static List<MeetUp> getTypicalMeetUps() {
        return new ArrayList<>(Arrays.asList(FIRST_MEETUP, SECOND_MEETUP, THIRD_MEETUP, FOURTH_MEETUP, FITH_MEETUP));
    }
}

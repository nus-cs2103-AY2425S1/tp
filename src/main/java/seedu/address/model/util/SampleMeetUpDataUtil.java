package seedu.address.model.util;

import seedu.address.model.MeetUpList;
import seedu.address.model.ReadOnlyMeetUpList;
import seedu.address.model.meetup.From;
import seedu.address.model.meetup.Info;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.meetup.Name;
import seedu.address.model.meetup.To;


/**
 * Contains utility methods for populating {@code BuyerList} with sample data.
 */
public class SampleMeetUpDataUtil {
    public static MeetUp[] getSampleMeetUps() {
        return new MeetUp[] {
            new MeetUp(new Name("Sales Meeting"), new Info("Pitch products to potential clients"),
                    new From("2024-09-15 12:00"), new To("2024-09-15 14:00")),
            new MeetUp(new Name("Closing Meeting"), new Info("Contract negotiation and closing"),
                    new From("2024-09-16 12:00"), new To("2024-09-16 14:00")),
        };
    }

    public static ReadOnlyMeetUpList getSampleMeetUpList() {
        MeetUpList sampleMul = new MeetUpList();
        for (MeetUp sampleMeetUp : getSampleMeetUps()) {
            sampleMul.addMeetUp(sampleMeetUp);
        }
        return sampleMul;
    }


}

package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.MeetUpList;
import seedu.address.model.ReadOnlyMeetUpList;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.meetup.MeetUpFrom;
import seedu.address.model.meetup.MeetUpInfo;
import seedu.address.model.meetup.MeetUpName;
import seedu.address.model.meetup.MeetUpTo;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleMeetUpList {
    public static MeetUp[] getSampleMeetUps() {
        return new MeetUp[] {
            new MeetUp(new MeetUpName("Placeholder"), new MeetUpInfo("placeholder"),
                    new MeetUpFrom(LocalDateTime.now()), new MeetUpTo(LocalDateTime.now())),
            new MeetUp(new MeetUpName("Placeholder2"), new MeetUpInfo("placeholder2"),
                    new MeetUpFrom(LocalDateTime.now()), new MeetUpTo(LocalDateTime.now())),
        };
    }

    public static ReadOnlyMeetUpList getSampleMeetUpList() {
        MeetUpList sampleMul = new MeetUpList();
        for (MeetUp sampleMeetUp : getSampleMeetUps()) {
            sampleMul.addMeetUp(sampleMeetUp);
        }
        return sampleMul;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}

package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.MeetUpList;
import seedu.address.model.ReadOnlyMeetUpList;
import seedu.address.model.meetup.MeetUp;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleMeetUpList {
    public static MeetUp[] getSampleMeetUps() {
        return new MeetUp[] {
            new MeetUp("Placeholder", "placeholder", LocalDateTime.now(), LocalDateTime.now()),
            new MeetUp("Placeholder2", "placeholder2", LocalDateTime.now(), LocalDateTime.now())
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

package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.MeetingBook;
import seedu.address.model.meeting.Meeting;


/**
 * A utility class containing a list of {@code Meeting} objects to be used in tests.
 */
public class TypicalMeetings {

    public static final Meeting MEETING_ADMIRALTY = new MeetingBuilder()
            .withMeetingTitle("Admiralty HDB Client Viewing")
            .withMeetingDate("31-10-2024")
            .withBuyer(VALID_PHONE_AMY)
            .withSeller(VALID_PHONE_BOB)
            .withType("CONDO")
            .withPostalCode("654321")
            .build();

    public static final Meeting MEETING_BEDOK = new MeetingBuilder()
            .withMeetingTitle("Bedok Villa Finalizing Purchase Agreement")
            .withMeetingDate("11-06-2024")
            .withBuyer("91234562")
            .withSeller("81234563")
            .withType("hdb")
            .withPostalCode("123456")
            .build();

    // Manually added
    public static final Meeting MEETING_CLEMENTI = new MeetingBuilder()
            .withMeetingTitle("Clementi Apartment Lease Agreement Discussion")
            .withMeetingDate("01-01-2025")
            .withBuyer("91234564")
            .withSeller("81234565")
            .withType("hdb")
            .withPostalCode("654321")
            .build();

    public static final Meeting MEETING_DOVER = new MeetingBuilder()
            .withMeetingTitle("Dover condo client viewing")
            .withMeetingDate("02-01-2025")
            .withBuyer("91234566")
            .withSeller("81234567")
            .withType("hdb")
            .withPostalCode("890123")
            .build();
    private TypicalMeetings() {} // prevents instantiation
    /**
     * Returns an {@code MeetingBook} with all the typical persons.
     */
    public static MeetingBook getTypicalMeetingBook() {
        MeetingBook ab = new MeetingBook();
        for (Meeting meeting : getTypicalMeetings()) {
            ab.addMeeting(meeting);
        }
        return ab;
    }

    public static List<Meeting> getTypicalMeetings() {
        return new ArrayList<>(Arrays.asList(MEETING_ADMIRALTY, MEETING_BEDOK));
    }
}

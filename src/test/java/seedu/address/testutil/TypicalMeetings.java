package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import seedu.address.model.ReadOnlyScheduleList;
import seedu.address.model.ScheduleList;
import seedu.address.model.schedule.Meeting;

/**
 * A utility class containing a list of {@code Meeting} objects to be used in tests.
 *
 * <p>This class provides several predefined {@code Meeting} instances as typical
 * examples for testing purposes, such as {@code TEAM_SYNC}, {@code PROJECT_DISCUSSION},
 * and {@code CLIENT_REVIEW}.
 *
 * <p>The {@link #getTypicalMeetings()} method returns a {@code ReadOnlyScheduleList}
 * containing these meetings, allowing test cases to operate on a fixed set of sample data.
 *
 * <p>Usage of this class helps ensure consistency and reusability across different
 * test cases that involve scheduling or meeting functionality.
 */
public class TypicalMeetings {

    public static final UUID UUID_1 = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public static final UUID UUID_2 = UUID.fromString("22222222-2222-2222-2222-222222222222");
    public static final UUID UUID_3 = UUID.fromString("33333333-3333-3333-3333-333333333333");
    public static final UUID UUID_4 = UUID.fromString("44444444-4444-4444-4444-444444444444");
    public static final UUID UUID_5 = UUID.fromString("55555555-5555-5555-5555-555555555555");
    public static final UUID UUID_6 = UUID.fromString("66666666-6666-6666-6666-666666666666");
    public static final UUID UUID_7 = UUID.fromString("77777777-7777-7777-7777-777777777777");

    public static final Meeting TEAM_SYNC = new MeetingBuilder()
            .withName("Team Sync")
            .withDate(LocalDate.of(2024, 11, 1))
            .withTime(LocalTime.of(10, 0))
            .withContactUids(Arrays.asList(UUID_1, UUID_2))
            .build();

    public static final Meeting PROJECT_DISCUSSION = new MeetingBuilder()
            .withName("Project Discussion")
            .withDate(LocalDate.of(2024, 11, 3))
            .withTime(LocalTime.of(14, 30))
            .withContactUids(Arrays.asList(UUID_3, UUID_4, UUID_5))
            .build();

    public static final Meeting CLIENT_REVIEW = new MeetingBuilder()
            .withName("Client Review")
            .withDate(LocalDate.of(2024, 11, 5))
            .withTime(LocalTime.of(16, 0))
            .withContactUids(Arrays.asList(UUID_6, UUID_7))
            .build();

    public static final Meeting TEAM_SYNC_1 = new MeetingBuilder()
            .withName("Team Sync")
            .withDate(LocalDate.of(2024, 11, 1))
            .withTime(LocalTime.of(10, 0))
            .withContactUids(List.of(UUID_1))
            .build();

    public static final Meeting TEAM_SYNC_2 = new MeetingBuilder()
            .withName("Team Sync")
            .withDate(LocalDate.of(2024, 11, 1))
            .withTime(LocalTime.of(10, 0))
            .withContactUids(List.of(UUID_2))
            .build();

    /**
     * Returns a ReadOnlyScheduleList containing typical meetings for testing purposes.
     */
    public static ReadOnlyScheduleList getTypicalMeetings() {
        ScheduleList scheduleList = new ScheduleList();
        scheduleList.addMeeting(TEAM_SYNC);
        scheduleList.addMeeting(PROJECT_DISCUSSION);
        scheduleList.addMeeting(CLIENT_REVIEW);
        return scheduleList;
    }

    public static ReadOnlyScheduleList getAlmostDuplicateMeetings() {
        ScheduleList scheduleList = new ScheduleList();
        scheduleList.addMeeting(TEAM_SYNC_1);
        scheduleList.addMeeting(TEAM_SYNC_2);
        return scheduleList;
    }
}


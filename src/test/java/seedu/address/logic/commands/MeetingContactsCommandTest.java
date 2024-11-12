package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetings;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ScheduleList;
import seedu.address.model.UserPrefs;
import seedu.address.model.schedule.Meeting;

public class MeetingContactsCommandTest {
    private static final Meeting NO_UID_MEETING = new Meeting(List.<UUID>of(), "meeting with no uid",
            LocalDate.of(2022, 10, 10), LocalTime.of(10, 0));

    private final Model model = new ModelManager(getTypicalAddressBook(),
            new UserPrefs(), getTypicalMeetings());

    @Test
    public void execute_seccess() {
        MeetingContactsCommand meetingContactsCommand =
                new MeetingContactsCommand(INDEX_FIRST_MEETING);
        String expectedMessage = "2 persons listed!";

        // command success no change in model.
        assertCommandSuccess(meetingContactsCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_indexOutOfRangeLowerBound_failure() {
        MeetingContactsCommand meetingContactsCommand =
            new MeetingContactsCommand(new IndexStub(-1));

        // command failure query index lower than 0.
        assertThrows(CommandException.class, () -> meetingContactsCommand.execute(model));
    }

    @Test
    public void execute_indexOutOfRangeUpperBound_failure() {
        MeetingContactsCommand meetingContactsCommand =
            new MeetingContactsCommand(new IndexStub(100));

        // command failure query index higher than schedule size.
        assertThrows(CommandException.class, () -> meetingContactsCommand.execute(model));
    }

    @Test
    public void execute_assertMeetingHasContactUid_failure() {
        ScheduleList scheduleList = new ScheduleList();
        scheduleList.addMeeting(NO_UID_MEETING);
        Model modelAnother = new ModelManager(new AddressBook(), new UserPrefs(), scheduleList);
        MeetingContactsCommand meetingContactsCommand =
            new MeetingContactsCommand(INDEX_FIRST_MEETING);

        // command failure meeting has no contact uid.
        assertThrows(AssertionError.class, () -> meetingContactsCommand.execute(modelAnother));
    }

    @Test
    public void equals() {
        MeetingContactsCommand meetingContactsCommand =
            new MeetingContactsCommand(INDEX_FIRST_MEETING);
        // same object -> returns true
        assert(meetingContactsCommand.equals(meetingContactsCommand));
        // same values -> returns true
        MeetingContactsCommand meetingContactsCommandCopy =
            new MeetingContactsCommand(INDEX_FIRST_MEETING);
        assert(meetingContactsCommand.equals(meetingContactsCommandCopy));
        // different types -> returns false
        assert(!meetingContactsCommand.equals(1));
        // null -> returns false
        assert(!meetingContactsCommand.equals(null));
        // different index -> returns false
        MeetingContactsCommand meetingContactsCommandDifferentIndex =
            new MeetingContactsCommand(INDEX_SECOND_MEETING);
        assert(!meetingContactsCommand.equals(meetingContactsCommandDifferentIndex));
        assert(!meetingContactsCommand.equals("aaaa"));
    }

    @Test
    public void toStringTest() {
        MeetingContactsCommand meetingContactsCommand =
                new MeetingContactsCommand(INDEX_FIRST_MEETING);
        assert(meetingContactsCommand.toString()
                .equals("MeetingContactsCommand{targetMeetingIndex="
                + "seedu.address.commons.core.index.Index{zeroBasedIndex=0}}"));
    }

    public class IndexStub extends Index {
        private int index;

        public IndexStub(int index) {
            super();
            this.index = index;
        }

        public int getZeroBased() {
            return index;
        }
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetings;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.schedule.Meeting;
import seedu.address.testutil.EditScheduleDescriptorBuilder;
import seedu.address.testutil.MeetingBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditScheduleCommand.
 */
public class EditScheduleCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalMeetings());

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Meeting lastMeeting = model.getMeeting(INDEX_SECOND_MEETING);

        MeetingBuilder meetingInList = new MeetingBuilder(lastMeeting);
        Meeting editedMeeting = meetingInList.withName("Team Meeting")
                .withDate(LocalDate.of(2024, 10, 11))
                .withTime(LocalTime.of(15, 0))
                .build();

        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder()
                .withName("Team Meeting")
                .withDate(LocalDate.of(2024, 10, 11))
                .withTime(LocalTime.of(15, 0))
                .withContactIndexes(List.of(Index.fromOneBased(1)))
                .build();
        EditScheduleCommand editScheduleCommand = new EditScheduleCommand(INDEX_SECOND_MEETING, descriptor);

        String expectedMessage = String.format(EditScheduleCommand.MESSAGE_EDIT_SCHEDULE_SUCCESS,
                "Team Meeting", "2024-10-11", "15:00");

        // Create expected model using the full address book, user preferences, and a ReadOnlyScheduleList.
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getScheduleList());
        expectedModel.setMeeting(lastMeeting, editedMeeting);

        assertCommandSuccess(editScheduleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        EditScheduleCommand editScheduleCommand = new EditScheduleCommand(
                INDEX_FIRST_MEETING, new EditScheduleDescriptor());

        assertThrows(CommandException.class, () -> editScheduleCommand.execute(model));
    }

    @Test
    public void execute_invalidMeetingIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getWeeklySchedule().size() + 1);
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder().withName("Invalid Meeting").build();
        EditScheduleCommand editScheduleCommand = new EditScheduleCommand(outOfBoundIndex, descriptor);

        assertThrows(CommandException.class, () -> editScheduleCommand.execute(model));
    }

    @Test
    public void execute_duplicateMeetingUnfilteredList_failure() {
        Meeting firstMeeting = model.getMeeting(INDEX_FIRST_MEETING);
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder()
                .withName(firstMeeting.getMeetingName())
                .withDate(firstMeeting.getMeetingDate())
                .withTime(firstMeeting.getMeetingTime())
                .build();
        EditScheduleCommand editScheduleCommand = new EditScheduleCommand(INDEX_FIRST_MEETING, descriptor);

        assertThrows(CommandException.class, () -> editScheduleCommand.execute(model));
    }

    @Test
    public void execute_invalidContactIndexUnfilteredList_failure() {
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder()
                .withName("Team Meeting")
                .withDate(LocalDate.of(2024, 10, 11))
                .withTime(LocalTime.of(15, 0))
                .withContactIndexes(List.of(Index.fromOneBased(1000)))
                .build();
        EditScheduleCommand editScheduleCommand = new EditScheduleCommand(INDEX_SECOND_MEETING, descriptor);

        assertThrows(CommandException.class, () -> editScheduleCommand.execute(model));
    }

    @Test
    public void equals() {
        EditScheduleCommand editFirstCommand = new EditScheduleCommand(INDEX_FIRST_MEETING,
                new EditScheduleDescriptorBuilder().withName("Team Meeting").build());
        EditScheduleCommand editSecondCommand = new EditScheduleCommand(INDEX_SECOND_MEETING,
                new EditScheduleDescriptorBuilder().withName("Project Meeting").build());

        // same object -> returns true
        assertEquals(editFirstCommand, editFirstCommand);

        // same values -> returns true
        EditScheduleCommand editFirstCommandCopy = new EditScheduleCommand(INDEX_FIRST_MEETING,
                new EditScheduleDescriptorBuilder().withName("Team Meeting").build());
        assertEquals(editFirstCommand, editFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(editFirstCommand, 1);

        // null -> returns false
        assertNotEquals(editFirstCommand, null);

        // different meeting -> returns false
        assertNotEquals(editFirstCommand, editSecondCommand);
    }

    @Test
    public void descriptor_equals() {
        EditScheduleDescriptor descriptor1 = new EditScheduleDescriptor();
        descriptor1.setName(new Name("Team Meeting"));
        descriptor1.setDate(LocalDate.of(2024, 10, 11));
        descriptor1.setTime(LocalTime.of(15, 0));
        descriptor1.setContactIndex(List.of(Index.fromOneBased(1)));

        // same values -> returns true
        EditScheduleDescriptor descriptorSame = new EditScheduleDescriptor();
        descriptorSame.setName(new Name("Team Meeting"));
        descriptorSame.setDate(LocalDate.of(2024, 10, 11));
        descriptorSame.setTime(LocalTime.of(15, 0));
        descriptorSame.setContactIndex(List.of(Index.fromOneBased(1)));
        assertEquals(descriptor1, descriptorSame);

        // same object -> returns true
        assertEquals(descriptor1, descriptor1);

        // null -> returns false
        assertNotEquals(null, descriptor1);

        // different types -> returns false
        assertNotEquals("Some String", descriptor1);

        // different name -> returns false
        EditScheduleDescriptor descriptorDifferentName = new EditScheduleDescriptor();
        descriptorDifferentName.setName(new Name("Project Meeting"));
        descriptorDifferentName.setDate(LocalDate.of(2024, 10, 11));
        descriptorDifferentName.setTime(LocalTime.of(15, 0));
        descriptorDifferentName.setContactIndex(List.of(Index.fromOneBased(1)));
        assertNotEquals(descriptor1, descriptorDifferentName);

        // different date -> returns false
        EditScheduleDescriptor descriptorDifferentDate = new EditScheduleDescriptor();
        descriptorDifferentDate.setName(new Name("Team Meeting"));
        descriptorDifferentDate.setDate(LocalDate.of(2024, 11, 11));
        descriptorDifferentDate.setTime(LocalTime.of(15, 0));
        descriptorDifferentDate.setContactIndex(List.of(Index.fromOneBased(1)));
        assertNotEquals(descriptor1, descriptorDifferentDate);

        // different time -> returns false
        EditScheduleDescriptor descriptorDifferentTime = new EditScheduleDescriptor();
        descriptorDifferentTime.setName(new Name("Team Meeting"));
        descriptorDifferentTime.setDate(LocalDate.of(2024, 10, 11));
        descriptorDifferentTime.setTime(LocalTime.of(16, 0));
        descriptorDifferentTime.setContactIndex(List.of(Index.fromOneBased(1)));
        assertNotEquals(descriptor1, descriptorDifferentTime);

        // different contact indexes -> returns false
        EditScheduleDescriptor descriptorDifferentContactIndexes = new EditScheduleDescriptor();
        descriptorDifferentContactIndexes.setName(new Name("Team Meeting"));
        descriptorDifferentContactIndexes.setDate(LocalDate.of(2024, 10, 11));
        descriptorDifferentContactIndexes.setTime(LocalTime.of(15, 0));
        descriptorDifferentContactIndexes.setContactIndex(List.of(Index.fromOneBased(2)));
        assertNotEquals(descriptor1, descriptorDifferentContactIndexes);
    }
}

package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalClientBook;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProperty.getTypicalPropertyBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meeting.Meeting;
import seedu.address.testutil.MeetingBuilder;

public class AddMeetingCommandTest {
    private Model model;
    private Meeting meeting;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalPropertyBook(),
                getTypicalClientBook(), getTypicalMeetingBook());
        meeting = mock(Meeting.class); // Mocking a Meeting object
    }

    @Test
    public void execute_newMeeting_success() {
        Meeting validMeeting = new MeetingBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getPropertyBook(),
                model.getClientBook(), model.getMeetingBook());
        expectedModel.addMeeting(validMeeting);

        System.out.println(Messages.format(validMeeting));

        assertCommandSuccess(new AddMeetingCommand(validMeeting), model,
                String.format(AddMeetingCommand.MESSAGE_SUCCESS, Messages.format(validMeeting)),
                expectedModel);
    }

    @Test
    public void execute_duplicateMeeting_throwsCommandException() {
        Meeting meetingInList = model.getMeetingBook().getMeetingList().get(0);
        assertCommandFailure(new AddMeetingCommand(meetingInList), model,
                AddMeetingCommand.MESSAGE_DUPLICATE_MEETING);
    }

    @Test
    void equals_sameObject_returnsTrue() {
        AddMeetingCommand addMeetingCommand = new AddMeetingCommand(meeting);
        assertEquals(addMeetingCommand, addMeetingCommand); // Same object should return true
    }

    @Test
    void equals_sameMeeting_returnsTrue() {
        // Arrange
        AddMeetingCommand addMeetingCommand1 = new AddMeetingCommand(meeting);
        AddMeetingCommand addMeetingCommand2 = new AddMeetingCommand(meeting); // Same buyer

        // Act & Assert
        assertEquals(addMeetingCommand1, addMeetingCommand2); // Different instances, same buyer
    }

    @Test
    void equals_differentMeeting_returnsFalse() {
        Meeting differentMeeting = mock(Meeting.class); // Different Meeting instance
        AddMeetingCommand addMeetingCommand1 = new AddMeetingCommand(meeting);
        AddMeetingCommand addMeetingCommand2 = new AddMeetingCommand(differentMeeting); // Different meeting

        assertNotEquals(addMeetingCommand1, addMeetingCommand2);
    }
}

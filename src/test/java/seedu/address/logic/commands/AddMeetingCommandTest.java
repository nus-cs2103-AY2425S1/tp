package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalClientBook;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingBook;
import static seedu.address.testutil.TypicalProperty.getTypicalPropertyBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
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
        model = new ModelManager(new UserPrefs(), getTypicalPropertyBook(),
                getTypicalClientBook(), getTypicalMeetingBook());
        meeting = mock(Meeting.class); // Mocking a Meeting object
    }

    @Test
    public void execute_newMeeting_success() {
        Meeting validMeeting = new MeetingBuilder().build();

        Model expectedModel = new ModelManager(new UserPrefs(), model.getPropertyBook(),
                model.getClientBook(), model.getMeetingBook());
        expectedModel.addMeeting(validMeeting);

        System.out.println(Messages.format(validMeeting));

        assertCommandSuccess(new AddMeetingCommand(validMeeting), model,
                String.format(AddMeetingCommand.MESSAGE_SUCCESS, Messages.format(validMeeting)),
                expectedModel);
    }

    @Test
    public void execute_duplicateMeeting_throwsCommandException() {
        // Arrange
        AddMeetingCommand addMeetingCommand = new AddMeetingCommand(meeting);

        // Stubbing the behavior of model to simulate the presence of a duplicate buyer
        when(model.hasMeeting(meeting)).thenReturn(true);

        // Act & Assert
        assertThrows(CommandException.class, () -> addMeetingCommand.execute(model),
                AddBuyerCommand.MESSAGE_DUPLICATE_BUYER);
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

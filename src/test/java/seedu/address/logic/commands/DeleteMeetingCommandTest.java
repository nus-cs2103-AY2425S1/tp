package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class DeleteMeetingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        Person benson = TypicalPersons.BENSON;
        Meeting meetingToDelete = new Meeting(new Name("Benson Meier"),
                LocalDateTime.parse("10-11-2024 10:00", formatter),
                LocalDateTime.parse("10-11-2024 10:00", formatter),
                "Room 700");

        model.addMeeting(benson, meetingToDelete);

        DeleteMeetingCommand deleteMeetingCommand = new DeleteMeetingCommand(INDEX_FIRST_MEETING);

        String expectedMessage = String.format(DeleteMeetingCommand.MESSAGE_DELETE_MEETING_SUCCESS,
                Messages.format(meetingToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.deleteMeeting(benson, meetingToDelete);

        assertCommandSuccess(deleteMeetingCommand, model, expectedMessage, expectedModel);
    }
}

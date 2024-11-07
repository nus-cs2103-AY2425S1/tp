package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
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
import seedu.address.model.person.Person;

public class DeleteMeetingCommandTest {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validMeetingIndex_success() throws CommandException {
        LocalDateTime startTime = LocalDateTime.parse("30-07-2024 11:00", formatter);
        LocalDateTime endTime = LocalDateTime.parse("30-07-2024 12:00", formatter);
        String location = "Some location";
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Meeting firstMeeting = new Meeting(personToEdit.getName(), startTime, endTime, location);
        model.addMeeting(personToEdit, firstMeeting);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        String expectedMessage = String.format(DeleteMeetingCommand.MESSAGE_DELETE_MEETING_SUCCESS,
                Messages.format(firstMeeting));
        assertCommandSuccess(new DeleteMeetingCommand(INDEX_FIRST_MEETING), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyMeetingsList_throwsCommandException() throws CommandException {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertThrows(CommandException.class, () -> new DeleteMeetingCommand(INDEX_FIRST_MEETING).execute(model));
    }

    @Test
    public void equals() {
        DeleteMeetingCommand deleteFirstCommand = new DeleteMeetingCommand(INDEX_FIRST_MEETING);
        DeleteMeetingCommand deleteSecondCommand = new DeleteMeetingCommand(INDEX_SECOND_MEETING);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteMeetingCommand deleteFirstCommandCopy = new DeleteMeetingCommand(INDEX_FIRST_MEETING);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}

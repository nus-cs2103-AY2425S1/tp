package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditMeetingDescriptorBuilder;

public class EditMeetingCommandTest {
    private Model model;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    @Test
    public void execute_allFieldsSpecified_success() throws Exception {
        LocalDateTime startTime = LocalDateTime.parse("30-07-2024 11:00", formatter);
        LocalDateTime endTime = LocalDateTime.parse("30-07-2024 12:00", formatter);
        String location = "A Valid Location";
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Meeting firstMeeting = new Meeting(personToEdit.getName(), startTime, endTime, location);
        model.addMeeting(personToEdit, firstMeeting);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Meeting updatedMeeting = new Meeting(personToEdit.getName(), LocalDateTime.parse("01-08-2024 11:00",
                formatter), LocalDateTime.parse("01-08-2024 12:00", formatter), "Some new place");
        EditMeetingCommand.EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(updatedMeeting).build();
        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, updatedMeeting);
        expectedModel.setMeeting(personToEdit, firstMeeting, updatedMeeting);

        CommandResult result = editMeetingCommand.execute(expectedModel);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        expectedModel.deleteMeeting(personToEdit, updatedMeeting);
        assertEquals(expectedModel.getMeetingSize(), 0);
    }

    @Test
    public void execute_onlyLocationChanged_success() throws CommandException {
        LocalDateTime startTime = LocalDateTime.parse("02-08-2024 10:00", formatter);
        LocalDateTime endTime = LocalDateTime.parse("02-08-2024 12:00", formatter);
        String location = "A Valid Location";
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Meeting firstMeeting = new Meeting(personToEdit.getName(), startTime, endTime, location);
        model.addMeeting(personToEdit, firstMeeting);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Meeting updatedMeeting = new Meeting(personToEdit.getName(), startTime, endTime, "Some new place");
        EditMeetingCommand.EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(updatedMeeting).build();
        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, updatedMeeting);
        expectedModel.setMeeting(personToEdit, firstMeeting, updatedMeeting);

        CommandResult result = editMeetingCommand.execute(expectedModel);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        expectedModel.deleteMeeting(personToEdit, updatedMeeting);
        assertEquals(expectedModel.getMeetingSize(), 0);
    }

    @Test
    public void execute_onlyStartTimeChanged_success() throws CommandException {
        LocalDateTime startTime = LocalDateTime.parse("03-08-2024 10:00", formatter);
        LocalDateTime endTime = LocalDateTime.parse("03-08-2024 12:00", formatter);
        String location = "COM4";
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Meeting firstMeeting = new Meeting(personToEdit.getName(), startTime, endTime, location);
        model.addMeeting(personToEdit, firstMeeting);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Meeting updatedMeeting = new Meeting(personToEdit.getName(),
                LocalDateTime.parse("03-08-2024 11:00", formatter), endTime, location);
        EditMeetingCommand.EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(updatedMeeting).build();
        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, updatedMeeting);
        expectedModel.setMeeting(personToEdit, firstMeeting, updatedMeeting);

        CommandResult result = editMeetingCommand.execute(expectedModel);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        expectedModel.deleteMeeting(personToEdit, updatedMeeting);
        assertEquals(expectedModel.getMeetingSize(), 0);
    }

    @Test
    public void execute_onlyEndTimeChanged_success() throws CommandException {
        LocalDateTime startTime = LocalDateTime.parse("04-08-2024 10:00", formatter);
        LocalDateTime endTime = LocalDateTime.parse("04-08-2024 12:00", formatter);
        String location = "COM4";
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Meeting firstMeeting = new Meeting(personToEdit.getName(), startTime, endTime, location);
        model.addMeeting(personToEdit, firstMeeting);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Meeting updatedMeeting = new Meeting(personToEdit.getName(), startTime,
                LocalDateTime.parse("04-08-2024 12:00", formatter), location);
        EditMeetingCommand.EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(updatedMeeting).build();
        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, updatedMeeting);
        expectedModel.setMeeting(personToEdit, firstMeeting, updatedMeeting);

        CommandResult result = editMeetingCommand.execute(expectedModel);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        expectedModel.deleteMeeting(personToEdit, updatedMeeting);
        assertEquals(expectedModel.getMeetingSize(), 0);
    }

    @Test
    public void execute_onlyNameChanged_success() throws CommandException {
        LocalDateTime startTime = LocalDateTime.parse("05-08-2024 15:00", formatter);
        LocalDateTime endTime = LocalDateTime.parse("05-08-2024 16:00", formatter);
        String location = "COM4";
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person newPerson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Meeting firstMeeting = new Meeting(personToEdit.getName(), startTime, endTime, location);
        model.addMeeting(personToEdit, firstMeeting);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Meeting updatedMeeting = new Meeting(newPerson.getName(), startTime, endTime, location);
        EditMeetingCommand.EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(updatedMeeting).build();
        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, updatedMeeting);
        expectedModel.deleteMeeting(personToEdit, firstMeeting);
        expectedModel.addMeeting(newPerson, updatedMeeting);

        CommandResult result = editMeetingCommand.execute(expectedModel);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        expectedModel.deleteMeeting(newPerson, updatedMeeting);
        assertEquals(expectedModel.getMeetingSize(), 0);
    }

    @Test
    public void equals() {
        EditMeetingCommand.EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder()
                .withName("Test")
                .withStartTime("09-10-2024 10:00")
                .withEndTime("10-10-2024 10:00")
                .withLocation("somewhere").build();
        EditMeetingCommand editFirstCommand = new EditMeetingCommand(INDEX_FIRST_PERSON, descriptor);
        EditMeetingCommand editSecondCommand = new EditMeetingCommand(INDEX_SECOND_PERSON, descriptor);

        // same object -> returns true
        assertTrue(editFirstCommand.equals(editFirstCommand));

        // same values -> returns true
        EditMeetingCommand editFirstCommandCopy = new EditMeetingCommand(INDEX_FIRST_PERSON, descriptor);
        assertTrue(editFirstCommand.equals(editFirstCommandCopy));

        // different types -> returns false
        assertFalse(editFirstCommand.equals(1));

        // null -> returns false
        assertFalse(editFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(editFirstCommand.equals(editSecondCommand));
    }
}


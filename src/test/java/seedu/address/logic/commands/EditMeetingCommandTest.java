package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditMeetingCommand.EditMeetingDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Meeting;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditMeetingDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class EditMeetingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws CommandException {
        // Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        // Person person = TypicalPersons.DANIEL;
        // Person editedPerson = new PersonBuilder().build();
        // expectedModel.addPerson(editedPerson);
        // model.addPerson(editedPerson);

        // Meeting meetingToBeEdited = new Meeting(person.getName(),
        //         LocalDateTime.parse("09-10-2030 08:00", formatter),
        //         LocalDateTime.parse("09-10-2030 11:00", formatter),
        //         "Room 123");

        // expectedModel.addMeeting(person, meetingToBeEdited);
        // System.out.println("After expected model");
        // System.out.println("FROM TESTING CLASS: " + expectedModel.getMeetingSize());

        // Meeting editedMeeting = new Meeting(editedPerson.getName(),
        //         LocalDateTime.parse("10-10-2030 08:00", formatter),
        //         LocalDateTime.parse("10-10-2030 11:00", formatter),
        //         "Room 234");

        // EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(editedMeeting).build();

        // EditMeetingCommand editMeetingCommand = new EditMeetingCommand(INDEX_FIRST_MEETING, descriptor);

        // String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        // expectedModel.deleteMeeting(person, meetingToBeEdited);
        // expectedModel.addMeeting(editedPerson, editedMeeting);

        // assertCommandSuccess(editMeetingCommand, model, expectedMessage, expectedModel);
        // Setup: Create the edited meeting and descriptor
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        Person person = TypicalPersons.ALICE;
        Meeting editedMeeting = new Meeting(
                new Name("Alice Pauline"),
                LocalDateTime.parse("10-10-2030 08:00", formatter),
                LocalDateTime.parse("10-10-2030 10:00", formatter),
                "Room 234");

        EditMeetingDescriptor descriptor = new EditMeetingDescriptorBuilder(editedMeeting).build();
        EditMeetingCommand editMeetingCommand = new EditMeetingCommand(INDEX_FIRST_MEETING, descriptor);

        // Expected message after successful edit
        String expectedMessage = String.format(EditMeetingCommand.MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting);

        // Setup expected model: Create a copy of the current model and update it with the edited meeting
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.addMeeting(person, editedMeeting);

        // Assert the command succeeds and the model matches the expected model
        assertCommandSuccess(editMeetingCommand, model, expectedMessage, expectedModel);
    }
}

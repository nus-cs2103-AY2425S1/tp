package seedu.address.logic.commands;

import static seedu.address.commons.util.DateUtil.DATE_TIME_FORMATTER;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESCRIPTION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_END_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_END_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_START_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_START_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_END_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_START_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class AddAppointmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addValidAppointment_success() throws CommandException {

        // Step 1: Create a valid appointment
        Appointment validAppointment = new Appointment(APPOINTMENT_DESCRIPTION_AMY,
                LocalDateTime.parse(VALID_APPOINTMENT_START_AMY, DATE_TIME_FORMATTER),
                LocalDateTime.parse(VALID_APPOINTMENT_END_AMY, DATE_TIME_FORMATTER));
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(validAppointment, INDEX_FIRST_PERSON);

        // Step 2: Prepare the expected message, including full details of editedPerson with the appointment
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit)
                .withAppointment(APPOINTMENT_DESCRIPTION_AMY,
                        LocalDateTime.parse(VALID_APPOINTMENT_START_AMY, DATE_TIME_FORMATTER),
                        LocalDateTime.parse(VALID_APPOINTMENT_END_AMY, DATE_TIME_FORMATTER)).build();
        String expectedMessage = String.format(AddAppointmentCommand.MESSAGE_ADD_APPOINTMENT_SUCCESS,
                Messages.format(editedPerson));

        // Step 3: Set up the expected model state
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        // Step 4: Execute the command and verify success
        assertCommandSuccess(addAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        // Step 1: Create an index that is out of bounds
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        // Step 2: Create a valid appointment
        Appointment validAppointment = new Appointment(APPOINTMENT_DESCRIPTION_AMY,
                LocalDateTime.parse(VALID_APPOINTMENT_START_AMY, DATE_TIME_FORMATTER),
                LocalDateTime.parse(VALID_APPOINTMENT_END_AMY, DATE_TIME_FORMATTER));

        // Step 3: Set up AddAppointmentCommand with the out-of-bound index
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(validAppointment, outOfBoundIndex);

        // Step 4: Expect failure with MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
        assertCommandFailure(addAppointmentCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    // overlapping dates occurring between an existing appointment
    @Test
    public void execute_overlappingAppointmentDateWithin_failure() {
        //step 1: Create a invalid appointment
        Appointment invalidAppointment = new Appointment(APPOINTMENT_DESCRIPTION_AMY,
                LocalDateTime.parse(INVALID_APPOINTMENT_START_AMY, DATE_TIME_FORMATTER),
                LocalDateTime.parse(INVALID_APPOINTMENT_END_AMY, DATE_TIME_FORMATTER));


        //step 2: Set up AddAppointmentCommand with the overlapping dates with other appointments
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(invalidAppointment,
                INDEX_SECOND_PERSON);


        assertCommandFailure(addAppointmentCommand, model, AddAppointmentCommand.MESSAGE_OVERLAPPING_APPOINTMENT);
    }


    // overlapping dates occurring outside an existing appointment
    @Test
    public void execute_overlappingAppointmentDateOutside_failure() {
        //step 1: Create a invalid appointment
        Appointment invalidAppointment = new Appointment(APPOINTMENT_DESCRIPTION_AMY,
                LocalDateTime.parse(INVALID_APPOINTMENT_START_BOB, DATE_TIME_FORMATTER),
                LocalDateTime.parse(INVALID_APPOINTMENT_END_BOB, DATE_TIME_FORMATTER));


        //step 2: Set up AddAppointmentCommand with the overlapping dates with other appointments
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(invalidAppointment,
                INDEX_SECOND_PERSON);


        assertCommandFailure(addAppointmentCommand, model, AddAppointmentCommand.MESSAGE_OVERLAPPING_APPOINTMENT);
    }


    // overlapping dates occurring outside an existing appointment
    @Test
    public void execute_overlappingEndingAppointmentDate_failure() {
        //step 1: Create a invalid appointment
        Appointment invalidAppointment = new Appointment(APPOINTMENT_DESCRIPTION_AMY,
                LocalDateTime.parse(INVALID_APPOINTMENT_START_BOB, DATE_TIME_FORMATTER),
                LocalDateTime.parse(INVALID_APPOINTMENT_END_AMY, DATE_TIME_FORMATTER));


        //step 2: Set up AddAppointmentCommand with the overlapping dates with other appointments
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(invalidAppointment,
                INDEX_SECOND_PERSON);


        assertCommandFailure(addAppointmentCommand, model, AddAppointmentCommand.MESSAGE_OVERLAPPING_APPOINTMENT);
    }


    // overlapping dates occurring outside an existing appointment
    @Test
    public void execute_overlappingStartingAppointmentDate_failure() {
        //step 1: Create a invalid appointment
        Appointment invalidAppointment = new Appointment(APPOINTMENT_DESCRIPTION_AMY,
                LocalDateTime.parse(INVALID_APPOINTMENT_START_AMY, DATE_TIME_FORMATTER),
                LocalDateTime.parse(INVALID_APPOINTMENT_END_BOB, DATE_TIME_FORMATTER));


        //step 2: Set up AddAppointmentCommand with the overlapping dates with other appointments
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(invalidAppointment,
                INDEX_SECOND_PERSON);


        assertCommandFailure(addAppointmentCommand, model, AddAppointmentCommand.MESSAGE_OVERLAPPING_APPOINTMENT);
    }

}

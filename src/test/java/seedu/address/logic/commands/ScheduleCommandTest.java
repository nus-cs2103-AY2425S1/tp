package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ScheduleCommandParser;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Schedule;
import seedu.address.testutil.PersonBuilder;

public class ScheduleCommandTest {

    private Model model;
    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validSchedule_success() {
        Person personToSchedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Schedule validSchedule = new Schedule("2024-10-04 1000");

        ScheduleCommand command = new ScheduleCommand(personToSchedule.getName().toString(), validSchedule);

        Person scheduledPerson = new PersonBuilder(personToSchedule).withSchedule(validSchedule.dateTime).build();

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_SUCCESS,
                validSchedule, personToSchedule.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToSchedule, scheduledPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTime_throwsCommandException() {
        Person personToSchedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Schedule invalidSchedule = new Schedule("2024-10-05 1730"); // Outside working hours

        ScheduleCommand command = new ScheduleCommand(personToSchedule.getName().toString(), invalidSchedule);

        assertCommandFailure(command, model, ScheduleCommand.MESSAGE_INVALID_TIME);
    }

    @Test
    public void execute_slotTaken_throwsCommandException() throws CommandException {
        Person personToSchedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Schedule takenSchedule = new Schedule("2024-10-04 1000");

        // Schedule first person with the time slot
        Person scheduledPerson = new PersonBuilder(personToSchedule).withSchedule(takenSchedule.dateTime).build();
        model.setPerson(personToSchedule, scheduledPerson);

        // Try to schedule another person with the same time slot
        Person secondPerson = model.getFilteredPersonList().get(1);
        ScheduleCommand command = new ScheduleCommand(secondPerson.getName().toString(), takenSchedule);
        assertCommandFailure(command, model, ScheduleCommand.MESSAGE_SLOT_TAKEN);
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        ScheduleCommand command = new ScheduleCommand("Unknown Person", new Schedule("2024-10-05 1000"));

        assertCommandFailure(command, model, ScheduleCommand.MESSAGE_INVALID_NAME);
    }
    @Test
    public void execute_scheduleOnWeekend_throwsCommandException() {
        Person personToSchedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Schedule weekendSchedule = new Schedule("2024-10-06 1000"); // Saturday

        ScheduleCommand command = new ScheduleCommand(personToSchedule.getName().toString(), weekendSchedule);

        assertCommandFailure(command, model, ScheduleCommand.MESSAGE_INVALID_TIME);
    }

    @Test
    public void execute_scheduleBeforeWorkingHours_throwsCommandException() {
        Person personToSchedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Schedule earlySchedule = new Schedule("2024-10-04 0800"); // Before 9 AM

        ScheduleCommand command = new ScheduleCommand(personToSchedule.getName().toString(), earlySchedule);

        assertCommandFailure(command, model, ScheduleCommand.MESSAGE_INVALID_TIME);
    }

    @Test
    public void execute_scheduleAfterWorkingHours_throwsCommandException() {
        Person personToSchedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Schedule lateSchedule = new Schedule("2024-10-04 1800"); // After 5 PM

        ScheduleCommand command = new ScheduleCommand(personToSchedule.getName().toString(), lateSchedule);

        assertCommandFailure(command, model, ScheduleCommand.MESSAGE_INVALID_TIME);
    }

    @Test
    public void execute_scheduleNotOnTheHour_throwsCommandException() {
        Person personToSchedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Schedule notOnTheHourSchedule = new Schedule("2024-10-04 1015"); // Not on the hour

        ScheduleCommand command = new ScheduleCommand(personToSchedule.getName().toString(), notOnTheHourSchedule);

        assertCommandFailure(command, model, ScheduleCommand.MESSAGE_INVALID_TIME);
    }

}

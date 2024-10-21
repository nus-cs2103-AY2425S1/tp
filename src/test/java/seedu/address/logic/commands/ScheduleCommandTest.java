package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.Set;

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
        Set<Schedule> validSchedule = new HashSet<>();
        validSchedule.add(new Schedule("2024-10-04 1000", ""));

        ScheduleCommand command = new ScheduleCommand(personToSchedule.getName().toString(), validSchedule);

        Person scheduledPerson = new PersonBuilder(personToSchedule)
                .withSchedule(new String[]{"2024-10-04 1000"}, new String[]{""}).build();

        String expectedMessage = String.format(ScheduleCommand.MESSAGE_SUCCESS,
                validSchedule, personToSchedule.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToSchedule, scheduledPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTime_throwsCommandException() {
        Person personToSchedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Schedule> invalidSchedule = new HashSet<>();
        invalidSchedule.add(new Schedule("2024-10-05 1730", "")); // Outside working hours

        ScheduleCommand command = new ScheduleCommand(personToSchedule.getName().toString(), invalidSchedule);

        assertCommandFailure(command, model, ScheduleCommand.MESSAGE_INVALID_TIME);
    }

    @Test
    public void execute_slotTaken_throwsCommandException() throws CommandException {
        Person personToSchedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Schedule> takenSchedule = new HashSet<>();
        takenSchedule.add(new Schedule("2024-10-04 1000", ""));

        // Schedule first person with the time slot
        Person scheduledPerson = new PersonBuilder(personToSchedule)
                .withSchedule(new String[]{"2024-10-04 1000"}, new String[]{""}).build();
        model.setPerson(personToSchedule, scheduledPerson);

        // Try to schedule another person with the same time slot
        Person secondPerson = model.getFilteredPersonList().get(1);
        ScheduleCommand command = new ScheduleCommand(secondPerson.getName().toString(), takenSchedule);
        assertCommandFailure(command, model, ScheduleCommand.MESSAGE_SLOT_TAKEN);
    }

    @Test
    public void execute_personNotFound_throwsCommandException() {
        Set<Schedule> schedules = new HashSet<>();
        schedules.add(new Schedule("2024-10-04 1000", ""));
        ScheduleCommand command = new ScheduleCommand("Unknown Person", schedules);

        assertCommandFailure(command, model, ScheduleCommand.MESSAGE_INVALID_NAME);
    }
    @Test
    public void execute_scheduleOnWeekend_throwsCommandException() {
        Person personToSchedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Schedule> weekendSchedule = new HashSet<>();
        weekendSchedule.add(new Schedule("2024-10-06 1000", "")); // Saturday

        ScheduleCommand command = new ScheduleCommand(personToSchedule.getName().toString(), weekendSchedule);

        assertCommandFailure(command, model, ScheduleCommand.MESSAGE_INVALID_TIME);
    }

    @Test
    public void execute_scheduleBeforeWorkingHours_throwsCommandException() {
        Person personToSchedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Schedule> earlySchedule = new HashSet<>();
        earlySchedule.add(new Schedule("2024-10-04 0800", "")); // Before 9 AM

        ScheduleCommand command = new ScheduleCommand(personToSchedule.getName().toString(), earlySchedule);

        assertCommandFailure(command, model, ScheduleCommand.MESSAGE_INVALID_TIME);
    }

    @Test
    public void execute_scheduleAfterWorkingHours_throwsCommandException() {
        Person personToSchedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Schedule> lateSchedule = new HashSet<>();
        lateSchedule.add(new Schedule("2024-10-04 1800", "")); // After 5 PM

        ScheduleCommand command = new ScheduleCommand(personToSchedule.getName().toString(), lateSchedule);

        assertCommandFailure(command, model, ScheduleCommand.MESSAGE_INVALID_TIME);
    }

    @Test
    public void execute_scheduleNotOnTheHour_throwsCommandException() {
        Person personToSchedule = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Set<Schedule> notOnTheHourSchedule = new HashSet<>();
        notOnTheHourSchedule.add(new Schedule("2024-10-04 1015", "")); // Not on the hour

        ScheduleCommand command = new ScheduleCommand(personToSchedule.getName().toString(), notOnTheHourSchedule);

        assertCommandFailure(command, model, ScheduleCommand.MESSAGE_INVALID_TIME);
    }

    @Test
    public void equals() {
        Schedule schedule1 = new Schedule("2024-10-04 1000", "Meeting with client");
        Schedule schedule2 = new Schedule("2024-10-04 1000", "Meeting with client");
        Schedule schedule3 = new Schedule("2024-10-05 1000", "Lunch with team");
        Schedule schedule4 = new Schedule("2024-10-04 1100", "Meeting with client");

        // same values -> returns true
        assertTrue(schedule1.equals(schedule2));

        // same object -> returns true
        assertTrue(schedule1.equals(schedule1));

        // null -> returns false
        assertFalse(schedule1.equals(null));

        // different types -> returns false
        assertFalse(schedule1.equals(5));

        // different dateTime -> returns false
        assertFalse(schedule1.equals(schedule4));

        // different notes -> returns false
        assertFalse(schedule1.equals(schedule3));
    }
}

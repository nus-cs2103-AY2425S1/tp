package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.UnmarkAttendanceCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.attendance.AttendanceEvent;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class UnmarkAttendanceCommandTest {

    private Model model = new ModelManager();
    private UnmarkAttendanceCommandParser parser = new UnmarkAttendanceCommandParser();

    @Test
    public void execute_unmarkAttendanceSingleStudent_success() throws Exception {
        // Setup
        model.addAttendanceEvent(new AttendanceEvent("Event1"));
        Person student = new PersonBuilder().build();
        model.addPerson(student);
        // Mark attendance first
        model.markStudentAttendance("Event1", student.getStudentId(), true);

        String userInput = " e/Event1 i/1";
        UnmarkAttendanceCommand command = parser.parse(userInput);
        CommandResult result = command.execute(model);

        String expectedMessage = "Unmarked attendance for 1 in Event1";
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_unmarkAttendanceMultipleStudents_success() throws Exception {
        // Setup
        model.addAttendanceEvent(new AttendanceEvent("Event1"));
        Person student1 = TypicalPersons.ALICE;
        Person student2 = TypicalPersons.BOB;
        model.addPerson(student1);
        model.addPerson(student2);

        String userInput = " e/Event1 i/1 i/2";
        UnmarkAttendanceCommand command = parser.parse(userInput);
        CommandResult result = command.execute(model);

        String expectedMessage = "Unmarked attendance for 2 in Event1";
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_unmarkAttendanceNonExistingEvent_failure() throws Exception {
        String userInput = " e/NonExistingEvent i/1";
        UnmarkAttendanceCommand command = parser.parse(userInput);

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals("Attendance event 'NonExistingEvent' not found.", exception.getMessage());
    }

    @Test
    public void execute_unmarkAttendanceInvalidIndex_failure() throws Exception {
        // Setup
        model.addAttendanceEvent(new AttendanceEvent("Event1"));
        // No students added

        String userInput = " e/Event1 i/1";
        UnmarkAttendanceCommand command = parser.parse(userInput);

        CommandException exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(MarkAttendanceCommand.MESSAGE_INVALID_ATTENDANCE_INDEX, exception.getMessage());
    }

    @Test
    public void execute_unmarkAttendanceNonIntegerIndex_failure() {
        String userInput = " e/Event1 i/abc";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertEquals(MarkAttendanceCommand.MESSAGE_INVALID_ATTENDANCE_INDEX, exception.getMessage());
    }

    @Disabled
    public void execute_unmarkAttendanceMultipleEvents_failure() {
        String userInput = " e/Event1 e/Event2 i/1";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertEquals("Multiple values specified for the following single-valued field(s): e/", exception.getMessage());
    }

    @Test
    public void execute_unmarkAttendanceDuplicateIndices_failure() {
        String userInput = " e/Event1 i/1 i/1";
        ParseException exception = assertThrows(ParseException.class, () -> parser.parse(userInput));
        assertEquals("Duplicate indices detected. Please specify each student only once.", exception.getMessage());
    }
}

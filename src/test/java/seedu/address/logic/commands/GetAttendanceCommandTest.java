package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

class GetAttendanceCommandTest {

    private final Name validName = new Name("John Doe");
    private final Name invalidName = new Name("Unknown Person");

    private final LocalDate validDate = LocalDate.of(2023, 10, 9);
    private final LocalDate invalidDate = LocalDate.of(2024, 1, 1);

    private final Student studentWithAttendance = new StudentBuilder().withName("John Doe")
            .withAttendanceRecord(validDate, "p").build();

    private final Student studentWithoutAttendance = new StudentBuilder().withName("Jane Doe").build();

    @Test
    void execute_validStudentAndDate_success() throws CommandException {
        Model model = new ModelManager();
        studentWithAttendance.markAttendance(validDate, "p");
        model.addStudent(studentWithAttendance);

        GetAttendanceCommand command = new GetAttendanceCommand(validName, validDate);
        CommandResult result = command.execute(model);

        String expectedMessage = String.format(GetAttendanceCommand.MESSAGE_SUCCESS, validName, validDate, "Present");
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    void execute_validStudentInvalidDate_noAttendanceRecord() throws CommandException {
        Model model = new ModelManager();
        model.addStudent(studentWithAttendance);

        GetAttendanceCommand command = new GetAttendanceCommand(validName, invalidDate);
        CommandResult result = command.execute(model);

        String expectedMessage = String.format(GetAttendanceCommand.MESSAGE_NO_ATTENDANCE, validName, invalidDate);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    void execute_validStudentNoAttendanceRecord_noAttendanceRecord() throws CommandException {
        Model model = new ModelManager();
        model.addStudent(studentWithoutAttendance);

        GetAttendanceCommand command = new GetAttendanceCommand(new Name("Jane Doe"), validDate);
        CommandResult result = command.execute(model);

        String expectedMessage = String.format(GetAttendanceCommand.MESSAGE_NO_ATTENDANCE,
                new Name("Jane Doe"), validDate);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    void execute_invalidStudent_throwsCommandException() {
        Model model = new ModelManager();

        GetAttendanceCommand command = new GetAttendanceCommand(invalidName, validDate);

        assertThrows(CommandException.class, "Student not found: " + invalidName, () ->
                command.execute(model));
    }



}

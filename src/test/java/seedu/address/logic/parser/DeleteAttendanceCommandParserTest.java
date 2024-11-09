package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Name;
import seedu.address.model.student.StudentNumber;

public class DeleteAttendanceCommandParserTest {

    private final DeleteAttendanceCommandParser parser = new DeleteAttendanceCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        Name name = new Name("John Doe");
        LocalDate date = LocalDate.of(2023, 10, 9);
        StudentNumber studentNumber = new StudentNumber("A0123456L");

        DeleteAttendanceCommand expectedCommand = new DeleteAttendanceCommand(name, date, Optional.of(studentNumber));

        String userInput = " " + PREFIX_NAME + "John Doe " + PREFIX_DATE + "2023-10-09 " + PREFIX_STUDENT_NUMBER + "A0123456L";
        DeleteAttendanceCommand command = parser.parse(userInput);

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_optionalFieldMissing_success() throws Exception {
        Name name = new Name("John Doe");
        LocalDate date = LocalDate.of(2023, 10, 9);

        DeleteAttendanceCommand expectedCommand = new DeleteAttendanceCommand(name, date, Optional.empty());

        String userInput = " " + PREFIX_NAME + "John Doe " + PREFIX_DATE + "2023-10-09";
        DeleteAttendanceCommand command = parser.parse(userInput);

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_compulsoryFieldMissing_throwsParseException() {
        String userInput = " " + PREFIX_NAME + "John Doe";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidValue_throwsParseException() {
        String userInput = " " + PREFIX_NAME + "John Doe " + PREFIX_DATE + "invalid-date";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
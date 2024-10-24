package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddStudentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class AddStudentCommandParserTest {

    private final AddStudentCommandParser parser = new AddStudentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        Student expectedStudent = new StudentBuilder().withName("John Doe").withPhone("12345678")
                .withTutorialGroup("T01").withStudentNumber("A1234567X").build_default();

        AddStudentCommand command = parser.parse(" " + PREFIX_NAME + "John Doe " + PREFIX_PHONE + "12345678 "
                + PREFIX_TUTORIAL_GROUP + "T01 " + PREFIX_STUDENT_NUMBER + "A1234567X");

        assertEquals(new AddStudentCommand(expectedStudent), command);
    }

    @Test
    public void parse_missingRequiredField_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" " + PREFIX_NAME + "John Doe "
                + PREFIX_PHONE + "12345678 " + PREFIX_TUTORIAL_GROUP + "T01"));
    }

    @Test
    public void parse_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" " + PREFIX_NAME + "John Doe " + PREFIX_PHONE
                + "invalidPhone " + PREFIX_TUTORIAL_GROUP + "T01 " + PREFIX_STUDENT_NUMBER + "A1234567X"));
    }
}

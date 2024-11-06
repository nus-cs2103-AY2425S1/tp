package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.deletecommands.DeleteStudentFromGroupCommand;
import seedu.address.logic.parser.deletecommands.DeleteStudentFromGroupCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.StudentNumber;


public class DeleteStudentFromGroupCommandParserTest {

    private final DeleteStudentFromGroupCommandParser parser = new DeleteStudentFromGroupCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteStudentFromGroupCommand() throws Exception {
        String input = " " + PREFIX_STUDENT_NUMBER + "A0123456Z";
        DeleteStudentFromGroupCommand expectedCommand = new DeleteStudentFromGroupCommand(
                new StudentNumber("A0123456Z"));

        DeleteStudentFromGroupCommand command = parser.parse(input);
        assertEquals(expectedCommand.toString(), command.toString());
    }

    @Test
    public void parse_missingStudentNumber_throwsParseException() {
        String input = " " + PREFIX_GROUP_NAME + "CS2103-F12-4";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_invalidPreamble_throwsParseException() {
        String input = "randomPreamble " + PREFIX_GROUP_NAME + "CS2103-F12-4 " + PREFIX_STUDENT_NUMBER + "A0123456Z";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        String input = " " + PREFIX_GROUP_NAME + "CS2103-F12-4 " + PREFIX_GROUP_NAME + "CS2103-F12-3 "
                + PREFIX_STUDENT_NUMBER + "A0123456Z";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_emptyArgument_throwsParseException() {
        String input = " ";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }
}

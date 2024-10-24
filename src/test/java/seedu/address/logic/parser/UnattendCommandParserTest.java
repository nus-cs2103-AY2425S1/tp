package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCEDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALID;
import static seedu.address.testutil.Assert.assertThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnattendCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;

public class UnattendCommandParserTest {

    private final UnattendCommandParser parser = new UnattendCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String userInput = " " + PREFIX_STUDENTID + "1001 "
                + PREFIX_TUTORIALID + "1001 "
                + PREFIX_ATTENDANCEDATE + "2024/02/21";

        UnattendCommand command = parser.parse(userInput);

        StudentId expectedStudentId = new StudentId("1001");
        TutorialId expectedTutorialId = TutorialId.of("1001");
        Date expectedDate = new SimpleDateFormat("yyyy/MM/dd").parse("2024/02/21");

        UnattendCommand expectedCommand = new UnattendCommand(expectedStudentId, expectedTutorialId, expectedDate);

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_missingStudentId_failure() {
        String userInput = " " + PREFIX_TUTORIALID + "1001 "
                + PREFIX_ATTENDANCEDATE + "2024/02/21";

        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnattendCommand.MESSAGE_USAGE), () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingTutorialClass_failure() {
        String userInput = " " + PREFIX_STUDENTID + "1001 "
                + PREFIX_ATTENDANCEDATE + "2024/02/21";

        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnattendCommand.MESSAGE_USAGE), () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidStudentId_failure() {
        String userInput = " " + PREFIX_STUDENTID + "invalid_id "
                + PREFIX_TUTORIALID + "1001 "
                + PREFIX_ATTENDANCEDATE + "2024/02/21";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidDate_failure() {
        String userInput = " " + PREFIX_STUDENTID + "1001 "
                + PREFIX_TUTORIALID + "1001 "
                + PREFIX_ATTENDANCEDATE + "invalid_date";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String userInput = " " + PREFIX_STUDENTID + "1001 "
                + PREFIX_STUDENTID + "1002 "
                + PREFIX_TUTORIALID + "1001 "
                + PREFIX_ATTENDANCEDATE + "2024/02/21";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_extraArguments_failure() {
        String userInput = " extra_arg "
                + PREFIX_STUDENTID + "1001 "
                + PREFIX_TUTORIALID + "1001 "
                + PREFIX_ATTENDANCEDATE + "2024/02/21";

        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnattendCommand.MESSAGE_USAGE), () -> parser.parse(userInput));
    }
}

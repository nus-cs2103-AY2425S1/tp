package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteAttendanceAllCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.TutorialGroup;

public class DeleteAttendanceAllCommandParserTest {

    private final DeleteAttendanceAllCommandParser parser = new DeleteAttendanceAllCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        TutorialGroup tutorialGroup = new TutorialGroup("G04");
        LocalDate date = LocalDate.of(2023, 10, 9);

        DeleteAttendanceAllCommand expectedCommand = new DeleteAttendanceAllCommand(tutorialGroup, date);

        String userInput = " " + PREFIX_TUTORIAL_GROUP + "G04 " + PREFIX_DATE + "2023-10-09";
        DeleteAttendanceAllCommand command = parser.parse(userInput);

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_compulsoryFieldMissing_throwsParseException() {
        String userInput = " " + PREFIX_TUTORIAL_GROUP + "G04";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidValue_throwsParseException() {
        String userInput = " " + PREFIX_TUTORIAL_GROUP + "G04 " + PREFIX_DATE + "invalid-date";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}

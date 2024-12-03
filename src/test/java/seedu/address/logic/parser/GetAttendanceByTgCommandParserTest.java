package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GetAttendanceByTgCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.TutorialGroup;

public class GetAttendanceByTgCommandParserTest {

    private final GetAttendanceByTgCommandParser parser = new GetAttendanceByTgCommandParser();

    @Test
    public void parse_validArgs_returnsGetAttendanceByTgCommand() throws Exception {
        String userInput = " tg/A01";
        GetAttendanceByTgCommand expectedCommand = new GetAttendanceByTgCommand(new TutorialGroup("A01"));
        GetAttendanceByTgCommand command = parser.parse(userInput);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_missingTutorialGroup_throwsParseException() {
        String userInput = " ";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidTutorialGroup_throwsParseException() {
        String userInput = " tg/";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}

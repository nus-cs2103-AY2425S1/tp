package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.parser.CliSyntax.PREFIX_DATE;
import static keycontacts.logic.parser.CliSyntax.PREFIX_END_TIME;
import static keycontacts.logic.parser.CliSyntax.PREFIX_START_TIME;
import static keycontacts.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import keycontacts.commons.core.index.Index;
import keycontacts.logic.commands.MakeupLessonCommand;
import keycontacts.logic.parser.exceptions.ParseException;
import keycontacts.model.lesson.Date;
import keycontacts.model.lesson.Lesson;
import keycontacts.model.lesson.MakeupLesson;
import keycontacts.model.lesson.Time;

public class MakeupCommandParserTest {

    private MakeupCommandParser parser = new MakeupCommandParser();

    @Test
    public void parse_validArgs_returnsMakeupLessonCommand() throws Exception {
        String userInput = "1 " + PREFIX_DATE + "08-01-2025 "
                + PREFIX_START_TIME + "14:00 " + PREFIX_END_TIME + "15:00";
        MakeupLessonCommand command = parser.parse(userInput);

        Index expectedIndex = Index.fromOneBased(1);
        Date expectedDate = new Date("08-01-2025");
        Time expectedStartTime = new Time("14:00");
        Time expectedEndTime = new Time("15:00");
        MakeupLesson expectedMakeupLesson = new MakeupLesson(expectedDate, expectedStartTime, expectedEndTime);
        MakeupLessonCommand expectedCommand = new MakeupLessonCommand(expectedIndex, expectedMakeupLesson);

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        String userInput = PREFIX_DATE + "08-01-2025 "
                + PREFIX_START_TIME + "14:00 " + PREFIX_END_TIME + "15:00";
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MakeupLessonCommand.MESSAGE_USAGE), () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingDatePrefix_throwsParseException() {
        String userInput = "1 " + PREFIX_START_TIME + "14:00 " + PREFIX_END_TIME + "15:00";
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MakeupLessonCommand.MESSAGE_USAGE), () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingStartTimePrefix_throwsParseException() {
        String userInput = "1 " + PREFIX_DATE + "08-01-2025 " + PREFIX_END_TIME + "15:00";
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MakeupLessonCommand.MESSAGE_USAGE), () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingEndTimePrefix_throwsParseException() {
        String userInput = "1 " + PREFIX_DATE + "08-01-2025 " + PREFIX_START_TIME + "14:00";
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MakeupLessonCommand.MESSAGE_USAGE), () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "a " + PREFIX_DATE + "08-01-2025 "
                + PREFIX_START_TIME + "14:00 " + PREFIX_END_TIME + "15:00";
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MakeupLessonCommand.MESSAGE_USAGE), () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidDate_throwsParseException() {
        String userInput = "1 " + PREFIX_DATE + "invalid-date "
                + PREFIX_START_TIME + "14:00 " + PREFIX_END_TIME + "15:00";
        assertThrows(ParseException.class, Date.MESSAGE_CONSTRAINTS, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidStartTime_throwsParseException() {
        String userInput = "1 " + PREFIX_DATE + "08-01-2025 "
                + PREFIX_START_TIME + "invalid-time " + PREFIX_END_TIME + "15:00";
        assertThrows(ParseException.class, Time.MESSAGE_CONSTRAINTS, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidEndTime_throwsParseException() {
        String userInput = "1 " + PREFIX_DATE + "08-01-2025 "
                + PREFIX_START_TIME + "14:00 " + PREFIX_END_TIME + "invalid-time";
        assertThrows(ParseException.class, Time.MESSAGE_CONSTRAINTS, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidTimePair_throwsParseException() {
        String userInput = "1 " + PREFIX_DATE + "08-01-2025 "
                + PREFIX_START_TIME + "15:00 " + PREFIX_END_TIME + "14:00";
        assertThrows(ParseException.class, Lesson.MESSAGE_CONSTRAINTS, () -> parser.parse(userInput));
    }

}

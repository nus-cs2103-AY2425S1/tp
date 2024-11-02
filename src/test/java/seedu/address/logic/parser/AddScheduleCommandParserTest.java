package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.AddScheduleCommand.MESSAGE_INVALID_DATE;
import static seedu.address.logic.commands.AddScheduleCommand.MESSAGE_INVALID_TIME;
import static seedu.address.logic.commands.AddScheduleCommand.MESSAGE_USAGE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class AddScheduleCommandParserTest {

    private final AddScheduleCommandParser parser = new AddScheduleCommandParser();

    @Test
    public void parse_missingContactIndex_throwsParseException() {
        String userInput = " n/Team Meeting d/10-10-2024 t/1800";
        assertThrows(ParseException.class, () -> parser.parse(userInput), MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        String userInput = " c/1 n/Team Meeting d/31/12/2024 t/1800"; // Invalid date format
        assertThrows(ParseException.class, () -> parser.parse(userInput), MESSAGE_INVALID_DATE);
    }

    @Test
    public void parse_invalidTimeFormat_throwsParseException() {
        String userInput = " c/1 n/Team Meeting d/10-10-2024 t/9 PM"; // Invalid time format
        assertThrows(ParseException.class, () -> parser.parse(userInput), MESSAGE_INVALID_TIME);
    }

    @Test
    public void parse_missingDate_throwsParseException() {
        String userInput = " c/1 n/Team Meeting t/1800"; // Missing date
        assertThrows(ParseException.class, () -> parser.parse(userInput), MESSAGE_USAGE);
    }

    @Test
    public void parse_missingTime_throwsParseException() {
        String userInput = " c/1 n/Team Meeting d/10-10-2024"; // Missing time
        assertThrows(ParseException.class, () -> parser.parse(userInput), MESSAGE_USAGE);
    }

    @Test
    public void parse_missingName_throwsParseException() {
        String userInput = " c/1 d/10-10-2024 t/1800"; // Missing name
        assertThrows(ParseException.class, () -> parser.parse(userInput), MESSAGE_USAGE);
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        String userInput = ""; // Empty input
        assertThrows(ParseException.class, () -> parser.parse(userInput), MESSAGE_USAGE);
    }
}

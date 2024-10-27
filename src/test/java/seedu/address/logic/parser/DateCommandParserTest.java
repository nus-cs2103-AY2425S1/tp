package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.time.LocalDateTime;


public class DateCommandParserTest {
    private DateCommandParser parser = new DateCommandParser();
    private final String nonEmptyDate = "Some date.";

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, DateCommand.COMMAND_WORD, expectedMessage);
        // no index
        assertParseFailure(parser, DateCommand.COMMAND_WORD + " " + nonEmptyDate, expectedMessage);
    }

    // Valid date parsing test
    @Test
    public void parseDateTime_validDate_returnsLocalDateTime() throws ParseException {
        assertEquals(LocalDateTime.of(2024, 2, 29, 18, 0)
                , parser.parseDateString("29/2/2024 1800"));
    }

    // Leap year checks
    @Test
    public void parseDateTime_february29LeapYear_returnsLocalDateTime() throws ParseException {
        assertEquals(LocalDateTime.of(2024, 2, 29, 0, 0),
                parser.parseDateString("29/2/2024 0000"));
    }

    @Test
    public void parseDateTime_february29NonLeapYear_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parseDateString("29/2/2023 0000"),
                "Invalid date: February 29 is only valid in leap years.");
    }

    // Month-end validations
    @Test
    public void parseDateTime_invalid31stApril_throwsParseException() {
        ParseException thrown = assertThrows(ParseException.class,
                () -> parser.parseDateString("31/4/2024 1200"));
        assertEquals("Invalid date: APRIL cannot have more than 30 days.", thrown.getMessage());
    }

    @Test
    public void parseDateTime_invalid31stJune_throwsParseException() {
        ParseException thrown = assertThrows(ParseException.class,
                () -> parser.parseDateString("31/6/2024 1200"));
        assertEquals("Invalid date: JUNE cannot have more than 30 days.", thrown.getMessage());
    }

    // Invalid formats
    @Test
    public void parseDateTime_invalidFormat_throwsParseException() {
        ParseException thrown = assertThrows(ParseException.class,
                () -> parser.parseDateString("12-31-2024 1200"));
        assertEquals("Invalid date format! Please use 'd/M/yyyy HHmm'. "
                        + "For example, '2/12/2024 1800'.",
                thrown.getMessage());
    }


    @Test
    public void parseDateTime_outOfRangeDayMonth_throwsParseException() {
        ParseException thrown = assertThrows(ParseException.class,
                () -> parser.parseDateString("32/1/2024 1200"));
        assertEquals("Invalid date or time values! Ensure day, month, hour, " +
                "and minute ranges are correct.", thrown.getMessage());
    }
}

package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Date;
import seedu.address.model.person.SchedulePredicate;

public class ScheduleCommandParserTest {
    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsScheduleCommand() {
        // no leading and trailing whitespaces
        ScheduleCommand expectedScheduleCommand =
              new ScheduleCommand(new SchedulePredicate(
                      new Date(LocalDateTime.of(2024, 2, 16, 18, 45))));
        assertParseSuccess(parser, " d/ 16/2/2024 1845", expectedScheduleCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n d/ 16/2/2024 1845 \n \t", expectedScheduleCommand);

        //empty preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " \n d/ 16/2/2024 1845 \n \t",
              expectedScheduleCommand);
    }

    @Test
    public void parse_invalidArgs_returnsScheduleCommand() {
        //invalid preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " d/ 16 February 2024",
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        // multiple dates inputted
        assertParseFailure(parser, " d/ 16 February 2024 d/ 17 February 2024",
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));

        // invalid prefix syntax for date
        assertParseFailure(parser, " t/High Risk",
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }

    // Invalid date format - missing time
    @Test
    void parse_invalidDateFormat_missingTime_throwsParseException() {
        String invalidDate = "2/12/2024";
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(" d/" + invalidDate));
        assertEquals("Invalid date format! Please use 'd/M/yyyy HHmm'. For example, '2/12/2024 1800'.",
                thrown.getMessage());
    }

    // Invalid date format - incorrect delimiter
    @Test
    void parse_invalidDateFormat_wrongDelimiter_throwsParseException() {
        String invalidDate = "2-12-2024 1800";
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(" d/" + invalidDate));
        assertEquals("Invalid date format! Please use 'd/M/yyyy HHmm'. For example, '2/12/2024 1800'.",
                thrown.getMessage());
    }

    // Invalid date values - leap year check
    @Test
    void parse_invalidDateValues_nonLeapYear_throwsParseException() {
        String invalidDate = "29/2/2023 1800";  // 2023 is not a leap year
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(" d/" + invalidDate));
        assertEquals("Invalid date: February 29 is only valid in leap years.", thrown.getMessage());
    }

    // Invalid date values - month with 30 days
    @Test
    void parse_invalidDateValues_monthWith30Days_throwsParseException() {
        String invalidDate = "31/4/2024 1800";  // April has only 30 days
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(" d/" + invalidDate));
        assertEquals("Invalid date: APRIL cannot have more than 30 days.", thrown.getMessage());
    }

    // Invalid time format
    @Test
    void parse_invalidTimeFormat_throwsParseException() {
        String invalidTime = "2/12/2024 18:00";  // ':' should not be present
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(" d/" + invalidTime));
        assertEquals("Invalid date format! Please use 'd/M/yyyy HHmm'. For example, '2/12/2024 1800'.", thrown.getMessage());
    }

    // Multiple date inputs
    @Test
    void parse_multipleDateInputs_throwsParseException() {
        String multipleDates = " d/2/12/2024 1800 d/3/12/2024 1400";
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(multipleDates));
        assertTrue(thrown.getMessage().contains("Invalid command format"));
    }
    
}

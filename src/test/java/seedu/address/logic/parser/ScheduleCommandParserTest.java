package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
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
                      new Date(LocalDateTime.of(2024, 2, 16, 0, 0))));
        assertParseSuccess(parser, " d/ 16/2/2024", expectedScheduleCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n d/ 16/2/2024 \n \t", expectedScheduleCommand);

        //empty preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " \n d/ 16/2/2024 \n \t",
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

    // Invalid date format - incorrect delimiter
    @Test
    void parseInvalidDateFormatWrongDelimiter_throwsParseException() {
        String invalidDate = "2-12-2024 1800";
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(" d/" + invalidDate));
        assertEquals("Invalid date characters detected! Only numbers and '/' are allowed.",
                thrown.getMessage());
    }

    // Invalid date values - leap year check
    @Test
    void parseInvalidDateValuesNonLeapYear_throwsParseException() {
        String invalidDate = "29/2/2023"; // 2023 is not a leap year
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(" d/" + invalidDate));
        assertEquals("Invalid date: FEBRUARY 29 is only valid in leap years.", thrown.getMessage());
    }

    // Invalid date values - month with 30 days
    @Test
    void parse_invalidDateValues_throwsParseException() {
        String invalidDate = "31/4/2024"; // April has only 30 days
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(" d/" + invalidDate));
        assertEquals("Invalid date: APRIL cannot have more than 30 days.", thrown.getMessage());

        String invalidDateTemp = "32/8/2024"; // April has only 30 days
        ParseException thrownTemp =
                assertThrows(ParseException.class, () -> parser.parse(" d/" + invalidDateTemp));
        assertEquals("Invalid date values! Ensure day and month ranges are correct.",
                thrownTemp.getMessage());

        String invalidDateFeb = "31/2/2024"; // February has only 29 days
        ParseException thrownFeb = assertThrows(ParseException.class, () -> parser.parse(" d/" + invalidDateFeb));
        assertEquals("Invalid date: FEBRUARY cannot have more than 29 days.", thrownFeb.getMessage());
    }



    // Invalid format time is given
    @Test
    void parse_invalidTimeFormat_throwsParseException() {
        String invalidTime = "2/12/2024 1800"; // time is not allowed for schedule feature
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(" d/" + invalidTime));
        assertEquals("Invalid date format! Please use 'd/M/yyyy'. For example, '2/12/2024'.",
                thrown.getMessage());
    }

    // Multiple date inputs
    @Test
    void parse_multipleDateInputs_throwsParseException() {
        String multipleDates = " d/2/12/2024 1800 d/3/12/2024 1400";
        ParseException thrown = assertThrows(ParseException.class, () -> parser.parse(multipleDates));
        assertTrue(thrown.getMessage().contains("Invalid command format"));
    }

    @Test
    public void parse_invalidPrefix_failure() {
        assertParseFailure(parser, " d/ 16/2/2024 t/High Risk",
              String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }

}

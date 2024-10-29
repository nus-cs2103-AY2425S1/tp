package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.DATE_INVALID_INDEX;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ScheduleDateCommand;
import seedu.address.model.appointment.AppointmentContainsDatePredicate;


public class ScheduleDateCommandParserTest {

    private ScheduleDateCommandParser parser = new ScheduleDateCommandParser();

    @Test
    public void parse_validArgs_returnsScheduleDateCommand() {
        // Test with valid date format
        LocalDate expectedDate = LocalDate.of(2024, 10, 12);
        AppointmentContainsDatePredicate predicate = new AppointmentContainsDatePredicate(expectedDate);
        assertParseSuccess(parser, "12-10-2024", new ScheduleDateCommand(predicate));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test with invalid date format
        assertParseFailure(parser, "2024-10-12",
                DATE_INVALID_INDEX + "\nInput causing the error: " + "2024-10-12");

        // Test with completely invalid input
        assertParseFailure(parser, "invalidDate",
                DATE_INVALID_INDEX + "\nInput causing the error: " + "invalidDate");

        // Test with missing input
        assertParseFailure(parser, "", DATE_INVALID_INDEX + "\nInput causing the error: ");
    }
}


package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindAppointmentCommand;
import seedu.address.logic.parser.criteria.AppointmentSearchCriteria;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ContainsKeywordsPredicate;

public class FindAppointmentCommandParserTest {

    private FindAppointmentCommandParser parser = new FindAppointmentCommandParser();

    @Test
    public void parse_allValidPrefixes_success() throws ParseException {
        // Test Case: All required prefixes are present and valid
        String userInput = " sdate/30/10/2024 start/14:00 edate/30/10/2024 end/15:00";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        FindAppointmentCommand expectedCommand = new FindAppointmentCommand(
                new ContainsKeywordsPredicate(
                        List.of(new AppointmentSearchCriteria(
                                LocalDate.parse("30/10/2024", dateFormatter),
                                LocalTime.parse("14:00", timeFormatter),
                                LocalDate.parse("30/10/2024", dateFormatter),
                                LocalTime.parse("15:00", timeFormatter)
                        ))
                )
        );

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingStartDate_failure() {
        // Test Case: Missing Start Date
        String userInput = " start/14:00 enddate/30/10/2024 end/15:00";
        assertParseFailure(parser, userInput, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FindAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingEndDate_failure() {
        // Test Case: Missing End Date
        String userInput = " sdate/30/10/2024 start/14:00 end/15:00";
        assertParseFailure(parser, userInput, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, FindAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDateFormat_failure() {
        // Test Case: Invalid Date Format
        String userInput = " sdate/30-10-2024 start/14:00 edate/31-10-2024 end/15:00";
        assertParseFailure(parser, userInput, FindAppointmentCommand.MESSAGE_INVALID_DATE);
    }

    @Test
    public void parse_invalidTimeFormat_failure() {
        // Test Case: Invalid Time Format
        String userInput = " sdate/30/10/2024 start/14:60 edate/30/10/2024 end/15:00";
        assertParseFailure(parser, userInput, FindAppointmentCommand.MESSAGE_INVALID_TIME);
    }

    @Test
    public void parse_extraPreamble_failure() {
        // Test Case: Extra preamble in input
        String userInput = "randomtext sdate/30/10/2024 start/14:00 edate/30/10/2024 end/15:00";
        assertParseFailure(parser, userInput,
                "Please do not enter anything before the keywords!\nPlease remove this from your input: randomtext");
    }

    @Test
    public void parse_noKeywords_failure() {
        // Test Case: No keywords provided
        String userInput = "";
        assertParseFailure(parser, userInput,
                "Please enter at least one keyword!\n" + FindAppointmentCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_extraWhitespace_success() throws ParseException {
        // Test Case: Extra whitespace around valid input
        String userInput = " \n sdate/30/10/2024 \n \t start/14:00 \t edate/30/10/2024 end/15:00  ";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        FindAppointmentCommand expectedCommand = new FindAppointmentCommand(
                new ContainsKeywordsPredicate(
                        List.of(new AppointmentSearchCriteria(
                                LocalDate.parse("30/10/2024", dateFormatter),
                                LocalTime.parse("14:00", timeFormatter),
                                LocalDate.parse("30/10/2024", dateFormatter),
                                LocalTime.parse("15:00", timeFormatter)
                        ))
                )
        );

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        // Test Case: Duplicate prefixes provided
        String userInput = " sdate/30/10/2024 sdate/31/10/2024 start/14:00 edate/30/10/2024 end/15:00";
        assertParseFailure(parser, userInput,
                "Multiple values specified for the following single-valued field(s): sdate/");
    }
}

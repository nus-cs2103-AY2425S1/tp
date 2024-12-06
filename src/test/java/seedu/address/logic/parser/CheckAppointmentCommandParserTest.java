package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_DATE_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.ID_FIRST_PERSON;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CheckAppointmentCommand;

/**
 * Asserts parsing behavior for AddAppointmentCommandParser.
 */
public class CheckAppointmentCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, CheckAppointmentCommand.MESSAGE_USAGE);
    private final CheckAppointmentCommandParser parser = new CheckAppointmentCommandParser();

    // 1. All valid input
    @Test
    public void parse_allFieldsPresent_success() {

        String userInput = ID_DESC_FIRST + VALID_APPOINTMENT_DATE_DESC;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        CheckAppointmentCommand expectedCommand = new CheckAppointmentCommand(ID_FIRST_PERSON, LocalDate.parse(
                VALID_APPOINTMENT_DATE, formatter));
        System.out.println(userInput);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    // 2. Input is only checkA
    @Test
    public void parse_commandWordOnly_failure() {
        String userInput = "checkA";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    // 3. id field is empty
    @Test
    public void parse_missingIds_failure() {
        String userInput = VALID_APPOINTMENT_DATE_DESC;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    // 4. date y/ is empty
    @Test
    public void parse_missingDate_failure() {
        String userInput = ID_DESC_FIRST;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    // 4. date is in the wrong format
    @Test
    public void parse_dateWrongFormat_failure() {
        String userInput = ID_DESC_FIRST + INVALID_APPOINTMENT_DATE_DESC;
        assertParseFailure(parser, userInput, "Invalid date format. Please use yyyy-MM-dd.");
    }

    // 4. id is in the wrong format
    @Test
    public void parse_idWrongFormat_failure() {
        String userInput = INVALID_ID_DESC + INVALID_APPOINTMENT_DATE_DESC;
        assertParseFailure(parser, userInput, "Invalid ID entered! Check the ID that you have entered! "
                + "Make sure the ID is valid!");
    }
}

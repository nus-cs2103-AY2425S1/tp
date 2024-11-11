package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TIME_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TIME_FIRST_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.ID_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.ID_SECOND_PERSON;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddAppointmentCommand;

/**
 * Asserts parsing behavior for AddAppointmentCommandParser.
 */
public class AddAppointmentCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);
    private final AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    // 1. All valid input
    @Test
    public void parse_allFieldsPresent_success() {

        String userInput = ID_DESC_FIRST + ID_DESC_SECOND + VALID_APPOINTMENT_TIME_FIRST_DESC
                + VALID_REMARK_DESC;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        AddAppointmentCommand expectedCommand = new AddAppointmentCommand(LocalDateTime.parse(
                VALID_APPOINTMENT_TIME_FIRST, formatter), ID_FIRST_PERSON, ID_SECOND_PERSON,
                 VALID_REMARK);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    // 2. Input is only addA
    @Test
    public void parse_commandWordOnly_failure() {
        String userInput = "addA";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    // 3. Id fields are empty
    @Test
    public void parse_missingIds_failure() {
        String userInput = VALID_APPOINTMENT_TIME_FIRST_DESC + VALID_REMARK_DESC;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    // 4. Valid ids entered but appointment time is empty
    @Test
    public void parse_missingDate_failure() {
        String userInput = ID_DESC_FIRST + ID_DESC_SECOND + VALID_REMARK_DESC;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    // 5. Invalid appointment time
    @Test
    public void parse_invalidTime_failure() {

        String userInput = ID_DESC_FIRST + ID_DESC_SECOND + INVALID_APPOINTMENT_TIME_DESC
                + VALID_REMARK_DESC;

        assertParseFailure(parser, userInput, "Invalid date-time format, please use yyyy-MM-dd HH:mm.");
    }

    // 6. Invalid id
    @Test
    public void parse_invalidId_failure() {

        String userInput = INVALID_ID_DESC + ID_DESC_SECOND + INVALID_APPOINTMENT_TIME_DESC + VALID_REMARK_DESC;

        assertParseFailure(parser, userInput, "Invalid ID entered! Check the ID that you have entered! "
                + "Make sure the ID is valid!");
    }

}

package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TIME_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_TIME_FIRST_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.ID_FIRST_PERSON;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ViewHistoryCommand;

/**
 * Asserts parsing behavior for ViewHistoryCommandParserTest.
 */
public class ViewHistoryCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ViewHistoryCommand.MESSAGE_USAGE);
    private final ViewHistoryCommandParser parser = new ViewHistoryCommandParser();

    // 1. All valid input
    @Test
    public void parse_allFieldsPresent_success() {

        String userInput = ID_DESC_FIRST + VALID_APPOINTMENT_TIME_FIRST_DESC;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        ViewHistoryCommand expectedCommand = new ViewHistoryCommand(ID_FIRST_PERSON, LocalDateTime.parse(
                VALID_APPOINTMENT_TIME_FIRST, formatter));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    // 2. Input is only view
    @Test
    public void parse_commandWordOnly_failure() {
        String userInput = "view";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    // 3. Id field is empty
    @Test
    public void parse_missingIds_failure() {
        String userInput = VALID_APPOINTMENT_TIME_FIRST_DESC;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    // 4. Valid id entered but appointment time is empty
    @Test
    public void parse_missingDate_failure() {
        String userInput = ID_DESC_FIRST;
        ViewHistoryCommand expectedCommand = new ViewHistoryCommand(ID_FIRST_PERSON, null);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    // 5. Invalid appointment time
    @Test
    public void parse_invalidTime_failure() {

        String userInput = ID_DESC_SECOND + INVALID_APPOINTMENT_TIME_DESC;

        assertParseFailure(parser, userInput, "Invalid date-time format, please use yyyy-MM-dd HH:mm.");
    }

    // 5. Invalid id
    @Test
    public void parse_invalidId_failure() {

        String userInput = INVALID_ID_DESC + INVALID_APPOINTMENT_TIME_DESC;

        assertParseFailure(parser, userInput, "Invalid ID entered! Check the ID that you have entered! "
                + "Make sure the ID is valid!");
    }

}


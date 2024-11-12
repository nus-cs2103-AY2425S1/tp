package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.IDENTITY_NUMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IDENTITY_NUMBER_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddLogEntryCommand;
import seedu.address.model.log.AppointmentDate;
import seedu.address.model.person.IdentityNumber;

public class AddLogEntryCommandParserTest {

    private final AddLogEntryCommandParser parser = new AddLogEntryCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // Create expected AddLogEntryCommand with correct fields
        IdentityNumber expectedIdentityNumber = new IdentityNumber(VALID_IDENTITY_NUMBER_AMY);
        AppointmentDate expectedDate = new AppointmentDate("22 May 2024");

        // Expected command object
        AddLogEntryCommand expectedCommand = new AddLogEntryCommand(expectedIdentityNumber, expectedDate);

        // Parse successfully using all required fields and verify with expected result
        assertParseSuccess(parser, IDENTITY_NUMBER_DESC_AMY + " d/22 May 2024", expectedCommand);
    }

    @Test
    public void parse_missingFields_failure() {
        // Missing identity number prefix
        assertParseFailure(parser, "d/22 May 2024",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLogEntryCommand.MESSAGE_USAGE));

        // Missing date prefix
        assertParseFailure(parser, IDENTITY_NUMBER_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLogEntryCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIdentityNumber_failure() {
        // Identity number in incorrect format
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLogEntryCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "i/S123Z d/22 May 2024", expectedMessage);
    }

    @Test
    public void parse_invalidDateFormat_failure() {
        // Date in incorrect format
        assertParseFailure(parser, IDENTITY_NUMBER_DESC_AMY + " d/22/05/2024",
                AddLogEntryCommandParser.MESSAGE_INVALID_DATE);
    }
}

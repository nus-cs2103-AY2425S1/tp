package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.IDENTITY_NUMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.IDENTITY_NUMBER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IDENTITY_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IDENTITY_NUMBER_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListLogsCommand;
import seedu.address.model.person.IdentityNumber;

public class ListLogsParserTest {

    private ListLogsParser parser = new ListLogsParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListLogsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIdentityNumberLength_throwsParseException() {
        // Input too short/long
        assertParseFailure(parser, PREAMBLE_WHITESPACE + "i/S123",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListLogsCommand.MESSAGE_USAGE));
        assertParseFailure(parser, PREAMBLE_WHITESPACE + "i/S12345678901",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListLogsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        // Missing prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE + "S1234567D",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListLogsCommand.MESSAGE_USAGE));
    }


    @Test
    public void parse_multipleIdentityNumber_throwsParseException() {
        // Multiple identity numbers entered
        assertParseFailure(parser, PREAMBLE_WHITESPACE + "i/S8642950J i/S0894499G",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListLogsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsListLogsCommand() {
        // Single valid identity number without additional spaces for Amy
        IdentityNumber identityNumberAmy = new IdentityNumber(VALID_IDENTITY_NUMBER_AMY);
        ListLogsCommand expectedCommandAmy = new ListLogsCommand(identityNumberAmy);
        assertParseSuccess(parser, IDENTITY_NUMBER_DESC_AMY, expectedCommandAmy);
        assertParseSuccess(parser, "    " + IDENTITY_NUMBER_DESC_AMY + "   ", expectedCommandAmy);

        // Single valid identity number without additional spaces for Bob
        IdentityNumber identityNumberBob = new IdentityNumber(VALID_IDENTITY_NUMBER_BOB);
        ListLogsCommand expectedCommandBob = new ListLogsCommand(identityNumberBob);
        assertParseSuccess(parser, IDENTITY_NUMBER_DESC_BOB, expectedCommandBob);
        assertParseSuccess(parser, "    " + IDENTITY_NUMBER_DESC_BOB + "   ", expectedCommandBob);
    }

}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMERGENCY_PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMERGENCY_PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMERGENCY_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_PHONE_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EmergencyPhoneCommand;
import seedu.address.model.person.EmergencyPhone;

public class EmergencyPhoneCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmergencyPhoneCommand.MESSAGE_USAGE);

    private EmergencyPhoneCommandParser parser = new EmergencyPhoneCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_EMERGENCY_PHONE_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + EMERGENCY_PHONE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + EMERGENCY_PHONE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_EMERGENCY_PHONE_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        // emergency phone field is filled
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + EMERGENCY_PHONE_DESC_BOB;
        EmergencyPhoneCommand expectedCommand = new EmergencyPhoneCommand(targetIndex,
                new EmergencyPhone(VALID_EMERGENCY_PHONE_BOB));
        assertParseSuccess(parser, userInput, expectedCommand);

        // emergency phone field is empty
        userInput = targetIndex.getOneBased() + " ep/";
        expectedCommand = new EmergencyPhoneCommand(targetIndex,
                new EmergencyPhone(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

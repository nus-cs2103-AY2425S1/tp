package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ECNUMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ECNUMBER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ECNUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNUMBER_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddEcNumberCommand;
import seedu.address.model.person.EcNumber;

public class AddEcNumberCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEcNumberCommand.MESSAGE_USAGE);

    private AddEcNumberCommandParser parser = new AddEcNumberCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ECNUMBER_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ECNUMBER_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ECNUMBER_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_ECNUMBER_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        // emergency phone field is filled
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + ECNUMBER_DESC_BOB;
        AddEcNumberCommand expectedCommand = new AddEcNumberCommand(targetIndex,
                new EcNumber(VALID_ECNUMBER_BOB));
        assertParseSuccess(parser, userInput, expectedCommand);

        // emergency phone field is empty
        userInput = targetIndex.getOneBased() + " ep/";
        expectedCommand = new AddEcNumberCommand(targetIndex,
                new EcNumber(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

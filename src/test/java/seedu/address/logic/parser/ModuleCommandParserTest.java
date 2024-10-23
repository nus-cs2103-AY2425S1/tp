package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENTID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.STUDENTID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENTID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ModuleCommand;
import seedu.address.model.person.Module;
import seedu.address.model.person.StudentId;

public class ModuleCommandParserTest {
    private ModuleCommandParser parser = new ModuleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        StudentId expectedStudentId = new StudentId(VALID_STUDENTID_BOB);
        Module expectedModule = new Module(VALID_MODULE_BOB);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + STUDENTID_DESC_BOB + MODULE_DESC_BOB,
                new ModuleCommand(expectedStudentId, expectedModule));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedModuleString = STUDENTID_DESC_AMY + MODULE_DESC_AMY;

        // multiple studentIds
        assertParseFailure(parser, STUDENTID_DESC_AMY + validExpectedModuleString,
                Messages.getErrorMessageForDuplicateID());

        // multiple modules
        assertParseFailure(parser, validExpectedModuleString + MODULE_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedModuleString + STUDENTID_DESC_AMY + MODULE_DESC_AMY + validExpectedModuleString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE));

        // invalid value followed by valid value

        // invalid studentId
        assertParseFailure(parser, INVALID_STUDENTID_DESC + validExpectedModuleString,
                Messages.getErrorMessageForDuplicateID());

        // invalid module
        assertParseFailure(parser, INVALID_MODULE_DESC + validExpectedModuleString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE));

        // valid value followed by invalid value

        // invalid studentId
        assertParseFailure(parser, validExpectedModuleString + INVALID_STUDENTID_DESC,
                 Messages.getErrorMessageForDuplicateID());

        // invalid module
        assertParseFailure(parser, validExpectedModuleString + INVALID_MODULE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleCommand.MESSAGE_USAGE);

        // missing module prefix
        assertParseFailure(parser, STUDENTID_DESC_BOB + VALID_MODULE_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_STUDENTID_BOB + VALID_MODULE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid studentId
        assertParseFailure(parser, INVALID_STUDENTID_DESC + MODULE_DESC_BOB, StudentId.MESSAGE_CONSTRAINTS);

        // invalid module
        assertParseFailure(parser, STUDENTID_DESC_BOB + INVALID_MODULE_DESC, Module.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_STUDENTID_DESC + INVALID_MODULE_DESC,
                StudentId.MESSAGE_CONSTRAINTS);

        //        // non-empty preamble
        //        assertParseFailure(parser, PREAMBLE_NON_EMPTY + STUDENTID_DESC_BOB + MODULE_DESC_BOB,
        //                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModuleCommand.MESSAGE_USAGE));
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENTID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TUTORIALID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STUDENTID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TUTORIALID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIALID_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditStudentDescriptor;
import seedu.address.model.student.Name;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;
import seedu.address.testutil.EditStudentDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid name
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // Invalid student ID
        assertParseFailure(parser, "1" + INVALID_STUDENTID_DESC, StudentId.MESSAGE_CONSTRAINTS);

        // Invalid tutorial ID
        assertParseFailure(parser, "1" + INVALID_TUTORIALID_DESC, TutorialId.MESSAGE_CONSTRAINTS);

        // Multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_STUDENTID_DESC + VALID_TUTORIALID_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_STUDENT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY + STUDENTID_DESC_AMY + TUTORIALID_DESC_AMY;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withStudentId(VALID_STUDENTID_AMY).withTutorialId(VALID_TUTORIALID_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_THIRD_STUDENT;
        String userInput;
        EditStudentDescriptor descriptor;
        EditCommand expectedCommand;

        // Name
        userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Student ID
        userInput = targetIndex.getOneBased() + STUDENTID_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withStudentId(VALID_STUDENTID_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // Tutorial ID
        userInput = targetIndex.getOneBased() + TUTORIALID_DESC_AMY;
        descriptor = new EditStudentDescriptorBuilder().withTutorialId(VALID_TUTORIALID_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // Duplicate prefixes should result in failure

        // Valid followed by invalid
        Index targetIndex = INDEX_FIRST_STUDENT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY + NAME_DESC_BOB;

        assertParseFailure(parser, userInput, getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // Invalid followed by valid
        userInput = targetIndex.getOneBased() + INVALID_NAME_DESC + NAME_DESC_AMY;

        assertParseFailure(parser, userInput, getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // Multiple valid fields repeated
        userInput = targetIndex.getOneBased() + NAME_DESC_AMY + STUDENTID_DESC_AMY + TUTORIALID_DESC_AMY
                + NAME_DESC_AMY + STUDENTID_DESC_AMY + TUTORIALID_DESC_AMY;

        assertParseFailure(parser, userInput, getErrorMessageForDuplicatePrefixes(
                PREFIX_NAME, PREFIX_STUDENTID, PREFIX_TUTORIALID));

        // Multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_NAME_DESC + INVALID_STUDENTID_DESC + INVALID_TUTORIALID_DESC
                + INVALID_NAME_DESC + INVALID_STUDENTID_DESC + INVALID_TUTORIALID_DESC;

        assertParseFailure(parser, userInput, getErrorMessageForDuplicatePrefixes(
                PREFIX_NAME, PREFIX_STUDENTID, PREFIX_TUTORIALID));
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // If user first inputs an invalid value followed by a valid value for the same prefix,
        // the parser should catch the duplicate prefix error.

        Index targetIndex = INDEX_FIRST_STUDENT;

        // Invalid name followed by valid name
        String userInput = targetIndex.getOneBased() + INVALID_NAME_DESC + NAME_DESC_AMY;

        assertParseFailure(parser, userInput, getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success2() {
        // Similar test as above but with different field

        Index targetIndex = INDEX_FIRST_STUDENT;

        // Invalid student ID followed by valid student ID
        String userInput = targetIndex.getOneBased() + INVALID_STUDENTID_DESC + STUDENTID_DESC_AMY;

        assertParseFailure(parser, userInput, getErrorMessageForDuplicatePrefixes(PREFIX_STUDENTID));
    }

    @Test
    public void parse_resetFields_success() {
        // We can test resetting fields if applicable in your implementation.
        // Since we don't have any fields that require resetting (like tags),
        // this test can be omitted unless necessary.
    }
}

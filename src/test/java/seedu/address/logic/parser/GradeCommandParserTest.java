package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODULE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENTID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MODULE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.STUDENTID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENTID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MODULE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.GradeCommand;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Module;
import seedu.address.model.person.StudentId;

public class GradeCommandParserTest {
    private GradeCommandParser parser = new GradeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        StudentId expectedStudentId = new StudentId(VALID_STUDENTID_BOB);
        Module expectedModule = new Module(VALID_MODULE_BOB);
        Grade expectedGrade = new Grade(VALID_GRADE_BOB);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + STUDENTID_DESC_BOB + MODULE_DESC_BOB + GRADE_DESC_BOB,
                new GradeCommand(expectedStudentId, expectedModule, expectedGrade));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedGradeString = STUDENTID_DESC_AMY + MODULE_DESC_AMY + GRADE_DESC_AMY;

        // multiple studentIds
        System.out.println(STUDENTID_DESC_AMY + validExpectedGradeString);
        assertParseFailure(parser, STUDENTID_DESC_AMY + validExpectedGradeString,
                Messages.getErrorMessageForDuplicateID());

        // multiple modules
        assertParseFailure(parser, MODULE_DESC_AMY + validExpectedGradeString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE));

        // multiple grades
        assertParseFailure(parser, GRADE_DESC_AMY + validExpectedGradeString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GRADE));

        // multiple fields repeated
        assertParseFailure(parser,
                STUDENTID_DESC_AMY + validExpectedGradeString + MODULE_DESC_AMY + GRADE_DESC_AMY
                        + validExpectedGradeString,
                Messages.getErrorMessageForDuplicateID());

        // invalid value followed by valid value

        // invalid studentId
        assertParseFailure(parser, INVALID_STUDENTID_DESC + validExpectedGradeString,
                Messages.getErrorMessageForDuplicateID());

        // invalid module
        assertParseFailure(parser, INVALID_MODULE_DESC + validExpectedGradeString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE));

        // invalid grade
        assertParseFailure(parser, INVALID_GRADE_DESC + validExpectedGradeString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GRADE));

        // valid value followed by invalid value

        // invalid studentId
        assertParseFailure(parser, validExpectedGradeString + INVALID_STUDENTID_DESC ,
                Messages.getErrorMessageForDuplicateID());

        // invalid module
        assertParseFailure(parser, validExpectedGradeString + INVALID_MODULE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MODULE));

        // invalid grade
        assertParseFailure(parser, validExpectedGradeString + INVALID_GRADE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GRADE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradeCommand.MESSAGE_USAGE);

        //        // missing studentId prefix
        //        assertParseFailure(parser, VALID_STUDENTID_BOB + MODULE_DESC_BOB + GRADE_DESC_BOB, expectedMessage);

        // missing module prefix
        assertParseFailure(parser, STUDENTID_DESC_BOB + VALID_MODULE_BOB + GRADE_DESC_BOB, expectedMessage);

        // missing module prefix
        assertParseFailure(parser, STUDENTID_DESC_BOB + MODULE_DESC_BOB + VALID_GRADE_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_STUDENTID_BOB + VALID_MODULE_BOB + VALID_GRADE_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid studentId
        assertParseFailure(parser, INVALID_STUDENTID_DESC + MODULE_DESC_BOB + GRADE_DESC_BOB,
                StudentId.MESSAGE_CONSTRAINTS);

        // invalid module
        assertParseFailure(parser, STUDENTID_DESC_BOB + INVALID_MODULE_DESC + GRADE_DESC_BOB,
                Module.MESSAGE_CONSTRAINTS);

        // invalid grade
        assertParseFailure(parser, STUDENTID_DESC_BOB + MODULE_DESC_BOB + INVALID_GRADE_DESC,
                Grade.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_STUDENTID_DESC + INVALID_MODULE_DESC + GRADE_DESC_BOB,
                StudentId.MESSAGE_CONSTRAINTS);

        //        // non-empty preamble
        //        assertParseFailure(parser, PREAMBLE_NON_EMPTY + STUDENTID_DESC_BOB + MODULE_DESC_BOB + GRADE_DESC_BOB,
        //                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradeCommand.MESSAGE_USAGE));
    }
}

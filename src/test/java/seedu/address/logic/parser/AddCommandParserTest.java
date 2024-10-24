package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENTID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TUTORIALID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.STUDENTID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENTID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TUTORIALID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TUTORIALID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIALID_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TutUtil.TUTORIAL_ID;
import static seedu.address.testutil.TypicalStudents.AMY;
import static seedu.address.testutil.TypicalStudents.AMY_NO_TUTORIAL_ID;
import static seedu.address.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentId;
import seedu.address.model.student.TutorialId;
import seedu.address.testutil.StudentBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedStudent = new StudentBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + STUDENTID_DESC_BOB
                + TUTORIALID_DESC_BOB, new AddCommand(expectedStudent, TUTORIAL_ID));


        // multiple tags - all accepted (no longer relevant)
        Student expectedStudentMultipleTags = new StudentBuilder(BOB)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + STUDENTID_DESC_BOB + TUTORIALID_DESC_BOB,
                new AddCommand(expectedStudentMultipleTags, TUTORIAL_ID));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedStudentString = NAME_DESC_BOB + STUDENTID_DESC_BOB + TUTORIALID_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple fields repeated
        assertParseFailure(parser,
                NAME_DESC_AMY + STUDENTID_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_STUDENTID));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid student id
        assertParseFailure(parser, INVALID_STUDENTID_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENTID));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedStudentString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid student id
        assertParseFailure(parser, validExpectedStudentString + INVALID_STUDENTID_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENTID));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Student expectedStudent = new StudentBuilder(AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY + STUDENTID_DESC_AMY + TUTORIALID_DESC_AMY,
                new AddCommand(expectedStudent, TUTORIAL_ID));
    }

    @Test
    public void parse_tutorialFieldMissing_success() {
        // zero tags
        Student expectedStudent = new StudentBuilder(AMY_NO_TUTORIAL_ID).build();
        assertParseSuccess(parser, NAME_DESC_AMY + STUDENTID_DESC_AMY,
                new AddCommand(expectedStudent, TutorialId.none()));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + STUDENTID_DESC_BOB + TUTORIALID_DESC_BOB,
                expectedMessage);

        // missing student id prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_STUDENTID_BOB + TUTORIALID_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_STUDENTID_BOB + VALID_TUTORIALID_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + STUDENTID_DESC_BOB
                + TUTORIALID_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid student id
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_STUDENTID_DESC
                + TUTORIALID_DESC_BOB, StudentId.MESSAGE_CONSTRAINTS);

        // invalid tutorial class
        assertParseFailure(parser, NAME_DESC_BOB + STUDENTID_DESC_BOB
                + INVALID_TUTORIALID_DESC, TutorialId.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + STUDENTID_DESC_BOB + INVALID_TUTORIALID_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + STUDENTID_DESC_BOB
                        + TUTORIALID_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

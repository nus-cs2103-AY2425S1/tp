package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCORE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME_PHYSICS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_SCORE_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAX_SCORE_PHYSICS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_MAX_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.model.assignment.Assignment.MAX_SCORE_MESSAGE_CONSTRAINTS;
import static seedu.address.model.assignment.AssignmentName.MESSAGE_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the EditAssignmentCommandParser code.
 */
public class EditAssignmentCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAssignmentCommand.MESSAGE_USAGE);
    private final EditAssignmentCommandParser parser = new EditAssignmentCommandParser();

    @Test
    public void parse_missingStudentIndex_throwsParseException() {
        String userInput = " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_MATH
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + VALID_MAX_SCORE_MATH;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingAssignmentIndex_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_MATH
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + VALID_MAX_SCORE_MATH;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidStudentIndex_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INVALID_INDEX
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_MATH
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + VALID_MAX_SCORE_MATH;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidAssignmentIndex_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INVALID_INDEX
                + " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_MATH
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + VALID_MAX_SCORE_MATH;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_extraPrefixes_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_STUDENT_INDEX + INDEX_FIRST_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_MATH
                + " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_MATH
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + VALID_MAX_SCORE_MATH;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingAssignmentName_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME + " "
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + VALID_MAX_SCORE_MATH;

        assertParseFailure(parser, userInput, MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidName_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME + INVALID_ASSIGNMENT_NAME
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + VALID_MAX_SCORE_MATH;

        assertParseFailure(parser, userInput, MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidMaxScore_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_MATH
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + INVALID_SCORE;

        assertParseFailure(parser, userInput, MAX_SCORE_MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_maxScoreOutOfRange_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_MATH
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + "-1";
        assertParseFailure(parser, userInput, MAX_SCORE_MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_MATH
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + VALID_MAX_SCORE_MATH;

        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder().withAssignmentName(new AssignmentName(VALID_ASSIGNMENT_NAME_MATH))
                        .withMaxScore(VALID_MAX_SCORE_MATH).build();
        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(INDEX_SECOND_STUDENT, INDEX_FIRST_ASSIGNMENT,
                descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + VALID_MAX_SCORE_PHYSICS;

        EditAssignmentCommand.EditAssignmentDescriptor descriptor =
                new EditAssignmentDescriptorBuilder()
                        .withMaxScore(VALID_MAX_SCORE_PHYSICS).build();
        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(INDEX_SECOND_STUDENT, INDEX_FIRST_ASSIGNMENT,
                descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_PHYSICS;

        descriptor = new EditAssignmentDescriptorBuilder()
                .withAssignmentName(new AssignmentName(VALID_ASSIGNMENT_NAME_PHYSICS)).build();
        expectedCommand = new EditAssignmentCommand(INDEX_SECOND_STUDENT, INDEX_FIRST_ASSIGNMENT,
                descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // valid followed by invalid
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_MATH
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + INVALID_SCORE;

        assertParseFailure(parser, userInput, MAX_SCORE_MESSAGE_CONSTRAINTS);

        // invalid followed by valid
        userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME + INVALID_ASSIGNMENT_NAME
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + VALID_MAX_SCORE_MATH;

        assertParseFailure(parser, userInput, MESSAGE_CONSTRAINTS);

        // multiple valid values
        userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_MATH
                + " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_PHYSICS
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + VALID_MAX_SCORE_MATH
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + VALID_MAX_SCORE_PHYSICS;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT_NAME,
                PREFIX_ASSIGNMENT_MAX_SCORE));

        // multiple invalid values
        userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME + INVALID_ASSIGNMENT_NAME
                + " " + PREFIX_ASSIGNMENT_NAME + INVALID_ASSIGNMENT_NAME
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + INVALID_SCORE
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + INVALID_SCORE;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT_NAME,
                PREFIX_ASSIGNMENT_MAX_SCORE));
    }

    @Test
    public void parse_noFieldsEdited_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased();

        assertParseFailure(parser, userInput, EditAssignmentCommand.MESSAGE_NOT_EDITED);
    }
}

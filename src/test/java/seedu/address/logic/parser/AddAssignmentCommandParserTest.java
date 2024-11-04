package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCORE_MATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_MAX_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.testutil.AssignmentBuilder;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the AddAssignmentCommandParser code.
 */
public class AddAssignmentCommandParserTest {
    private AddAssignmentCommandParser parser = new AddAssignmentCommandParser();

    @Test
    public void parse_validArgs_returnsAddAssignmentCommand() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_FIRST_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_MATH
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + VALID_SCORE_MATH;

        AddAssignmentCommand.AssignmentDescriptor toAddDescriptor =
                new AddAssignmentCommand.AssignmentDescriptor(AssignmentBuilder.DEFAULT_MAX_SCORE,
                        new AssignmentName(AssignmentBuilder.DEFAULT_ASSIGNMENT_NAME));

        assertParseSuccess(parser, userInput, new AddAssignmentCommand(INDEX_FIRST_STUDENT, toAddDescriptor));
    }

    @Test
    public void parse_missingStudentIndex_throwsParseException() {
        String userInput = " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_MATH
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + VALID_SCORE_MATH;

        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingAssignmentName_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_FIRST_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + VALID_SCORE_MATH;

        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingMaxScore_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_FIRST_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_MATH;

        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingStudentArg_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX
                + " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_MATH
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + VALID_SCORE_MATH;

        assertParseFailure(parser, userInput, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_missingAssignmentNameArg_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_FIRST_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE + VALID_SCORE_MATH;

        assertParseFailure(parser, userInput, AssignmentName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingMaxScoreArg_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_FIRST_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_NAME + VALID_ASSIGNMENT_NAME_MATH
                + " " + PREFIX_ASSIGNMENT_MAX_SCORE;

        assertParseFailure(parser, userInput, Assignment.MAX_SCORE_MESSAGE_CONSTRAINTS);
    }
}

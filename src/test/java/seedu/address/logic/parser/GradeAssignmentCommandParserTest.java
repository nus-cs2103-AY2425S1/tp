package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCORE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GradeAssignmentCommand;

public class GradeAssignmentCommandParserTest {

    private GradeAssignmentCommandParser parser = new GradeAssignmentCommandParser();

    @Test
    public void parse_missingStudentIndex_throwsParseException() {
        String userInput = " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_SCORE + VALID_SCORE;

        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GradeAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingAssignmentIndex_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_SCORE + VALID_SCORE;

        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GradeAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingScore_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased();
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GradeAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStudentIndex_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INVALID_INDEX
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_SCORE + VALID_SCORE;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GradeAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidAssignmentIndex_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INVALID_INDEX
                + " " + PREFIX_ASSIGNMENT_SCORE + VALID_SCORE;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GradeAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidScore_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_SCORE + INVALID_SCORE;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                GradeAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraPrefixes_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_STUDENT_INDEX + INDEX_FIRST_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_SCORE + VALID_SCORE;
        assertParseFailure(parser, userInput, MESSAGE_DUPLICATE_FIELDS + PREFIX_STUDENT_INDEX);
    }

    @Test
    public void parse_validArgs_returnsGradeAssignmentCommand() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased()
                + " " + PREFIX_ASSIGNMENT_SCORE + VALID_SCORE;
        assertParseSuccess(parser, userInput, new GradeAssignmentCommand(INDEX_SECOND_STUDENT, INDEX_FIRST_ASSIGNMENT,
                VALID_SCORE));
    }
}

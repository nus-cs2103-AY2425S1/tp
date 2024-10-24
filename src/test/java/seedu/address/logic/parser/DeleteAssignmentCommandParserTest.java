package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_2024_10_20;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_80;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GRADE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER_MISSING_LETTER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER_TOO_FEW_NUMBERS;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_N;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_Y;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_2024_10_20;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_80;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_N;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_Y;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_HUGH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteAssignmentCommand;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.AssignmentQuery;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Grade;
import seedu.address.model.assignment.Status;
import seedu.address.model.person.Name;
import seedu.address.model.student.StudentNumber;

public class DeleteAssignmentCommandParserTest {
    private DeleteAssignmentCommandParser parser = new DeleteAssignmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // all fields provided correctly
        Name name = new Name(VALID_NAME_BOB);
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_MATH);
        Deadline deadline = new Deadline(VALID_DEADLINE_2024_10_20);
        Status submissionStatus = new Status(VALID_STATUS_Y);
        Status gradingStatus = new Status(VALID_STATUS_N);
        Grade grade = new Grade(VALID_GRADE_80);

        AssignmentQuery expectedQuery = new AssignmentQuery(
                assignmentName, deadline, submissionStatus, gradingStatus, grade);

        assertParseSuccess(parser,
                NAME_DESC_BOB + ASSIGNMENT_DESC_MATH + DEADLINE_DESC_2024_10_20
                        + STATUS_DESC_Y + STATUS_DESC_N + GRADE_DESC_80,
                new DeleteAssignmentCommand(name, expectedQuery));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // only compulsory fields
        Name name = new Name(VALID_NAME_BOB);
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_MATH);

        AssignmentQuery expectedQuery = new AssignmentQuery(
                assignmentName, null, null, null, null);

        assertParseSuccess(parser,
                NAME_DESC_BOB + ASSIGNMENT_DESC_MATH,
                new DeleteAssignmentCommand(name, expectedQuery));

        // one optional field (deadline) present
        expectedQuery = new AssignmentQuery(
                assignmentName, new Deadline(VALID_DEADLINE_2024_10_20), null, null, null);

        assertParseSuccess(parser,
                NAME_DESC_BOB + ASSIGNMENT_DESC_MATH + DEADLINE_DESC_2024_10_20,
                new DeleteAssignmentCommand(name, expectedQuery));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser,
                INVALID_NAME_DESC + ASSIGNMENT_DESC_MATH,
                Name.MESSAGE_CONSTRAINTS);

        // invalid assignment name
        assertParseFailure(parser,
                NAME_DESC_BOB + INVALID_ASSIGNMENT_DESC,
                AssignmentName.MESSAGE_CONSTRAINTS);

        // invalid deadline
        assertParseFailure(parser,
                NAME_DESC_BOB + ASSIGNMENT_DESC_MATH + " " + PREFIX_DEADLINE + "invalid_date",
                Deadline.MESSAGE_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser,
                NAME_DESC_BOB + ASSIGNMENT_DESC_MATH + INVALID_STATUS_DESC,
                Status.MESSAGE_CONSTRAINTS);

        // invalid grade
        assertParseFailure(parser,
                NAME_DESC_BOB + ASSIGNMENT_DESC_MATH + INVALID_GRADE_DESC,
                Grade.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_extraStatusValues_success() {
        // more than 2 status values
        String input = NAME_DESC_BOB + ASSIGNMENT_DESC_MATH
                + STATUS_DESC_Y + STATUS_DESC_N + STATUS_DESC_Y;
        Name name = new Name(VALID_NAME_BOB);
        AssignmentQuery assignmentQuery = new AssignmentQuery(
                new AssignmentName(VALID_ASSIGNMENT_MATH),
                null, new Status(VALID_STATUS_Y), new Status(VALID_STATUS_N), null);
        assertParseSuccess(parser, input, new DeleteAssignmentCommand(name, assignmentQuery));
    }

    @Test
    public void parse_missingCompulsoryFields_failure() {
        // missing name
        assertParseFailure(parser,
                ASSIGNMENT_DESC_MATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAssignmentCommand.MESSAGE_USAGE));

        // missing assignment name
        assertParseFailure(parser,
                NAME_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAssignmentCommand.MESSAGE_USAGE));

        // missing both name and assignment name
        assertParseFailure(parser,
                "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStudentNumber_failure() {
        assertParseFailure(parser,
                NAME_DESC_BOB + ASSIGNMENT_DESC_MATH + " "
                        + PREFIX_STUDENT_NUMBER + INVALID_STUDENT_NUMBER_TOO_FEW_NUMBERS,
                StudentNumber.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser,
                NAME_DESC_BOB + ASSIGNMENT_DESC_MATH + " "
                        + PREFIX_STUDENT_NUMBER + INVALID_STUDENT_NUMBER_MISSING_LETTER,
                StudentNumber.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validStudentNumber_success() {
        Name name = new Name(VALID_NAME_BOB);
        AssignmentQuery assignmentQuery = new AssignmentQuery(
                new AssignmentName(VALID_ASSIGNMENT_MATH),
                null, null, null, null);
        StudentNumber studentNumber = new StudentNumber(VALID_STUDENT_NUMBER_HUGH);

        assertParseSuccess(parser,
                NAME_DESC_BOB + ASSIGNMENT_DESC_MATH + " "
                        + PREFIX_STUDENT_NUMBER + VALID_STUDENT_NUMBER_HUGH,
                new DeleteAssignmentCommand(name, assignmentQuery, studentNumber));
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER_MISSING_LETTER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER_TOO_FEW_NUMBERS;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_HUGH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.commands.DeleteAssignmentCommand;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.AssignmentQuery;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Grade;
import seedu.address.model.assignment.Status;
import seedu.address.model.person.Name;
import seedu.address.model.student.StudentNumber;

public class AddAssignmentCommandParserTest {

    private static final String VALID_INPUT = " n/John Doe a/CS2103 Project d/2024-12-01 s/Y s/Y g/85.5";
    private static final String MISSING_NAME_INPUT = " a/CS2103 Project d/2024-12-01";
    private static final String MISSING_ASSIGNMENT_NAME_INPUT = " n/John Doe d/2024-12-01";
    private static final String MISSING_DEADLINE_INPUT = " n/John Doe a/CS2103 Project";
    private static final String INVALID_DEADLINE_INPUT = " n/John Doe a/CS2103 Project d/invalid-date";
    private static final String GRADE_NOT_PROVIDED_INPUT = " n/John Doe a/CS2103 Project d/2024-12-01 s/Y";
    private static final String GRADING_STATUS_NOT_GRADED_INPUT = " n/John Doe a/CS2103 Project d/2024-12-01 s/N";
    private static final String STATUS_ONLY_INPUT = " n/John Doe a/CS2103 Project d/2024-12-01 s/Y";

    private final AddAssignmentCommandParser parser = new AddAssignmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        AddAssignmentCommand expectedCommand = new AddAssignmentCommand(
                new Name("John Doe"),
                new Assignment(
                        new AssignmentName("CS2103 Project"),
                        new Deadline("2024-12-01"),
                        new Status("Y"),
                        new Status("Y"),
                        new Grade("85.5")
                )
        );

        assertParseSuccess(parser, VALID_INPUT, expectedCommand);
    }

    @Test
    public void parse_missingName_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE);
        assertParseFailure(parser, MISSING_NAME_INPUT, expectedMessage);
    }

    @Test
    public void parse_missingAssignmentName_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE);
        assertParseFailure(parser, MISSING_ASSIGNMENT_NAME_INPUT, expectedMessage);
    }

    @Test
    public void parse_missingDeadline_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE);
        assertParseFailure(parser, MISSING_DEADLINE_INPUT, expectedMessage);
    }

    @Test
    public void parse_invalidDeadline_throwsParseException() {
        String expectedMessage = Deadline.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, INVALID_DEADLINE_INPUT, expectedMessage);
    }

    @Test
    public void parse_gradeNotProvidedWhenGradingStatusIsY_success() throws Exception {
        AddAssignmentCommand expectedCommand = new AddAssignmentCommand(
                new Name("John Doe"),
                new Assignment(
                        new AssignmentName("CS2103 Project"),
                        new Deadline("2024-12-01"),
                        new Status("Y"),
                        Status.getDefault(), // Assuming the default grading status is "N"
                        Grade.getDefault() // Assuming the default grade is NULL or empty
                )
        );

        assertParseSuccess(parser, GRADE_NOT_PROVIDED_INPUT, expectedCommand);
    }

    @Test
    public void parse_gradingStatusNotGraded_success() throws Exception {
        AddAssignmentCommand expectedCommand = new AddAssignmentCommand(
                new Name("John Doe"),
                new Assignment(
                        new AssignmentName("CS2103 Project"),
                        new Deadline("2024-12-01"),
                        Status.getDefault(), // Default submission status
                        new Status("N"),
                        Grade.getDefault() // Assuming the default grade is NULL or empty
                )
        );

        assertParseSuccess(parser, GRADING_STATUS_NOT_GRADED_INPUT, expectedCommand);
    }

    @Test
    public void parse_statusOnly_success() throws Exception {
        AddAssignmentCommand expectedCommand = new AddAssignmentCommand(
                new Name("John Doe"),
                new Assignment(
                        new AssignmentName("CS2103 Project"),
                        new Deadline("2024-12-01"),
                        new Status("Y"),
                        Status.getDefault(), // Default grading status
                        Grade.getDefault() // Assuming the default grade is NULL or empty
                )
        );

        assertParseSuccess(parser, STATUS_ONLY_INPUT, expectedCommand);
    }

    @Test
    public void parse_invalidStudentNumber_failure() {
        assertParseFailure(parser,
                VALID_INPUT + " "
                        + PREFIX_STUDENT_NUMBER + INVALID_STUDENT_NUMBER_TOO_FEW_NUMBERS,
                StudentNumber.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser,
                VALID_INPUT + " "
                        + PREFIX_STUDENT_NUMBER + INVALID_STUDENT_NUMBER_MISSING_LETTER,
                StudentNumber.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validStudentNumber_success() {
        Name name = new Name(VALID_NAME_BOB);
        AddAssignmentCommand expectedCommand = new AddAssignmentCommand(
                new Name("John Doe"),
                new Assignment(
                        new AssignmentName("CS2103 Project"),
                        new Deadline("2024-12-01"),
                        new Status("Y"),
                        new Status("Y"),
                        new Grade("85.5")
                ),
                new StudentNumber(VALID_STUDENT_NUMBER_HUGH)
        );

        assertParseSuccess(parser,
                VALID_INPUT + " "
                        + PREFIX_STUDENT_NUMBER + VALID_STUDENT_NUMBER_HUGH,
                expectedCommand);
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER_MISSING_LETTER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER_TOO_FEW_NUMBERS;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_HUGH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteAssignmentCommand;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.person.Name;
import seedu.address.model.student.StudentNumber;

public class DeleteAssignmentCommandParserTest {
    private DeleteAssignmentCommandParser parser = new DeleteAssignmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // all fields provided correctly
        Name name = new Name(VALID_NAME_BOB);
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_MATH);
        StudentNumber studentNumber = new StudentNumber(VALID_STUDENT_NUMBER_HUGH);

        assertParseSuccess(parser,
                NAME_DESC_BOB + ASSIGNMENT_DESC_MATH + STUDENT_NUMBER_DESC_HUGH,
                new DeleteAssignmentCommand(name, assignmentName, studentNumber));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // only compulsory fields
        Name name = new Name(VALID_NAME_BOB);
        AssignmentName assignmentName = new AssignmentName(VALID_ASSIGNMENT_MATH);

        assertParseSuccess(parser,
                NAME_DESC_BOB + ASSIGNMENT_DESC_MATH,
                new DeleteAssignmentCommand(name, assignmentName));
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
        StudentNumber studentNumber = new StudentNumber(VALID_STUDENT_NUMBER_HUGH);

        assertParseSuccess(parser,
                NAME_DESC_BOB + ASSIGNMENT_DESC_MATH + " "
                        + PREFIX_STUDENT_NUMBER + VALID_STUDENT_NUMBER_HUGH,
                new DeleteAssignmentCommand(name, new AssignmentName(VALID_ASSIGNMENT_MATH), studentNumber));
    }
}

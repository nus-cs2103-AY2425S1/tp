package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_2024_10_20;
import static seedu.address.logic.commands.CommandTestUtil.GRADE_DESC_80;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_Y;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_2024_10_20;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GRADE_80;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_Y;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_HUGH;
import static seedu.address.logic.commands.EditAssignmentCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.AssignmentQuery;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Grade;
import seedu.address.model.assignment.Status;
import seedu.address.model.person.Name;
import seedu.address.model.student.StudentNumber;

public class EditAssignmentCommandParserTest {

    private EditAssignmentCommandParser parser = new EditAssignmentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, VALID_ASSIGNMENT_MATH, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid assignment
        assertParseFailure(parser, NAME_DESC_AMY + INVALID_ASSIGNMENT_DESC, AssignmentName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = NAME_DESC_AMY + ASSIGNMENT_DESC_MATH + DEADLINE_DESC_2024_10_20 +
                GRADE_DESC_80 + STATUS_DESC_Y + STATUS_DESC_Y;

        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(
                new Name(VALID_NAME_AMY), new AssignmentName(VALID_ASSIGNMENT_MATH),
                new AssignmentQuery(null,
                        new Deadline(VALID_DEADLINE_2024_10_20),
                        new Status(VALID_STATUS_Y),
                        new Status(VALID_STATUS_Y),
                        new Grade(VALID_GRADE_80)));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldsSpecified_success() {
        String userInput = NAME_DESC_AMY + ASSIGNMENT_DESC_MATH;

        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(
                new Name(VALID_NAME_AMY), new AssignmentName(VALID_ASSIGNMENT_MATH),
                new AssignmentQuery(null, null, null, null, null));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_allFieldsSpecifiedWithStudentNumber_success() {
        String userInput = NAME_DESC_AMY + ASSIGNMENT_DESC_MATH + DEADLINE_DESC_2024_10_20 +
                GRADE_DESC_80 + STATUS_DESC_Y + STATUS_DESC_Y + STUDENT_NUMBER_DESC_HUGH;

        EditAssignmentCommand expectedCommand = new EditAssignmentCommand(
                new Name(VALID_NAME_AMY), new AssignmentName(VALID_ASSIGNMENT_MATH),
                new AssignmentQuery(null,
                        new Deadline(VALID_DEADLINE_2024_10_20),
                        new Status(VALID_STATUS_Y),
                        new Status(VALID_STATUS_Y),
                        new Grade(VALID_GRADE_80)),
                new StudentNumber(VALID_STUDENT_NUMBER_HUGH));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

}

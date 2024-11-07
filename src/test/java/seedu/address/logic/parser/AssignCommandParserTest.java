package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_ID_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGNMENT_ID_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.EMPLOYEE_ID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASSIGNMENT_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMPLOYEE_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROJECT_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_ID_DESC_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_ID_DESC_BETA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_ID_ONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEmployees.BOB;
import static seedu.address.testutil.TypicalProjects.BETA;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.model.assignment.Assignment;
import seedu.address.testutil.AssignmentBuilder;

public class AssignCommandParserTest {
    private AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Assignment expectedAssignment = new AssignmentBuilder().withAssignmentId(VALID_ASSIGNMENT_ID_ONE)
                .withEmployee(BOB).withProject(BETA).build();

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + ASSIGNMENT_ID_DESC_ONE + PROJECT_ID_DESC_BETA
                        + EMPLOYEE_ID_DESC_BOB,
                new AssignCommand(expectedAssignment.getAssignmentId(),
                        expectedAssignment.getProject().getId(),
                        expectedAssignment.getEmployee().getEmployeeId()));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedEmployeeString = ASSIGNMENT_ID_DESC_ONE + PROJECT_ID_DESC_BETA
                + EMPLOYEE_ID_DESC_BOB;

        // multiple assignmentIds
        assertParseFailure(parser, ASSIGNMENT_ID_DESC_TWO + validExpectedEmployeeString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT_ID));

        // multiple projectIds
        assertParseFailure(parser, PROJECT_ID_DESC_ALPHA + validExpectedEmployeeString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PROJECT_ID));

        // multiple employeeIds
        assertParseFailure(parser, EMPLOYEE_ID_DESC_BOB + validExpectedEmployeeString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMPLOYEE_ID));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedEmployeeString + ASSIGNMENT_ID_DESC_TWO + PROJECT_ID_DESC_ALPHA
                        + EMPLOYEE_ID_DESC_BOB
                        + validExpectedEmployeeString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT_ID, PREFIX_PROJECT_ID,
                        PREFIX_EMPLOYEE_ID));

        // invalid value followed by valid value

        // invalid assignmentId
        assertParseFailure(parser, INVALID_ASSIGNMENT_ID_DESC + validExpectedEmployeeString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT_ID));

        // invalid projectId
        assertParseFailure(parser, INVALID_PROJECT_ID_DESC + validExpectedEmployeeString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PROJECT_ID));

        // invalid phone
        assertParseFailure(parser, INVALID_EMPLOYEE_ID_DESC + validExpectedEmployeeString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMPLOYEE_ID));

        // valid value followed by invalid value

        // invalid assignmentId
        assertParseFailure(parser, validExpectedEmployeeString + INVALID_ASSIGNMENT_ID_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT_ID));

        // invalid projectId
        assertParseFailure(parser, validExpectedEmployeeString + INVALID_PROJECT_ID_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PROJECT_ID));

        // invalid phone
        assertParseFailure(parser, validExpectedEmployeeString + INVALID_EMPLOYEE_ID_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMPLOYEE_ID));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE);

        // missing assignmentId prefix
        assertParseFailure(parser, PROJECT_ID_DESC_ALPHA + EMPLOYEE_ID_DESC_BOB,
                expectedMessage);

        // missing employeeId prefix
        assertParseFailure(parser, ASSIGNMENT_ID_DESC_ONE + PROJECT_ID_DESC_ALPHA,
                expectedMessage);

        // missing projectId prefix
        assertParseFailure(parser, ASSIGNMENT_ID_DESC_ONE + EMPLOYEE_ID_DESC_BOB,
                expectedMessage);
    }

}

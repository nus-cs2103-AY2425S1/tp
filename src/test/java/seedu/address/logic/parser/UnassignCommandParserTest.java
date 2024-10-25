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
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMPLOYEE_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_ID_ALPHA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.UnassignCommand;
import seedu.address.model.assignment.Assignment;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.TypicalEmployees;
import seedu.address.testutil.TypicalProjects;


public class UnassignCommandParserTest {
    private UnassignCommandParser parser = new UnassignCommandParser();

    @Test
    public void parse_assignmentIdPresent_success() {
        Assignment expectedAssignment = new AssignmentBuilder().withAssignmentId(VALID_ASSIGNMENT_ID_ONE)
                .withPerson(TypicalEmployees.ALICE).withProject(TypicalProjects.ALPHA).build();

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + ASSIGNMENT_ID_DESC_ONE,
                new UnassignCommand(expectedAssignment.getAssignmentId()));
    }

    @Test
    public void parse_projectIdAndEmployeeIdPresent_success() {
        Assignment expectedAssignment = new AssignmentBuilder().withAssignmentId(VALID_ASSIGNMENT_ID_ONE)
                .withPerson(TypicalEmployees.BOB).withProject(TypicalProjects.BETA).build();

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + PROJECT_ID_DESC_BETA
                        + EMPLOYEE_ID_DESC_BOB,
                new UnassignCommand(expectedAssignment.getProject().getId(),
                        expectedAssignment.getPerson().getEmployeeId()));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid projectId
        assertParseFailure(parser, INVALID_PROJECT_ID_DESC + VALID_EMPLOYEE_ID_AMY,
               String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));

        // invalid employeeId
        assertParseFailure(parser, INVALID_EMPLOYEE_ID_DESC + VALID_PROJECT_ID_ALPHA,
               String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));

    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedProjectIdAndEmployeeIdString = PROJECT_ID_DESC_BETA
                + EMPLOYEE_ID_DESC_BOB;
        String validExpectedAssignmentIdString = ASSIGNMENT_ID_DESC_ONE;

        // multiple assignmentIds
        assertParseFailure(parser, ASSIGNMENT_ID_DESC_TWO + validExpectedAssignmentIdString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT_ID));

        // multiple projectIds
        assertParseFailure(parser, PROJECT_ID_DESC_ALPHA + validExpectedProjectIdAndEmployeeIdString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PROJECT_ID));

        // multiple employeeIds
        assertParseFailure(parser, EMPLOYEE_ID_DESC_BOB + validExpectedProjectIdAndEmployeeIdString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMPLOYEE_ID));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedProjectIdAndEmployeeIdString + ASSIGNMENT_ID_DESC_TWO + validExpectedAssignmentIdString
                        + validExpectedProjectIdAndEmployeeIdString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT_ID, PREFIX_PROJECT_ID,
                        PREFIX_EMPLOYEE_ID));

        // invalid value followed by valid value

        // invalid assignmentId
        assertParseFailure(parser, INVALID_ASSIGNMENT_ID_DESC + validExpectedAssignmentIdString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT_ID));

        // invalid projectId
        assertParseFailure(parser, INVALID_PROJECT_ID_DESC + validExpectedProjectIdAndEmployeeIdString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PROJECT_ID));

        // invalid employeeId
        assertParseFailure(parser, INVALID_EMPLOYEE_ID_DESC + validExpectedProjectIdAndEmployeeIdString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMPLOYEE_ID));

        // valid value followed by invalid value

        // invalid assignmentId
        assertParseFailure(parser, validExpectedAssignmentIdString + INVALID_ASSIGNMENT_ID_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASSIGNMENT_ID));

        // invalid projectId
        assertParseFailure(parser, validExpectedProjectIdAndEmployeeIdString + INVALID_PROJECT_ID_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PROJECT_ID));

        // invalid employeeId
        assertParseFailure(parser, validExpectedProjectIdAndEmployeeIdString + INVALID_EMPLOYEE_ID_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMPLOYEE_ID));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE);

        // missing assignmentId prefix
        assertParseFailure(parser, "",
                expectedMessage);

        // missing employeeId prefix
        assertParseFailure(parser, PROJECT_ID_DESC_ALPHA,
                expectedMessage);

        // missing projectId prefix
        assertParseFailure(parser, EMPLOYEE_ID_DESC_BOB,
                expectedMessage);
    }

    @Test
    public void parse_tooMuchFieldsGiven_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE);
        // too much prefix
        assertParseFailure(parser, ASSIGNMENT_ID_DESC_ONE + PROJECT_ID_DESC_ALPHA + EMPLOYEE_ID_DESC_BOB,
                expectedMessage);
    }
}

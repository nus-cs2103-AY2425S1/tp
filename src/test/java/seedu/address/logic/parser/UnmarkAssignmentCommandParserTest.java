package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ASSIGNMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.UnmarkAssignmentCommand;

public class UnmarkAssignmentCommandParserTest {

    private final UnmarkAssignmentCommandParser parser = new UnmarkAssignmentCommandParser();

    @Test
    public void parse_validArgs_returnsUnmarkAssignmentCommand() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_FIRST_STUDENT.getOneBased() + " "
                + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT.getOneBased();
        assertParseSuccess(parser, userInput, new UnmarkAssignmentCommand(INDEX_FIRST_STUDENT, INDEX_FIRST_ASSIGNMENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnmarkAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefixes_throwsParseException() {
        // EP: Missing both prefixes
        String userInput = "1 1";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnmarkAssignmentCommand.MESSAGE_USAGE));

        // EP: Missing student prefix
        userInput = "1 " + PREFIX_ASSIGNMENT_INDEX + "1";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnmarkAssignmentCommand.MESSAGE_USAGE));

        // EP: Missing assignment prefix
        userInput = PREFIX_STUDENT_INDEX + "1";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnmarkAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        // EP: Duplicate student prefix
        String duplicateStudentInput = " " + PREFIX_STUDENT_INDEX + INDEX_FIRST_STUDENT.getOneBased() + " "
                + PREFIX_STUDENT_INDEX + INDEX_SECOND_STUDENT + " " + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT;
        Prefix[] duplicatedStudentPrefixes = { PREFIX_STUDENT_INDEX };
        assertParseFailure(parser, duplicateStudentInput,
                Messages.getErrorMessageForDuplicatePrefixes(duplicatedStudentPrefixes));

        // EP: Duplicate assignment prefix
        String duplicateAssignmentInput = " " + PREFIX_STUDENT_INDEX + INDEX_FIRST_STUDENT.getOneBased() + " "
                + PREFIX_ASSIGNMENT_INDEX + INDEX_FIRST_ASSIGNMENT + " "
                + PREFIX_ASSIGNMENT_INDEX + INDEX_SECOND_ASSIGNMENT;
        Prefix[] duplicatedAssignmentPrefixes = { PREFIX_ASSIGNMENT_INDEX };
        assertParseFailure(parser, duplicateAssignmentInput,
                Messages.getErrorMessageForDuplicatePrefixes(duplicatedAssignmentPrefixes));
    }

    @Test
    public void parse_nonNumericIndexes_throwsParseException() {
        // EP: Non-numeric student index
        String userInput = PREFIX_STUDENT_INDEX + "a " + PREFIX_ASSIGNMENT_INDEX + "1";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnmarkAssignmentCommand.MESSAGE_USAGE));

        // EP: Non-numeric assignment index
        userInput = PREFIX_STUDENT_INDEX + "1 " + PREFIX_ASSIGNMENT_INDEX + "b";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnmarkAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        // EP: Empty input
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnmarkAssignmentCommand.MESSAGE_USAGE));
    }

}

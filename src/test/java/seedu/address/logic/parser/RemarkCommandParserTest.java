package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_MATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.student.Remark;

public class RemarkCommandParserTest {

    private final RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_validArgs_returnsRemarkCommand() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_FIRST_STUDENT.getOneBased() + " "
                + PREFIX_REMARK + VALID_REMARK_MATH;
        assertParseSuccess(parser, userInput, new RemarkCommand(INDEX_FIRST_STUDENT, new Remark(VALID_REMARK_MATH)));
    }

    @Test
    public void parse_missingRemark_returnsRemarkCommand() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_FIRST_STUDENT.getOneBased() + " " + PREFIX_REMARK;
        assertParseSuccess(parser, userInput, new RemarkCommand(INDEX_FIRST_STUDENT, new Remark("")));
    }

    @Test
    public void parse_emptyRemark_returnsRemarkCommand() {
        String userInput = " " + PREFIX_STUDENT_INDEX + INDEX_FIRST_STUDENT.getOneBased() + " "
                + PREFIX_REMARK + "   "; // Empty remark
        assertParseSuccess(parser, userInput, new RemarkCommand(INDEX_FIRST_STUDENT, new Remark("")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_INDEX, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingStudentIndex_throwsParseException() {
        String userInput = " " + PREFIX_STUDENT_INDEX + PREFIX_REMARK + VALID_REMARK_MATH;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefixes_throwsParseException() {
        // Missing both prefixes
        String userInput = "1 " + VALID_REMARK_MATH;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemarkCommand.MESSAGE_USAGE));

        // Missing student prefix
        userInput = "1 " + PREFIX_REMARK + VALID_REMARK_MATH;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemarkCommand.MESSAGE_USAGE));

        // Missing remark prefix
        userInput = PREFIX_STUDENT_INDEX + "1";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        // Duplicate student prefix
        String duplicateStudentInput = " " + PREFIX_STUDENT_INDEX + INDEX_FIRST_STUDENT.getOneBased() + " "
                + PREFIX_STUDENT_INDEX + INDEX_FIRST_STUDENT.getOneBased() + " " + PREFIX_REMARK + VALID_REMARK_MATH;
        assertParseFailure(parser, duplicateStudentInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_INDEX));

        // Duplicate remark prefix
        String duplicateRemarkInput = " " + PREFIX_STUDENT_INDEX + INDEX_FIRST_STUDENT.getOneBased() + " "
                + PREFIX_REMARK + VALID_REMARK_MATH + " " + PREFIX_REMARK + VALID_REMARK_MATH;
        assertParseFailure(parser, duplicateRemarkInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK));
    }

    @Test
    public void parse_nonNumericStudentIndex_throwsParseException() {
        String userInput = PREFIX_STUDENT_INDEX + INVALID_INDEX + " " + PREFIX_REMARK + VALID_REMARK_MATH;
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        // Empty input
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemarkCommand.MESSAGE_USAGE));
    }
}

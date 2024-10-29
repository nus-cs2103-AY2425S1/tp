package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SCORE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_INDEX_STRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCORE_STRING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_INDEX_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class EditAssignmentCommandParserTest {
    private final EditAssignmentCommandParser parser = new EditAssignmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        Index expectedStudentIndex = Index.fromOneBased(Integer.parseInt(VALID_STUDENT_INDEX_STRING));
        Index expectedAssignmentIndex = Index.fromOneBased(Integer.parseInt(VALID_ASSIGNMENT_INDEX_STRING));
        int expectedScore = Integer.parseInt(VALID_SCORE_STRING);

        // with valid prefix and whitespace preamble
        String validString = " " + PREFIX_STUDENT_INDEX + VALID_STUDENT_INDEX_STRING + " "
                + PREFIX_ASSIGNMENT_INDEX + VALID_ASSIGNMENT_INDEX_STRING + " "
                + PREFIX_ASSIGNMENT_SCORE + VALID_SCORE_STRING;

        EditAssignmentCommand command = parser.parse(validString);

        assertEquals(new EditAssignmentCommand(expectedStudentIndex, expectedAssignmentIndex, expectedScore), command);
    }

    @Test
    public void parse_missingStudentIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(PREFIX_ASSIGNMENT_INDEX
                        + VALID_ASSIGNMENT_INDEX_STRING + " "
                        + PREFIX_ASSIGNMENT_SCORE + VALID_SCORE_STRING),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingAssignmentIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(PREFIX_STUDENT_INDEX + VALID_STUDENT_INDEX_STRING + " "
                        + PREFIX_ASSIGNMENT_SCORE + VALID_SCORE_STRING),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingAssignmentScore_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(PREFIX_STUDENT_INDEX + VALID_STUDENT_INDEX_STRING + " "
                        + PREFIX_ASSIGNMENT_INDEX + VALID_ASSIGNMENT_INDEX_STRING),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidStudentIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(PREFIX_STUDENT_INDEX + INVALID_INDEX + " "
                        + PREFIX_ASSIGNMENT_INDEX + VALID_ASSIGNMENT_INDEX_STRING + " "
                        + PREFIX_ASSIGNMENT_SCORE + VALID_SCORE_STRING),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidAssignmentIndex_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(PREFIX_STUDENT_INDEX + VALID_STUDENT_INDEX_STRING + " "
                        + PREFIX_ASSIGNMENT_INDEX + INVALID_INDEX + " "
                        + PREFIX_ASSIGNMENT_SCORE + VALID_SCORE_STRING),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidAssignmentScore_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(PREFIX_STUDENT_INDEX + VALID_STUDENT_INDEX_STRING + " "
                        + PREFIX_ASSIGNMENT_INDEX + VALID_ASSIGNMENT_INDEX_STRING + " "
                        + PREFIX_ASSIGNMENT_SCORE + INVALID_SCORE),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAssignmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArguments_throwsParseException() {
        String extraArgs = PREFIX_STUDENT_INDEX + VALID_STUDENT_INDEX_STRING + " "
                + PREFIX_ASSIGNMENT_INDEX + VALID_ASSIGNMENT_INDEX_STRING + " "
                + PREFIX_ASSIGNMENT_SCORE + VALID_SCORE_STRING + " extra";

        assertThrows(ParseException.class, () -> parser.parse(extraArgs),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAssignmentCommand.MESSAGE_USAGE));
    }
}

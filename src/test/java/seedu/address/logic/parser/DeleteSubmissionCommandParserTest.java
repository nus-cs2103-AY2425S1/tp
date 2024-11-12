package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBMISSION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SUBMISSION_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBMISSION_ASSIGNMENT_1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.submission.Submission.NAME_MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteSubmissionCommand;
import seedu.address.model.submission.Submission;

public class DeleteSubmissionCommandParserTest {

    private DeleteSubmissionCommandParser parser = new DeleteSubmissionCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Submission expectedSubmission = new Submission(VALID_SUBMISSION_ASSIGNMENT_1);
        assertParseSuccess(parser, SUBMISSION_DESC_ASSIGNMENT, new DeleteSubmissionCommand(expectedSubmission));
    }

    @Test
    public void parse_missingPrefix_failure() {
        assertParseFailure(parser, VALID_SUBMISSION_ASSIGNMENT_1, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteSubmissionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidParams_failure() {
        assertParseFailure(parser, INVALID_SUBMISSION_DESC, NAME_MESSAGE_CONSTRAINTS);
    }
}

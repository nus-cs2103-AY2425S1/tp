package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBMISSION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBMISSION_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.SUBMISSION_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.SUBMISSION_STATUS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBMISSION_ASSIGNMENT_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBMISSION_STATUS_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.model.submission.Submission.NAME_MESSAGE_CONSTRAINTS;
import static seedu.address.model.submission.Submission.STATUS_MESSAGE_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddSubmissionStatusCommand;
import seedu.address.model.submission.Submission;

public class AddSubmissionStatusCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddSubmissionStatusCommand.MESSAGE_USAGE);

    private AddSubmissionStatusCommandParser parser = new AddSubmissionStatusCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + SUBMISSION_DESC_ASSIGNMENT + SUBMISSION_STATUS_DESC_AMY;
        AddSubmissionStatusCommand expectedCommand = new AddSubmissionStatusCommand(INDEX_FIRST_PERSON,
                new Submission(VALID_SUBMISSION_ASSIGNMENT_1), VALID_SUBMISSION_STATUS_AMY);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, SUBMISSION_DESC_ASSIGNMENT + SUBMISSION_STATUS_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // no submission specified
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + SUBMISSION_STATUS_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // no submission status specified
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + SUBMISSION_DESC_ASSIGNMENT,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingPrefix_failure() {
        // missing submission prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + VALID_SUBMISSION_ASSIGNMENT_1
                + SUBMISSION_STATUS_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSubmissionStatusCommand.MESSAGE_USAGE));

        // missing submission status prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + SUBMISSION_DESC_ASSIGNMENT
                + VALID_SUBMISSION_STATUS_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSubmissionStatusCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidParams_failure() {
        // invalid index
        assertParseFailure(parser, "0" + SUBMISSION_DESC_ASSIGNMENT + SUBMISSION_STATUS_DESC_AMY,
                MESSAGE_INVALID_INDEX);

        // invalid submission name
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + INVALID_SUBMISSION_DESC
                + SUBMISSION_STATUS_DESC_AMY, NAME_MESSAGE_CONSTRAINTS);

        // invalid submission status
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + SUBMISSION_DESC_ASSIGNMENT
                + INVALID_SUBMISSION_STATUS_DESC, STATUS_MESSAGE_CONSTRAINTS);
    }
}

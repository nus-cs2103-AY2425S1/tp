package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EXAM_DESC_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.EXAM_SCORE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXAM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXAM_SCORE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_SCORE_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.model.exam.Exam.NAME_MESSAGE_CONSTRAINTS;
import static seedu.address.model.exam.Exam.SCORE_MESSAGE_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddExamScoreCommand;
import seedu.address.model.exam.Exam;

public class AddExamScoreCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddExamScoreCommand.MESSAGE_USAGE);

    private AddExamScoreCommandParser parser = new AddExamScoreCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + EXAM_DESC_MIDTERM + EXAM_SCORE_DESC_AMY;
        AddExamScoreCommand expectedCommand = new AddExamScoreCommand(INDEX_FIRST_PERSON, new Exam(VALID_EXAM_MIDTERM),
                VALID_EXAM_SCORE_AMY);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, EXAM_DESC_MIDTERM + EXAM_SCORE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // no exam specified
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + EXAM_SCORE_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // no exam score specified
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + EXAM_DESC_MIDTERM,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidParams_failure() {
        // invalid index
        assertParseFailure(parser, "0" + EXAM_DESC_MIDTERM + EXAM_SCORE_DESC_AMY,
                MESSAGE_INVALID_INDEX);

        // invalid exam name
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + INVALID_EXAM_DESC + EXAM_SCORE_DESC_AMY,
                NAME_MESSAGE_CONSTRAINTS);

        // invalid exam score
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + EXAM_DESC_MIDTERM + INVALID_EXAM_SCORE_DESC,
                SCORE_MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingPrefix_failure() {
        // missing exam prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + VALID_EXAM_MIDTERM + EXAM_SCORE_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // missing exam score prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + EXAM_DESC_MIDTERM + VALID_EXAM_SCORE_AMY,
                MESSAGE_INVALID_FORMAT);
    }
}

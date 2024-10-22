package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EXAM_DESC_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.EXAM_SCORE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_SCORE_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddExamScoreCommand;
import seedu.address.model.exam.Exam;

public class AddExamScoreCommandParserTest {

    private AddExamScoreCommandParser parser = new AddExamScoreCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + EXAM_DESC_MIDTERM + EXAM_SCORE_DESC_AMY;
        AddExamScoreCommand expectedCommand = new AddExamScoreCommand(INDEX_FIRST_PERSON, new Exam(VALID_EXAM_MIDTERM),
                VALID_EXAM_SCORE_AMY);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidParams_failure() {
        // missing exam prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + VALID_EXAM_MIDTERM + EXAM_SCORE_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExamScoreCommand.MESSAGE_USAGE));

        // missing exam score prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + EXAM_DESC_MIDTERM + VALID_EXAM_SCORE_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddExamScoreCommand.MESSAGE_USAGE));
    }
}

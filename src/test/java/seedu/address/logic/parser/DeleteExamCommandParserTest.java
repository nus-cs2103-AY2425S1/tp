package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.EXAM_DESC_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXAM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_MIDTERM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.exam.Exam.NAME_MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteExamCommand;
import seedu.address.model.exam.Exam;

public class DeleteExamCommandParserTest {

    private DeleteExamCommandParser parser = new DeleteExamCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Exam expectedExam = new Exam(VALID_EXAM_MIDTERM);
        assertParseSuccess(parser, EXAM_DESC_MIDTERM, new DeleteExamCommand(expectedExam));
    }

    @Test
    public void parse_invalidParams_failure() {
        assertParseFailure(parser, INVALID_EXAM_DESC, NAME_MESSAGE_CONSTRAINTS);
    }
}

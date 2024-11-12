package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EXAM_DESC_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXAM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXAM_MIDTERM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.exam.Exam.NAME_MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddExamCommand;
import seedu.address.model.exam.Exam;

public class AddExamCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddExamCommand.MESSAGE_USAGE);

    private AddExamCommandParser parser = new AddExamCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Exam expectedExam = new Exam(VALID_EXAM_MIDTERM);
        assertParseSuccess(parser, EXAM_DESC_MIDTERM, new AddExamCommand(expectedExam));
    }

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidParams_failure() {
        assertParseFailure(parser, INVALID_EXAM_DESC, NAME_MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingPrefix_failure() {
        assertParseFailure(parser, VALID_EXAM_MIDTERM, MESSAGE_INVALID_FORMAT);
    }
}

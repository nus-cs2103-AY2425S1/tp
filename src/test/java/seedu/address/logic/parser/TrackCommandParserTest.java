package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_COMPANY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_STUDENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TrackCommand;
import seedu.address.model.person.CategoryContainsKeywordPredicate;

public class TrackCommandParserTest {
    private static final CategoryContainsKeywordPredicate STUDENT_PREDICATE =
            new CategoryContainsKeywordPredicate(VALID_CATEGORY_STUDENT);
    private static final CategoryContainsKeywordPredicate COMPANY_PREDICATE =
            new CategoryContainsKeywordPredicate(VALID_CATEGORY_COMPANY);
    private TrackCommandParser parser = new TrackCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrackCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCategory_throwsParseException() {
        // Not predefined category
        assertParseFailure(parser, "unknownCategory",
                String.format(MESSAGE_INVALID_INPUT, TrackCommand.MESSAGE_INVALID_INPUT_ERROR));

        // Multiple category words
        assertParseFailure(parser, "student student",
                String.format(MESSAGE_INVALID_INPUT, TrackCommand.MESSAGE_INVALID_INPUT_ERROR));
    }


    @Test
    public void parse_validArgs_returnsTrackCommand() {
        // No leading and trailing whitespaces
        TrackCommand expectedTrackCommandStudent = new TrackCommand(STUDENT_PREDICATE);
        TrackCommand expectedTrackCommandCompany = new TrackCommand(COMPANY_PREDICATE);
        assertParseSuccess(parser, VALID_CATEGORY_STUDENT, expectedTrackCommandStudent);
        assertParseSuccess(parser, VALID_CATEGORY_COMPANY, expectedTrackCommandCompany);

        // Mixed-case input
        assertParseSuccess(parser, "StudenT", expectedTrackCommandStudent);
        assertParseSuccess(parser, "cOmpany", expectedTrackCommandCompany);

        // Leading and trailing whitespaces
        assertParseSuccess(parser, " student  ", expectedTrackCommandStudent);
        assertParseSuccess(parser, " company  ", expectedTrackCommandCompany);
    }
}

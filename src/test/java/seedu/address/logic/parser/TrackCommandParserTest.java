package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_COMPANY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TrackCommand;
import seedu.address.model.person.CategoryContainsKeywordsPredicate;

public class TrackCommandParserTest {
    private static final CategoryContainsKeywordsPredicate STUDENT_PREDICATE =
            new CategoryContainsKeywordsPredicate("student");
    private static final String userInput2 = VALID_CATEGORY_COMPANY;
    private TrackCommandParser parser = new TrackCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrackCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsTrackCommand() {
        // no leading and trailing whitespaces
        TrackCommand expectedTrackCommand =
                new TrackCommand(STUDENT_PREDICATE);
        assertParseSuccess(parser, "student", expectedTrackCommand);

    }
}

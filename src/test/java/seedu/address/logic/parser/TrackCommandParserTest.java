package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_COMPANY;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.TrackCommand;
import seedu.address.model.person.CategoryContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

import javax.sound.midi.Track;
import java.util.Arrays;

public class TrackCommandParserTest {
    private TrackCommandParser parser = new TrackCommandParser();
    private static final CategoryContainsKeywordsPredicate STUDENT_PREDICATE = new CategoryContainsKeywordsPredicate("student");
    private static final String userInput2 = VALID_CATEGORY_COMPANY;

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrackCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsTrackCommand() {
        // no leading and trailing whitespaces
        TrackCommand expectedTrackCommand =
                new TrackCommand(STUDENT_PREDICATE);
        assertParseSuccess(parser, "student", expectedTrackCommand);

    }

}
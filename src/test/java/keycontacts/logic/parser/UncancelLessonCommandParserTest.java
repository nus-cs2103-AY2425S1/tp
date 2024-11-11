package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static keycontacts.logic.commands.CommandTestUtil.VALID_DATE;
import static keycontacts.logic.commands.CommandTestUtil.VALID_DATE_DESC;
import static keycontacts.logic.parser.CommandParserTestUtil.assertParseFailure;
import static keycontacts.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import keycontacts.commons.core.index.Index;
import keycontacts.logic.commands.UncancelLessonCommand;
import keycontacts.model.lesson.CancelledLesson;
import keycontacts.model.lesson.Date;

public class UncancelLessonCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UncancelLessonCommand.MESSAGE_USAGE);
    private static final Index VALID_INDEX = Index.fromOneBased(1);
    private final UncancelLessonCommandParser parser = new UncancelLessonCommandParser();

    @Test
    public void parse_noMissingParts_success() {
        UncancelLessonCommand expectedCommand = new UncancelLessonCommand(VALID_INDEX,
                new CancelledLesson(new Date(VALID_DATE)));
        String userInput = VALID_INDEX.getOneBased() + VALID_DATE_DESC;
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_dateNotPresent_failure() {
        String userInput = VALID_INDEX.getOneBased() + "";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidDateFormat_failure() {
        String userInput = VALID_INDEX.getOneBased() + INVALID_DATE_DESC;
        assertParseFailure(parser, userInput, Date.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidIndexFormat_failure() {
        String expectedString = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UncancelLessonCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "-1" + VALID_DATE_DESC, expectedString);
        assertParseFailure(parser, "0" + VALID_DATE_DESC, expectedString);
        assertParseFailure(parser, "1 r" + VALID_DATE_DESC, expectedString);
        assertParseFailure(parser, "1 i/s" + VALID_DATE_DESC, expectedString);
    }

    @Test
    public void parse_missingPreamble_failure() {
        String expectedString = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UncancelLessonCommand.MESSAGE_USAGE);
        assertParseFailure(parser, VALID_DATE_DESC, expectedString);
    }
}


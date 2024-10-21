package seedu.edulog.logic.parser;

import static seedu.edulog.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edulog.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edulog.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.edulog.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.edulog.testutil.TypicalNames.NAME_FIRST_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.edulog.logic.commands.UnmarkCommand;
import seedu.edulog.logic.commands.UnmarkIndexCommand;
import seedu.edulog.logic.commands.UnmarkNameCommand;


/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the UnmarkCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the UnmarkCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class UnmarkCommandParserTest {

    private UnmarkCommandParser parser = new UnmarkCommandParser();

    @Test
    public void parse_validArgs_returnsUnmarkIndexCommand() {
        assertParseSuccess(parser, "1", new UnmarkIndexCommand(INDEX_FIRST_STUDENT));
    }

    @Test
    public void parse_validArgs_returnsUnmarkNameCommand() {
        assertParseSuccess(parser, NAME_FIRST_STUDENT.fullName, new UnmarkNameCommand(NAME_FIRST_STUDENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkCommand.MESSAGE_USAGE));
    }
}

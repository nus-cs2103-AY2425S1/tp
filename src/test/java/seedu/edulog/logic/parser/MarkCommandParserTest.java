package seedu.edulog.logic.parser;

import static seedu.edulog.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edulog.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edulog.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.edulog.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;
import static seedu.edulog.testutil.TypicalNames.NAME_FIRST_STUDENT;

import org.junit.jupiter.api.Test;

import seedu.edulog.logic.commands.MarkCommand;
import seedu.edulog.logic.commands.MarkIndexCommand;
import seedu.edulog.logic.commands.MarkNameCommand;


public class MarkCommandParserTest {

    private MarkCommandParser parser = new MarkCommandParser();

    @Test
    public void parse_validArgs_returnsMarkIndexCommand() {
        assertParseSuccess(parser, "1", new MarkIndexCommand(INDEX_FIRST_STUDENT));
    }

    @Test
    public void parse_validArgs_returnsMarkNameCommand() {
        assertParseSuccess(parser, NAME_FIRST_STUDENT.fullName, new MarkNameCommand(NAME_FIRST_STUDENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
    }
}

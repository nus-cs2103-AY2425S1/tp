package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommentCommand;
import seedu.address.model.person.Comment;

public class CommentCommandParserTest {

    private CommentCommandParser parser = new CommentCommandParser();

    @Test
    public void parse_validSingleArg_returnsDeleteCommand() {
        assertParseSuccess(parser, "1 c/Late", new CommentCommand(INDEX_FIRST_PERSON, new Comment("Late")));
    }

    @Test
    public void parse_validMultipleArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1 c/Always late", new CommentCommand(INDEX_FIRST_PERSON,
                new Comment("Always late")));
    }

    @Test
    public void parse_validNoArg_returnsDeleteCommand() {
        assertParseSuccess(parser, "1 c/", new CommentCommand(INDEX_FIRST_PERSON, new Comment("")));
    }

    @Test
    public void parse_invalidArgFormat_throwsParseException() {
        String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CommentCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "a", errorMessage);
    }

    @Test
    public void parse_invalidArgIndex_throwsParseException() {
        String errorMessage = MESSAGE_INVALID_INDEX;
        assertParseFailure(parser, "-1 c/Test", errorMessage);
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommentCommand;
import seedu.address.model.person.Comment;

public class CommentCommandParserTest {
    private CommentCommandParser parser = new CommentCommandParser();
    private final Comment nonEmptyComment = new Comment("Some Comment");

    @Test
    public void parse_indexSpecified_success() {
        // have comment
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_COMMENT + nonEmptyComment;
        CommentCommand expectedCommand = new CommentCommand(INDEX_FIRST_PERSON, nonEmptyComment);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CommentCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, CommentCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, CommentCommand.COMMAND_WORD + " " + nonEmptyComment, expectedMessage);
    }
}

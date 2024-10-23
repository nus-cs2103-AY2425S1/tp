package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_SET_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_SET_FRIEND_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.tag.Tag;

public class FilterCommandParserTest {
    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_oneTag_success() {
        Set<Tag> tagSet = TAG_SET_FRIEND;
        FilterCommand expectedCommand = new FilterCommand(tagSet);
        assertParseSuccess(parser, TAG_DESC_FRIEND, expectedCommand);
    }

    @Test
    public void parse_twoTags_success() {
        String userInput = TAG_DESC_FRIEND_HUSBAND;
        Set<Tag> tagSet = TAG_SET_FRIEND_HUSBAND;
        FilterCommand expectedCommand = new FilterCommand(tagSet);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        String userInput = PREFIX_TAG + "     ";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        String userInput = "friend";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        String userInput = PREFIX_TAG + TAG_DESC_FRIEND + " " + PREFIX_TAG + TAG_DESC_HUSBAND;
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                FilterCommand.MESSAGE_DUPLICATE_PREFIXES);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_nonAlphanumericTag_throwsParseException() {
        String userInput = INVALID_TAG_DESC;
        String expectedMessage = Tag.MESSAGE_CONSTRAINTS;

        assertParseFailure(parser, userInput, expectedMessage);
    }

}

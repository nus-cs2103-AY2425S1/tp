package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_WEDDING3;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TagAddCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Tag;

public class TagAddCommandParserTest {
    private TagAddCommandParser parser = new TagAddCommandParser();

    @Test
    public void parse_nameSpecified_success() {
        // have tag
        String targetName = VALID_NAME_AMY;
        String userInput = " " + PREFIX_NAME + targetName + " " + PREFIX_TAG + VALID_TAG_WEDDING3;
        Tag stubTag = new Tag(VALID_TAG_WEDDING3);
        Name stubName = new Name(VALID_NAME_AMY);
        Set<Tag> stubTagList = new HashSet<>();
        stubTagList.add(stubTag);
        TagAddCommand expectedCommand = new TagAddCommand(stubName, stubTagList);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagAddCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, TagAddCommand.COMMAND_WORD, expectedMessage);
        // no name
        assertParseFailure(parser, TagAddCommand.COMMAND_WORD + " t/" + VALID_TAG_WEDDING3, expectedMessage);
        // no tag
        assertParseFailure(parser, TagAddCommand.COMMAND_WORD + " n/" + VALID_NAME_AMY, expectedMessage);
    }
}

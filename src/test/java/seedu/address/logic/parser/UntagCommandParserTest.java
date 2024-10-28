package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_TOO_MANY_INDEXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.tag.Tag.MAX_CHARACTER_LENGTH;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalTags.BRIDES_SIDE;
import static seedu.address.testutil.TypicalTags.FRIENDS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UntagCommand;
import seedu.address.model.tag.Tag;

public class UntagCommandParserTest {

    private UntagCommandParser parser = new UntagCommandParser();

    @Test
    public void parse_validArgs_success() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_TAG + FRIENDS.getTagName();
        Set<Tag> tags = new HashSet<>();
        List<Index> indexes = new ArrayList<>();
        tags.add(FRIENDS);
        indexes.add(INDEX_FIRST_PERSON);
        UntagCommand expectedCommand = new UntagCommand(indexes, tags);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingTag_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_TAG;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + FRIENDS.getTagName();
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        String userInput = " " + PREFIX_TAG + FRIENDS.getTagName();
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyTag_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_TAG + " ";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_tooLongTag_throwsParseException() {
        String longTag = "veryveryveryveryveryveryveryveryveryveryverylongtagthatshouldexceedthecharacterlimit";
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_TAG + longTag;
        String expectedMessage = String.format(Messages.MESSAGE_INPUT_LENGTH_EXCEEDED, MAX_CHARACTER_LENGTH);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "a " + PREFIX_TAG + FRIENDS;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_tooManyIndexes_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " "
                + INDEX_SECOND_PERSON.getOneBased() + " "
                + INDEX_THIRD_PERSON.getOneBased()
                + " 4"
                + " 5"
                + " 6"
                + " 7"
                + " 8"
                + " 9"
                + " 10"
                + " 11 "
                + PREFIX_TAG + BRIDES_SIDE.getTagName();
        assertParseFailure(parser, userInput, String.format(MESSAGE_TOO_MANY_INDEXES, Index.MAX_INDEXES));
    }
}

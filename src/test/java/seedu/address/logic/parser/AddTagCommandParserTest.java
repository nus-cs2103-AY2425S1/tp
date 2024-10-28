package seedu.address.logic.parser;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AddTagCommand.MESSAGE_NOT_ADD;
import static seedu.address.logic.commands.AddTagCommand.MESSAGE_USAGE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.model.tag.Tag;

public class AddTagCommandParserTest {
    private AddTagCommandParser parser = new AddTagCommandParser();

    @Test
    public void parse_invalidFormat_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);
        assertParseFailure(parser, "abcs" + TAG_DESC_HUSBAND, expectedMessage);
    }

    @Test
    public void parse_invalidTagCharacter_throwsParseException() {
        String expectedMessage = String.format(Tag.MESSAGE_CONSTRAINTS);

        // one invalid tag
        assertParseFailure(parser, "1 t/" + INVALID_TAG_DESC, expectedMessage);

        // two invalid tags
        assertParseFailure(parser, "2 t/" + INVALID_TAG_DESC + " t/c#lassmate", expectedMessage);
    }

    @Test
    public void parse_emptyTag_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_NOT_ADD);
        assertParseFailure(parser, "2", expectedMessage);
    }

    @Test
    public void parse_validTagAndIndex_success() {
        Index index = Index.fromOneBased(3);
        Set<Tag> tags = new HashSet<>(List.of(new Tag("walkie-talkie"), new Tag("Samsung")));
        AddTagCommand expectedCommand = new AddTagCommand(index, tags);
        assertParseSuccess(parser, "3 t/     walkie-talkie   \t\n    t/Samsung", expectedCommand);
    }
}

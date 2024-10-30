package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.personcommands.LinkPersonCommand;
import seedu.address.model.types.common.Name;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOOK_FAIR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class LinkCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkPersonCommand.MESSAGE_USAGE);

    private LinkCommandParser parser = new LinkCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_BOOK_FAIR, MESSAGE_INVALID_FORMAT);

        // no event name specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // invalid index
        assertParseFailure(parser, "a ev/" + VALID_NAME_BOOK_FAIR, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidEventName_failure() {
        // Empty event name
        assertParseFailure(parser, "1 ev/ ", Name.MESSAGE_CONSTRAINTS);

        // Invalid event name
        assertParseFailure(parser, "1 ev/!@#$%^&*()", Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Name expectedName = new Name(VALID_NAME_BOOK_FAIR);
        String userInput = "1 ev/" + expectedName;
        LinkPersonCommand expectedCommand = new LinkPersonCommand(Index.fromOneBased(1), expectedName);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

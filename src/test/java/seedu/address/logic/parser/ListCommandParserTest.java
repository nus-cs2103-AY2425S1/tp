package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListBothCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListOwnerCommand;
import seedu.address.logic.commands.ListPetCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the ListCommand code. For example, inputs "list people" and "list persons" take the
 * same path through the ListCommand, and therefore we test only one of them.
 */
public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnsListPetCommand() {
        assertParseSuccess(parser, "pets", new ListPetCommand());
    }

    @Test
    public void parse_validArgs_returnsListOwnerCommand() {
        assertParseSuccess(parser, "owners", new ListOwnerCommand());
    }

    @Test
    public void parse_validArgs_returnsListCombinedCommand() {
        assertParseSuccess(parser, "both", new ListBothCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }
}

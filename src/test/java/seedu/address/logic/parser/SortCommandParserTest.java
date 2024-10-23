package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortOwnerCommand;
import seedu.address.logic.commands.SortPetCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the SortCommand code. For example, inputs "sort people" and "sort persons" take the
 * same path through the SortCommand, and therefore we test only one of them.
 */
public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortPetCommand() {
        assertParseSuccess(parser, "pets", new SortPetCommand());
    }

    @Test
    public void parse_validArgs_returnsSortOwnersCommand() {
        assertParseSuccess(parser, "owners", new SortOwnerCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}

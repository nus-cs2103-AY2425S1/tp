package seedu.address.logic.parser.clientcommandparsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.clientcommands.MoreInfoCommand;

public class MoreInfoCommandParserTest {
    private MoreInfoCommandParser parser = new MoreInfoCommandParser();

    @Test
    public void parse_validArgs_returnsMoreInfoCommand() {
        assertParseSuccess(parser, "Alice Pauline", new MoreInfoCommand(ALICE.getName()));
    }

    @Test
    public void parse_missingField_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MoreInfoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "$$", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MoreInfoCommand.MESSAGE_USAGE));
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.MoreInfoCommand;

public class MoreInfoCommandParserTest {
    private MoreInfoCommandParser parser = new MoreInfoCommandParser();

    @Test
    public void parse_validArgs_returnsMoreInfoCommand() {
        assertParseSuccess(parser, PREFIX_NAME + "Alice Pauline", new MoreInfoCommand(ALICE.getName()));
    }

    @Test
    public void parse_missingField_throwsParseException() {
        assertParseFailure(parser, PREFIX_NAME.toString(), Messages.MISSING_CLIENT_NAME);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MoreInfoCommand.MESSAGE_USAGE));
    }
}

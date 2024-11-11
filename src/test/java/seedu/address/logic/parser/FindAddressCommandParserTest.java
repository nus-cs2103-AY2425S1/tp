package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindAddressCommand;
import seedu.address.model.person.AddressContainsKeywordsPredicate;

public class FindAddressCommandParserTest {

    private FindAddressCommandParser parser = new FindAddressCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindAddressCommand() {
        // no leading and trailing whitespaces
        FindAddressCommand expectedFindAddressCommand =
                new FindAddressCommand(new AddressContainsKeywordsPredicate("tampines blk"));
        assertParseSuccess(parser, "tampines blk", expectedFindAddressCommand);
    }
}

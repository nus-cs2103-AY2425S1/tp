package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.model.person.PhoneBeginsWithKeywordPredicate;

public class FindPhoneCommandParserTest {

    private FindPhoneCommandParser parser = new FindPhoneCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindPhoneCommand expectedFindCommand =
                new FindPhoneCommand(new PhoneBeginsWithKeywordPredicate("9876"));
        assertParseSuccess(parser, "9876", expectedFindCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // multiple inputs
        assertParseFailure(parser, "9876 9678",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));


        // not number
        assertParseFailure(parser, "string",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));


        // non-number in betweennumber
        assertParseFailure(parser, "987n9678",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
    }

}

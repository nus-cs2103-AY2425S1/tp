package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindPhoneNumberCommand;
import seedu.address.model.person.PhoneNumberContainsKeywordPredicate;

public class FindPhoneNumberCommandParserTest {

    private FindPhoneNumberCommandParser parser = new FindPhoneNumberCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneNumberCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindPhoneNumberCommand expectedFindPhoneNumber =
                new FindPhoneNumberCommand(new PhoneNumberContainsKeywordPredicate(Arrays.asList("12345678", "987654321")));
        assertParseSuccess(parser, "12345678 987654321", expectedFindPhoneNumber);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n 12345678 \n \t 987654321  \t", expectedFindPhoneNumber);
    }

}

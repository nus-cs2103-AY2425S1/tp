package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TelegramHandleContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validName_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validSingleTelegramHandle_returnsFindCommand() {
        // Expected command with Telegram handle predicate
        FindCommand expectedFindCommand =
            new FindCommand(new TelegramHandleContainsKeywordsPredicate(List.of("@amy123")));

        // Test with the correct input format
        assertParseSuccess(parser, "/h @amy123", expectedFindCommand);
    }

    @Test
    public void parse_validMultipleTelegramHandle_returnsFindCommand() {
        // Expected command with Telegram handle predicate
        FindCommand expectedFindCommand =
            new FindCommand(new TelegramHandleContainsKeywordsPredicate(Arrays.asList("@amy123", "@bob321")));

        // Test with the correct input format
        assertParseSuccess(parser, "/h @amy123 @bob321", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "/h \n @amy123 \n \t @bob321  \t", expectedFindCommand);
    }
}

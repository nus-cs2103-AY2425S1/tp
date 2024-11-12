package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindTelegramHandleCommand;
import seedu.address.model.person.TelegramHandleContainsKeywordsPredicate;

public class FindTelegramHandleCommandParserTest {

    private FindTelegramHandleCommandParser parser = new FindTelegramHandleCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindTelegramHandleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindTelegramHandleCommand() {
        // no leading and trailing whitespaces
        FindTelegramHandleCommand expectedfindTelegramHandleCommand =
            new FindTelegramHandleCommand(
                    new TelegramHandleContainsKeywordsPredicate(Arrays.asList("berner", "alice")));
        assertParseSuccess(parser, "berner alice", expectedfindTelegramHandleCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n berner \n \t alice  \t", expectedfindTelegramHandleCommand);
    }
}

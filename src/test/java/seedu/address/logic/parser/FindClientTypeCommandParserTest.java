package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindClientTypeCommand;
import seedu.address.model.person.ClientTypeContainsKeywordsPredicate;

/**
 * Contains unit tests for {@code FindClientTypeCommandParser}.
 */
public class FindClientTypeCommandParserTest {

    private FindClientTypeCommandParser parser = new FindClientTypeCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindClientTypeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindClientTypeCommand() {
        // no leading and trailing whitespaces
        FindClientTypeCommand expectedFindClientTypeCommand =
                new FindClientTypeCommand(new ClientTypeContainsKeywordsPredicate(List.of("Investment")));
        assertParseSuccess(parser, "Investment", expectedFindClientTypeCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // No inputs
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindClientTypeCommand.MESSAGE_USAGE));


        // Special Characters
        assertParseFailure(parser, "$12",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindClientTypeCommand.MESSAGE_USAGE));
    }

}

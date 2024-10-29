package seedu.address.logic.parser.buyer;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BUYER_FIND_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_FIND_ALICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.buyer.FindCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.buyer.FindCommand;
import seedu.address.model.buyer.Name;
import seedu.address.model.buyer.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));

        assertParseSuccess(parser, NAME_DESC_FIND_ALICE_BOB, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_FIND_ALICE_BOB, expectedFindCommand);
    }

    @Test
    public void parse_invalidNameFormat_failParse() {
        assertParseFailure(parser, INVALID_BUYER_FIND_DESC,
                String.format(Name.MESSAGE_CONSTRAINTS, MESSAGE_USAGE));
    }
}

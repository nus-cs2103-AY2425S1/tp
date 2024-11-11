package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NricContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);


        // multiple patients with NRIC names
        expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("S1234567Z", "G1234567Z")));
        assertParseSuccess(parser, "S1234567Z G1234567Z", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\t S1234567Z  \n G1234567Z     ", expectedFindCommand);


        // NRIC name followed by a normal name
        expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("S1234567Z", "Alice")));
        assertParseSuccess(parser, "S1234567Z Alice", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\t S1234567Z  \n Alice     ", expectedFindCommand);

        // normal name followed by a NRIC name
        expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "S1234567Z")));
        assertParseSuccess(parser, "Alice S1234567Z", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "Alice  \t S1234567Z  \n", expectedFindCommand);


        // normal name followed by multiple NRIC names
        expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "S1234567Z", "G1234567Z")));
        assertParseSuccess(parser, "Alice S1234567Z G1234567Z", expectedFindCommand);


        // name containing nric and other characters
        expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("AliceS1234567Z")));
        assertParseSuccess(parser, "AliceS1234567Z", expectedFindCommand);

    }

    @Test
    public void parse_validNricArg_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NricContainsKeywordsPredicate(Arrays.asList("S1234567Z")));
        assertParseSuccess(parser, "S1234567Z", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n S1234567Z \n \t", expectedFindCommand);
    }

}

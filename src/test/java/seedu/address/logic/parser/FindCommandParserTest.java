package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.ClassIdContainsKeywordsPredicate;
import seedu.address.model.person.NameAndClassIdContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void testParseMethodWithEmptyArg() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }



    @Test
    public void testParseMethodWithvalidArgs() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "n/Alice Bob", expectedFindCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, "n/ \n Alice \n \t Bob  \t", expectedFindCommand);
    }


    @Test
    public void testParseMethodWithInvalidArgsWithName() {
        // missing keywords after n/
        assertParseFailure(parser, "n/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // missing both prefixes
        assertParseFailure(parser, "Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void testParseMethodWithValidArgsWithClassId() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new ClassIdContainsKeywordsPredicate(Arrays.asList("1", "2")));
        assertParseSuccess(parser, "c/1 2", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "c/ \n 1 \n \t 2  \t", expectedFindCommand);
    }

    @Test
    public void testParseMethodValidArgsWithNameAndClassId() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameAndClassIdContainsKeywordsPredicate(Arrays.asList("Bob", "Alice"),
                        Arrays.asList("1", "2")));
        assertParseSuccess(parser, "n/Bob Alice c/1 2", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "n/ Bob \n Alice c/ \n 1 \n \t 2  \t", expectedFindCommand);
    }



}

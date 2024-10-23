package tutorease.address.logic.parser;

import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorease.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorease.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tutorease.address.logic.commands.FindContactCommand;
import tutorease.address.model.person.NameContainsKeywordsPredicate;

public class FindContactCommandParserTest {

    private FindContactCommandParser parser = new FindContactCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindContactCommand expectedFindCommand =
                new FindContactCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

}

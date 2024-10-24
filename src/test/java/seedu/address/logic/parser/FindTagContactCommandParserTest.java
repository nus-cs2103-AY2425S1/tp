package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindTagContactCommand;
import seedu.address.model.person.TagContactContainsKeywordPredicate;

public class FindTagContactCommandParserTest {

    private FindTagContactCommandParser parser = new FindTagContactCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindTagContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindTagContactCommand expectedFindTagContactCommand =
                new FindTagContactCommand(new TagContactContainsKeywordPredicate(Arrays.asList("owes", "friends")));
        assertParseSuccess(parser, "owes friends", expectedFindTagContactCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n owes \n \t friends  \t", expectedFindTagContactCommand);
    }

}

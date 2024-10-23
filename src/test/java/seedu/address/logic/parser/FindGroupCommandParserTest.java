package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUERY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.findcommands.FindGroupCommand;
import seedu.address.logic.parser.findcommands.FindGroupCommandParser;
import seedu.address.model.group.GroupNameContainsKeywordsPredicate;

public class FindGroupCommandParserTest {

    private FindGroupCommandParser parser = new FindGroupCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindGroupCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindGroupCommand() {
        // no leading and trailing whitespace
        FindGroupCommand expectedFindGroupCommand =
            new FindGroupCommand(new GroupNameContainsKeywordsPredicate(Arrays.asList("Group TD")));
        assertParseSuccess(parser, " " + PREFIX_QUERY + "Group TD", expectedFindGroupCommand);
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUERY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.findcommands.FindTaskCommand;
import seedu.address.logic.parser.findcommands.FindTaskCommandParser;
import seedu.address.model.task.TaskNameContainsKeywordsPredicate;

public class FindTaskCommandParserTest {

    private FindTaskCommandParser parser = new FindTaskCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindTaskCommand() {
        FindTaskCommand expectedFindTaskCommand =
            new FindTaskCommand(new TaskNameContainsKeywordsPredicate(Arrays.asList("tP")));
        assertParseSuccess(parser, " " + PREFIX_QUERY + "tP", expectedFindTaskCommand);
    }
}

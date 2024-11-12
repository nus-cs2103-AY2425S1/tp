package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindProjectCommand;
import seedu.address.model.project.ProjectNameContainsKeywordsPredicate;


public class FindProjectCommandParserTest {

    private FindProjectCommandParser parser = new FindProjectCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindProjectCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindProjectCommand() {
        // no leading and trailing whitespaces
        FindProjectCommand expectedFindProjectCommand =
                new FindProjectCommand(new ProjectNameContainsKeywordsPredicate(Arrays.asList("Alpha", "Beta")));
        assertParseSuccess(parser, "Alpha Beta", expectedFindProjectCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alpha \n \t Beta  \t", expectedFindProjectCommand);
    }

}

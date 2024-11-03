package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.awt.GraphicsEnvironment;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.FunctionalBrowser;
import seedu.address.logic.commands.GitHubCommand;
import seedu.address.model.person.Name;

public class GitHubCommandParserTest {
    private GitHubCommandParser parser = new GitHubCommandParser();
    private FunctionalBrowser functionalBrowser =
            GraphicsEnvironment.isHeadless() ? null : FunctionalBrowser.getDesktop();

    @Test
    public void parse_validArgs_returnsGitHubCommand() {
        assertParseSuccess(parser, NAME_DESC_BOB, new GitHubCommand(new Name(VALID_NAME_BOB), functionalBrowser));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //Name prefix not present
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GitHubCommand.MESSAGE_USAGE));

        //invalid preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GitHubCommand.MESSAGE_USAGE));

        //Empty String parsed
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GitHubCommand.MESSAGE_USAGE));

    }
}

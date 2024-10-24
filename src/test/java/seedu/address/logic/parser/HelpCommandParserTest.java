package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_HELP_KEYWORD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.HelpCommand;

public class HelpCommandParserTest {
    private final HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_validKeywords_success() {
        assertParseSuccess(parser, "", new HelpCommand(""));
        assertParseSuccess(parser, "   ", new HelpCommand(""));
        assertParseSuccess(parser, "add", new HelpCommand("add"));
        assertParseSuccess(parser, "delete", new HelpCommand("delete"));
        assertParseSuccess(parser, "edit", new HelpCommand("edit"));
        assertParseSuccess(parser, "find", new HelpCommand("find"));
        assertParseSuccess(parser, "clear", new HelpCommand("clear"));
        assertParseSuccess(parser, "help", new HelpCommand("help"));
        assertParseSuccess(parser, "list", new HelpCommand("list"));
        assertParseSuccess(parser, "exit", new HelpCommand("exit"));
        assertParseSuccess(parser, " add", new HelpCommand("add"));
        assertParseSuccess(parser, "add ", new HelpCommand("add"));
        assertParseSuccess(parser, " add ", new HelpCommand("add"));
        assertParseSuccess(parser, "ADD", new HelpCommand("add"));
    }

    @Test
    public void parse_invalidKeyWords_fail() {
        assertParseFailure(parser, "hello",
            String.format(MESSAGE_INVALID_HELP_KEYWORD, "hello"));
        assertParseFailure(parser, "helpadd",
            String.format(MESSAGE_INVALID_HELP_KEYWORD, "helpadd"));
    }
}

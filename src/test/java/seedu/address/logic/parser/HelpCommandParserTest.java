package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.HelpCommand;

public class HelpCommandParserTest {

    private HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_emptyArg_returnsHelpCommand() {
        HelpCommand expectedHelpCommand = new HelpCommand();
        assertParseSuccess(parser, "", expectedHelpCommand);
    }

    @Test
    public void parse_args_returnsHelpCommandWithKeyword() {
        HelpCommand expectedHelpCommandAddKeyword = new HelpCommand("add");
        HelpCommand expectedHelpCommandAddfKeyword = new HelpCommand("addf");
        HelpCommand expectedHelpCommandApptKeyword = new HelpCommand("appt");
        HelpCommand expectedHelpCommandDeleteKeyword = new HelpCommand("delete");

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "add", expectedHelpCommandAddKeyword);
        assertParseSuccess(parser, "addf", expectedHelpCommandAddfKeyword);
        assertParseSuccess(parser, "appt", expectedHelpCommandApptKeyword);
        assertParseSuccess(parser, "delete", expectedHelpCommandDeleteKeyword);

        // multiple whitespaces before and after keyword
        assertParseSuccess(parser, " \n add \n", expectedHelpCommandAddKeyword);
        assertParseSuccess(parser, " \n addf \n", expectedHelpCommandAddfKeyword);
        assertParseSuccess(parser, " \n appt \n", expectedHelpCommandApptKeyword);
        assertParseSuccess(parser, " \n delete \n", expectedHelpCommandDeleteKeyword);

    }

}

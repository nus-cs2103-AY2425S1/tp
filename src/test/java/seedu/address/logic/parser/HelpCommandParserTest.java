package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
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
        HelpCommand expectedHelpCommandBookApptKeyword = new HelpCommand("bookappt");
        HelpCommand expectedHelpCommandClearKeyword = new HelpCommand("clear");
        HelpCommand expectedHelpCommandDeleteApptKeyword = new HelpCommand("deleteappt");
        HelpCommand expectedHelpCommandDeleteKeyword = new HelpCommand("delete");
        HelpCommand expectedHelpCommandEditKeyword = new HelpCommand("edit");
        HelpCommand expectedHelpCommandExitKeyword = new HelpCommand("exit");
        HelpCommand expectedHelpCommandFilterKeyword = new HelpCommand("filter");
        HelpCommand expectedHelpCommandHomeKeyword = new HelpCommand("home");
        HelpCommand expectedHelpCommandViewKeyword = new HelpCommand("view");

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "add", expectedHelpCommandAddKeyword);
        assertParseSuccess(parser, "addf", expectedHelpCommandAddfKeyword);
        assertParseSuccess(parser, "bookappt", expectedHelpCommandBookApptKeyword);
        assertParseSuccess(parser, "clear", expectedHelpCommandClearKeyword);
        assertParseSuccess(parser, "deleteappt", expectedHelpCommandDeleteApptKeyword);
        assertParseSuccess(parser, "delete", expectedHelpCommandDeleteKeyword);
        assertParseSuccess(parser, "edit", expectedHelpCommandEditKeyword);
        assertParseSuccess(parser, "exit", expectedHelpCommandExitKeyword);
        assertParseSuccess(parser, "filter", expectedHelpCommandFilterKeyword);
        assertParseSuccess(parser, "home", expectedHelpCommandHomeKeyword);
        assertParseSuccess(parser, "view", expectedHelpCommandViewKeyword);

        // multiple whitespaces before and after keyword
        assertParseSuccess(parser, " \n add \n", expectedHelpCommandAddKeyword);
        assertParseSuccess(parser, " \n addf \n", expectedHelpCommandAddfKeyword);
        assertParseSuccess(parser, " \n bookappt \n", expectedHelpCommandBookApptKeyword);
        assertParseSuccess(parser, " \n clear \n", expectedHelpCommandClearKeyword);
        assertParseSuccess(parser, " \n deleteappt \n", expectedHelpCommandDeleteApptKeyword);
        assertParseSuccess(parser, " \n delete \n", expectedHelpCommandDeleteKeyword);
        assertParseSuccess(parser, " \n edit \n", expectedHelpCommandEditKeyword);
        assertParseSuccess(parser, " \n exit \n", expectedHelpCommandExitKeyword);
        assertParseSuccess(parser, " \n filter \n", expectedHelpCommandFilterKeyword);
        assertParseSuccess(parser, " \n home \n", expectedHelpCommandHomeKeyword);
        assertParseSuccess(parser, " \n view \n", expectedHelpCommandViewKeyword);
    }

    @Test
    public void parse_typoArgs_returnErrorMessage() {
        String errorMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "addfwew", errorMessage);
        assertParseFailure(parser, "addfgave", errorMessage);
        assertParseFailure(parser, "bookapptasg", errorMessage);
        assertParseFailure(parser, "clearasfg", errorMessage);
        assertParseFailure(parser, "deleteapptt", errorMessage);
        assertParseFailure(parser, "deletewgvbj", errorMessage);
        assertParseFailure(parser, "editwrg", errorMessage);
        assertParseFailure(parser, "exitgswda", errorMessage);
        assertParseFailure(parser, "filterr", errorMessage);
        assertParseFailure(parser, "homew", errorMessage);
        assertParseFailure(parser, "viewgwar", errorMessage);

    }

}

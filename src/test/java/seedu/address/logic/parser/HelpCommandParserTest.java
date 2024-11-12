package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GetCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.StatisticsCommand;


public class HelpCommandParserTest {

    private HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_validArgs_returnsHelpCommand() {
        assertParseSuccess(parser, "", new HelpCommand());
        assertParseSuccess(parser, "add", new HelpCommand(AddCommand.MESSAGE_USAGE));
        assertParseSuccess(parser, "clear", new HelpCommand(ClearCommand.MESSAGE_USAGE));
        assertParseSuccess(parser, "delete", new HelpCommand(DeleteCommand.MESSAGE_USAGE));
        assertParseSuccess(parser, "edit", new HelpCommand(EditCommand.MESSAGE_USAGE));
        assertParseSuccess(parser, "exit", new HelpCommand(ExitCommand.MESSAGE_USAGE));
        assertParseSuccess(parser, "find", new HelpCommand(FindCommand.MESSAGE_USAGE));
        assertParseSuccess(parser, "get", new HelpCommand(GetCommand.MESSAGE_USAGE));
        assertParseSuccess(parser, "help", new HelpCommand(HelpCommand.MESSAGE_USAGE));
        assertParseSuccess(parser, "list", new HelpCommand(ListCommand.MESSAGE_USAGE));
        assertParseSuccess(parser, "statistics", new HelpCommand(StatisticsCommand.MESSAGE_USAGE));
        // multiple whitespaces
        assertParseSuccess(parser, "   add   ", new HelpCommand(AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "invalid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "add edit",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }
}

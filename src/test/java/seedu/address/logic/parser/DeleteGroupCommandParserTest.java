package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.model.group.GroupName;

public class DeleteGroupCommandParserTest {

    private DeleteGroupCommandParser parser = new DeleteGroupCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteGroupCommand() {
        GroupName validGroupName = new GroupName("Study Group");
        DeleteGroupCommand expectedCommand = new DeleteGroupCommand(validGroupName);

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "Study Group", expectedCommand);

        // multiple whitespaces
        assertParseSuccess(parser, " \n Study Group  ", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Empty argument
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE));

        // Invalid argument format (non-alphanumeric)
        assertParseFailure(parser, "Invalid@Group",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE));
    }
}

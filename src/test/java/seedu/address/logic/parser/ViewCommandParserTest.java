package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.model.person.Name;

public class ViewCommandParserTest {
    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validIndex_returnsViewCommand() {
        // index
        String userInput = "1";
        Index index = Index.fromOneBased(1);
        ViewCommand expected = new ViewCommand(index);
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_validName_returnsViewCommand() {
        // name
        String userInput = "Alice Pauline";
        Name name = new Name("Alice Pauline");
        ViewCommand expected = new ViewCommand(name);
        assertParseSuccess(parser, userInput, expected);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // index
        String userInput = "-1";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidName_throwsParseException() {
        // index
        String userInput = "Alice /";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        // index
        String userInput = "    ";
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
    }
}

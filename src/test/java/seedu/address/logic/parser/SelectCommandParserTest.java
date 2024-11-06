package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.SelectCommand;

public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "    ", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                SelectCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        int firstIndex = 1;
        int secondIndex = 6;
        SelectCommand expectedCommand =
                new SelectCommand(
                        List.of(Index.fromOneBased(firstIndex), Index.fromOneBased(secondIndex)));

        //With leading and ending whitespaces
        assertParseSuccess(parser, String.format(" %d %d ", firstIndex, secondIndex), expectedCommand);

        //With multiple white spaces between the indexes
        assertParseSuccess(parser, String.format("%d    %d", firstIndex, secondIndex), expectedCommand);

        //With leading/ending whitespaces and whitespaces between the indexes
        assertParseSuccess(parser, String.format(" %d   %d ", firstIndex, secondIndex), expectedCommand);
    }
}

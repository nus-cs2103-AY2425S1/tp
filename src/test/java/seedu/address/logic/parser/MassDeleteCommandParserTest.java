package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MassDeleteCommand;

public class MassDeleteCommandParserTest {

    private MassDeleteCommandParser parser = new MassDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsMassDeleteCommand() {
        List<Index> expectedIndices = Arrays.asList(INDEX_FIRST, INDEX_SECOND, INDEX_THIRD);
        assertParseSuccess(parser, "1 2 3", new MassDeleteCommand(expectedIndices, Collections.emptyList()));
    }

    @Test
    public void parse_invalidArgs_returnsMassDeleteCommandWithInvalidInputs() {
        // Test with non-numeric input that should result in a command with invalid inputs
        List<String> expectedInvalidInputs = Arrays.asList("a", "b", "c");
        MassDeleteCommand expectedCommand = new MassDeleteCommand(Collections.emptyList(), expectedInvalidInputs);
        assertParseSuccess(parser, "a b c", expectedCommand);

        // Test with mixed valid and invalid input
        List<Index> expectedIndices = Arrays.asList(INDEX_FIRST, INDEX_THIRD);
        List<String> singleInvalidInput = Collections.singletonList("a");
        MassDeleteCommand mixedInputCommand = new MassDeleteCommand(expectedIndices, singleInvalidInput);
        assertParseSuccess(parser, "1 a 3", mixedInputCommand);
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        // Test with empty input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassDeleteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeIndex_treatedAsInvalidInput() {
        // Test with negative index
        List<String> expectedInvalidInputs = Collections.singletonList("-1");
        MassDeleteCommand expectedCommand = new MassDeleteCommand(Collections.emptyList(), expectedInvalidInputs);
        assertParseSuccess(parser, "-1", expectedCommand);
    }
}

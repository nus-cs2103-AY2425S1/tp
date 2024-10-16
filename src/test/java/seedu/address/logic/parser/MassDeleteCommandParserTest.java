package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

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
        List<Index> expectedIndices = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        assertParseSuccess(parser, "1 2 3", new MassDeleteCommand(expectedIndices, Collections.emptyList()));
    }

    @Test
    public void parse_invalidArgs_returnsMassDeleteCommandWithInvalidInputs() {
        // Test with non-numeric input that should result in a command with invalid inputs
        List<String> expectedInvalidInputsForABC = Arrays.asList("a", "b", "c");
        MassDeleteCommand expectedCommandForABC = new MassDeleteCommand(Collections.emptyList(), expectedInvalidInputsForABC);
        assertParseSuccess(parser, "a b c", expectedCommandForABC);

        // Test with mixed valid and invalid input
        List<Index> expectedIndices = Arrays.asList(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);
        List<String> expectedInvalidInputs = Collections.singletonList("a");
        MassDeleteCommand expectedCommand = new MassDeleteCommand(expectedIndices, expectedInvalidInputs);
        assertParseSuccess(parser, "1 a 3", expectedCommand);

        // Test with empty input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassDeleteCommand.MESSAGE_USAGE));
    }
}

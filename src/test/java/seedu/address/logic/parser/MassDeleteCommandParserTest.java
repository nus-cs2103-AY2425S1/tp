package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MassDeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the MassDeleteCommand code.
 */
public class MassDeleteCommandParserTest {

    private MassDeleteCommandParser parser = new MassDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsMassDeleteCommand() {
        List<Index> expectedIndices = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        assertParseSuccess(parser, "1 2 3", new MassDeleteCommand(expectedIndices));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test with non-numeric input
        assertParseFailure(parser, "a b c",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassDeleteCommand.MESSAGE_USAGE));

        // Test with mixed valid and invalid input
        assertParseFailure(parser, "1 a 3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassDeleteCommand.MESSAGE_USAGE));

        // Test with empty input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassDeleteCommand.MESSAGE_USAGE));
    }
}

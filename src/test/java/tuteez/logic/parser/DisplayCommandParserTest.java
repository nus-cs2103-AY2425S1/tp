package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import tuteez.logic.commands.DisplayCommand;
import tuteez.model.person.Name;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DisplayCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DisplayCommandParserTest {

    private DisplayCommandParser parser = new DisplayCommandParser();

    @Test
    public void parse_validArgsWithIndex_returnsDisplayCommand() {
        assertParseSuccess(parser, "1", new DisplayCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validArgsWithName_returnsDisplayCommand() {
        assertParseSuccess(parser, "Alice", new DisplayCommand(new Name("Alice")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DisplayCommand.MESSAGE_USAGE));
    }
}

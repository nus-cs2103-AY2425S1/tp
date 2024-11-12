package tuteez.logic.parser;

import static tuteez.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tuteez.logic.Messages.MESSAGE_INVALID_PERSON_INDEX_FORMAT;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tuteez.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static tuteez.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import tuteez.logic.commands.DeleteCommand;
import tuteez.model.person.Name;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgsWithIndex_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validArgsWithName_returnsDeleteCommand() {
        assertParseSuccess(parser, "Alice", new DeleteCommand(new Name("Alice")));
    }

    @Test
    public void parse_invalidArgsWithIndex_throwsParseException() {
        assertParseFailure(parser, "-1", (String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                String.format(MESSAGE_INVALID_PERSON_INDEX_FORMAT, "-1"))));
    }

    @Test
    public void parse_invalidArgsWithName_throwsParseException() {
        assertParseFailure(parser, "alice*", Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        assertParseFailure(parser, "0 li/", (String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE)));
    }
}

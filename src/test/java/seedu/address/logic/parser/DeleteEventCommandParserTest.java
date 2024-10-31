package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteEventByIndexCommand;
import seedu.address.logic.commands.DeleteEventByNameCommand;
import seedu.address.logic.commands.DeleteEventCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteEventCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteEventCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteEventCommandParserTest {

    private DeleteEventCommandParser parser = new DeleteEventCommandParser();

    @Test
    public void parse_validIndex_returnsDeleteEventCommand() {
        assertParseSuccess(parser, "1", new DeleteEventByIndexCommand(INDEX_FIRST));
    }

    @Test
    public void parse_validEventName_returnsDeleteEventCommand() {
        assertParseSuccess(parser, MEETING.getName().toString(), new DeleteEventByNameCommand(MEETING.getName()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(
                parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
    }
}

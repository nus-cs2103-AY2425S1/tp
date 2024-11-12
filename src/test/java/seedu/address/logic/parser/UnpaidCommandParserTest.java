package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UnpaidCommand;


public class UnpaidCommandParserTest {
    private UnpaidCommandParser parser = new UnpaidCommandParser();

    @Test
    public void parse_validArgs_returnsPaidCommand() {
        UnpaidCommand.UnpaidPersonDescriptor unpaidPersonDescriptor = new UnpaidCommand.UnpaidPersonDescriptor();
        unpaidPersonDescriptor.setHasNotPaid();

        assertParseSuccess(parser, "1", new UnpaidCommand(INDEX_FIRST_PERSON, unpaidPersonDescriptor));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnpaidCommand.MESSAGE_USAGE));
    }
}

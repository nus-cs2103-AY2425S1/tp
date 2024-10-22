package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PaidCommand;
import seedu.address.model.person.Frequency;


public class PaidCommandParserTest {
    private PaidCommandParser parser = new PaidCommandParser();

    @Test
    public void parse_validArgs_returnsPaidCommand() {
        PaidCommand.PaidPersonDescriptor paidPersonDescriptor = new PaidCommand.PaidPersonDescriptor();
        paidPersonDescriptor.setHasPaid();
        paidPersonDescriptor.setFrequency(new Frequency("3"));

        assertParseSuccess(parser, "1 f/ 3", new PaidCommand(INDEX_FIRST_PERSON, paidPersonDescriptor));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, PaidCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingFrequency_throwsParseException() {
        assertParseFailure(parser, "1", PaidCommand.MESSAGE_NO_FREQUENCY);
    }
}

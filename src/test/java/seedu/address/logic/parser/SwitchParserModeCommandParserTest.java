package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SwitchParserModeCommand;

public class SwitchParserModeCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SwitchParserModeCommand.MESSAGE_USAGE);
    private static final String BUYER_MODE = "b";
    private static final String MEETUP_MODE = "m";
    private static final String PROPERTY_MODE = "p";
    private static final String FRONT_TRAILING_BUYER_MODE = "  b";
    private static final String BACK_TRAILING_BUYER_MODE = "b   ";
    private static final String BOTH_TRAILING_BUYER_MODE = "    b  ";

    private SwitchParserModeCommandParser parser = new SwitchParserModeCommandParser();

    @Test
    public void parse_invalidArguments_failure() {
        // correct first letter, but wrong string
        assertParseFailure(parser, "BUYER", MESSAGE_INVALID_FORMAT);

        // non-existent mode
        assertParseFailure(parser, "i", MESSAGE_INVALID_FORMAT);

        // digits
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgumentsNoTrailingWhitespace_success() {

        SwitchParserModeCommand expectedBuyerCommand = new SwitchParserModeCommand(ParserMode.BUYER);
        SwitchParserModeCommand expectedMeetUpCommand = new SwitchParserModeCommand(ParserMode.MEETUP);
        SwitchParserModeCommand expectedPropertyCommand = new SwitchParserModeCommand(ParserMode.PROPERTY);

        assertParseSuccess(parser, BUYER_MODE, expectedBuyerCommand);
        assertParseSuccess(parser, MEETUP_MODE, expectedMeetUpCommand);
        assertParseSuccess(parser, PROPERTY_MODE, expectedPropertyCommand);
    }

    @Test
    public void parse_validArgumentsTrailingWhitespace_success() {

        SwitchParserModeCommand expectedBuyerCommand = new SwitchParserModeCommand(ParserMode.BUYER);

        assertParseSuccess(parser, FRONT_TRAILING_BUYER_MODE, expectedBuyerCommand);
        assertParseSuccess(parser, BACK_TRAILING_BUYER_MODE, expectedBuyerCommand);
        assertParseSuccess(parser, BOTH_TRAILING_BUYER_MODE, expectedBuyerCommand);
    }
}

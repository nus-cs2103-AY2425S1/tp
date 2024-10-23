package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BUDGET_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BUDGET_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BUDGET_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUDGET_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BUYER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BUYER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_BUYER;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.SwitchParserModeCommand;
import seedu.address.logic.commands.SwitchParserModeCommandTest;
import seedu.address.logic.commands.buyer.EditCommand;
import seedu.address.logic.commands.buyer.EditCommand.EditBuyerDescriptor;
import seedu.address.model.buyer.Budget;
import seedu.address.model.buyer.Email;
import seedu.address.model.buyer.Name;
import seedu.address.model.buyer.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.buyer.EditBuyerDescriptorBuilder;

public class SwitchParserModeCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SwitchParserModeCommand.MESSAGE_USAGE);

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

        String BUYER_MODE = "b";
        String MEETUP_MODE = "m";
        String PROPERTY_MODE = "p";

        SwitchParserModeCommand expectedBuyerCommand = new SwitchParserModeCommand(ParserMode.BUYER);
        SwitchParserModeCommand expectedMeetUpCommand = new SwitchParserModeCommand(ParserMode.MEETUP);
        SwitchParserModeCommand expectedPropertyCommand = new SwitchParserModeCommand(ParserMode.PROPERTY);

        assertParseSuccess(parser, BUYER_MODE, expectedBuyerCommand);
        assertParseSuccess(parser, MEETUP_MODE, expectedMeetUpCommand);
        assertParseSuccess(parser, PROPERTY_MODE, expectedPropertyCommand);
    }

    @Test
    public void parse_validArgumentsTrailingWhitespace_success() {

        String FRONT_TRAILING_BUYER_MODE = "  b";
        String BACK_TRAILING_BUYER_MODE = "b   ";
        String BOTH_TRAILING_BUYER_MODE = "    b  ";

        SwitchParserModeCommand expectedBuyerCommand = new SwitchParserModeCommand(ParserMode.BUYER);

        assertParseSuccess(parser, FRONT_TRAILING_BUYER_MODE, expectedBuyerCommand);
        assertParseSuccess(parser, BACK_TRAILING_BUYER_MODE, expectedBuyerCommand);
        assertParseSuccess(parser, BOTH_TRAILING_BUYER_MODE, expectedBuyerCommand);
    }
}

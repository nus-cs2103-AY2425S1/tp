package seedu.sellsavvy.logic.parser;

import static seedu.sellsavvy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sellsavvy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.sellsavvy.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.logic.commands.ordercommands.FilterOrderCommand;
import seedu.sellsavvy.model.order.Status;
import seedu.sellsavvy.model.order.StatusEqualsKeywordPredicate;

public class FilterOrderCommandParserTest {

    private FilterOrderCommandParser parser = new FilterOrderCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterOrderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Not a status
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterOrderCommand.MESSAGE_USAGE));

        // Invalid status
        assertParseFailure(parser, "invalid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterOrderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterOrderCommand() {
        // valid completed order status
        FilterOrderCommand expectedFilterOrderCommand =
                new FilterOrderCommand(new StatusEqualsKeywordPredicate(Status.COMPLETED));
        // no leading and trailing whitespaces, all lower case
        assertParseSuccess(parser, "completed", expectedFilterOrderCommand);
        // multiple whitespaces, status in Start case
        assertParseSuccess(parser, " \n Completed \t", expectedFilterOrderCommand);

        // valid pending order status
        expectedFilterOrderCommand =
                new FilterOrderCommand(new StatusEqualsKeywordPredicate(Status.PENDING));
        assertParseSuccess(parser, "PeNdInG", expectedFilterOrderCommand);
        assertParseSuccess(parser, "Pending", expectedFilterOrderCommand);
    }

}

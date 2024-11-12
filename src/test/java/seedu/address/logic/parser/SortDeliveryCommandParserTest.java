package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortDeliveryCommand;
import seedu.address.model.delivery.DeliverySortBy;
import seedu.address.model.delivery.DeliverySortCostComparator;
import seedu.address.model.delivery.DeliverySortDateTimeComparator;
import seedu.address.model.delivery.DeliverySortStatusComparator;

public class SortDeliveryCommandParserTest {

    private SortDeliveryCommandParser parser = new SortDeliveryCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortDeliveryCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validAscendingCost_returnsSortDeliveryCommand() {
        SortDeliveryCommand expectedSortCommand =
                new SortDeliveryCommand(new DeliverySortCostComparator(new SortOrder("a")));
        assertParseSuccess(parser,
                PREFIX_DELIVERY + " " + PREFIX_SORT_ORDER + "a " + PREFIX_SORT_BY + "c", expectedSortCommand);
    }

    @Test
    public void parse_validDescendingDateTime_returnsSortDeliveryCommand() {
        SortDeliveryCommand expectedSortCommand =
                new SortDeliveryCommand(new DeliverySortDateTimeComparator(new SortOrder("d")));
        assertParseSuccess(parser,
                PREFIX_DELIVERY + " " + PREFIX_SORT_ORDER + "d " + PREFIX_SORT_BY + "d", expectedSortCommand);
    }

    @Test
    public void parse_validAscendingStatus_returnsSortDeliveryCommand() {
        SortDeliveryCommand expectedSortCommand =
                new SortDeliveryCommand(new DeliverySortStatusComparator(new SortOrder("a")));
        assertParseSuccess(parser,
                PREFIX_DELIVERY + " " + PREFIX_SORT_ORDER + "a " + PREFIX_SORT_BY + "s", expectedSortCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test missing sort order
        assertParseFailure(parser, PREFIX_DELIVERY + " " + PREFIX_SORT_BY + "c",
                 String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortDeliveryCommand.MESSAGE_USAGE));

        // Test invalid sort order
        assertParseFailure(parser, PREFIX_DELIVERY + " " + PREFIX_SORT_ORDER + "x " + PREFIX_SORT_BY + "c",
                SortOrder.MESSAGE_CONSTRAINTS);

        // Test missing sort by
        assertParseFailure(parser, PREFIX_DELIVERY + " " + PREFIX_SORT_ORDER + "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortDeliveryCommand.MESSAGE_USAGE));

        // Test invalid sort by
        assertParseFailure(parser, PREFIX_DELIVERY + " " + PREFIX_SORT_ORDER + "d " + PREFIX_SORT_BY + "x",
                DeliverySortBy.MESSAGE_CONSTRAINTS);

        // Test with empty input
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortDeliveryCommand.MESSAGE_USAGE));

        // Test with only spaces
        assertParseFailure(parser, "      ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortDeliveryCommand.MESSAGE_USAGE));
    }
}

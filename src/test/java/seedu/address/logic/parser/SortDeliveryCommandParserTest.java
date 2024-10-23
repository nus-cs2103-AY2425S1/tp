package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortDeliveryCommand;
import seedu.address.model.delivery.DeliverySortCostComparator;

public class SortDeliveryCommandParserTest {

    private SortDeliveryCommandParser parser = new SortDeliveryCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortDeliveryCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        SortDeliveryCommand expectedSortCommand =
                new SortDeliveryCommand(new DeliverySortCostComparator(new SortOrder("a")));
        assertParseSuccess(parser,
                PREFIX_DELIVERY + " " + PREFIX_SORT_ORDER + "a " + PREFIX_SORT_BY + "c", expectedSortCommand);
    }

}

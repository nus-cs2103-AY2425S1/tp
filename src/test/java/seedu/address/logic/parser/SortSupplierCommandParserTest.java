package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUPPLIER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortSupplierCommand;
import seedu.address.model.person.SupplierSortNameComparator;


public class SortSupplierCommandParserTest {
    private SortSupplierCommandParser parser = new SortSupplierCommandParser();
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortSupplierCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        SortSupplierCommand expectedSortCommand =
                new SortSupplierCommand(new SupplierSortNameComparator(new SortOrder("a")));
        assertParseSuccess(parser,
                PREFIX_SUPPLIER + " " + PREFIX_SORT_ORDER + "a " + PREFIX_SORT_BY + "n", expectedSortCommand);
    }
}

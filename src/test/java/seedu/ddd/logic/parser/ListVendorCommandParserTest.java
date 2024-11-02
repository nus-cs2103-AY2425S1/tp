package seedu.ddd.logic.parser;

import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.commands.list.ListContactCommand;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.logic.parser.list.ListVendorCommandParser;

public class ListVendorCommandParserTest {
    private ListVendorCommandParser parser = new ListVendorCommandParser();

    @Test
    public void parse_validArgs_returnsListContactCommand() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_NAME, "Alice Bob");

        // no leading and trailing whitespaces
        ListContactCommand expectedListCommand = parser.parse("Alice Bob");
        assertParseSuccess(parser, "Alice Bob", expectedListCommand);
        // multiple whitespaces between keywords
        expectedListCommand = parser.parse(" \n Alice \n \t Bob  \t");
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedListCommand);
    }
}

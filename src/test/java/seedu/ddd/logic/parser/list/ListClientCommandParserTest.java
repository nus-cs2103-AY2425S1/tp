package seedu.ddd.logic.parser.list;

import static seedu.ddd.logic.parser.CliFlags.FLAG_CLIENT;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_EVENT_DATE_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_EVENT_DESC_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_VENDOR_SERVICE_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ddd.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.commands.list.ListContactCommand;
import seedu.ddd.logic.commands.list.ListEventCommand;
import seedu.ddd.logic.parser.ArgumentMultimap;
import seedu.ddd.logic.parser.exceptions.ParseException;

public class ListClientCommandParserTest {
    private ListClientCommandParser parser = new ListClientCommandParser();
    private final String templateInput = ListEventCommand.COMMAND_WORD + " " + FLAG_CLIENT + " ";

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
    @Test
    public void parse_invalidArgs_throwsParseException() throws ParseException {

        // Prefix service with empty input
        assertThrows(ParseException.class, () -> parser.parse(templateInput + PREFIX_SERVICE));

        // Prefix service with an input
        assertThrows(ParseException.class, () ->
                parser.parse(templateInput + PREFIX_SERVICE + VALID_VENDOR_SERVICE_ARGUMENT));

        // Prefix description with empty input
        assertThrows(ParseException.class, () -> parser.parse(templateInput + PREFIX_DESC));

        // Prefix description with an input
        assertThrows(ParseException.class, () ->
                parser.parse(templateInput + PREFIX_DESC + VALID_EVENT_DESC_ARGUMENT));

        //Prefix date with empty input
        assertThrows(ParseException.class, () -> parser.parse(templateInput + PREFIX_DATE));

        // Prefix date with an input
        assertThrows(ParseException.class, () ->
                        parser.parse(templateInput + PREFIX_DATE + VALID_EVENT_DATE_ARGUMENT));
    }
}

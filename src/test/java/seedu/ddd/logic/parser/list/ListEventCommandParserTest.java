package seedu.ddd.logic.parser.list;

import static seedu.ddd.logic.parser.CliFlags.FLAG_EVENT;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_TAG_ARGUMENT_1;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_VENDOR_ADDRESS_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_VENDOR_EMAIL_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_VENDOR_PHONE_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_VENDOR_SERVICE_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ddd.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.commands.list.ListEventCommand;
import seedu.ddd.logic.parser.ArgumentMultimap;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.event.common.predicate.EventPredicateBuilder;


public class ListEventCommandParserTest {
    private ListEventCommandParser parser = new ListEventCommandParser();
    private final String templateInput = ListEventCommand.COMMAND_WORD + " " + FLAG_EVENT + " ";
    @Test
    public void parse_validArgs_returnsListEventCommand() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(FLAG_EVENT, "");
        argMultimap.put(PREFIX_DESC, "Alice Bob");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);
        // no leading and trailing whitespaces
        ListEventCommand expectedListEventCommand = parser.parse("Alice Bob");
        assertParseSuccess(parser, "Alice Bob", expectedListEventCommand);
        // multiple whitespaces between keywords
        expectedListEventCommand = parser.parse(" \n Alice \n \t Bob  \t");
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedListEventCommand);
    }
    @Test
    public void parse_invalidArgs_throwsParseException() throws ParseException {

        // Prefix service with empty input
        assertThrows(ParseException.class, () -> parser.parse(templateInput + PREFIX_SERVICE));

        // Prefix service with an input
        assertThrows(ParseException.class, () ->
                parser.parse(templateInput + PREFIX_SERVICE + VALID_VENDOR_SERVICE_ARGUMENT));

        // Prefix address with empty input
        assertThrows(ParseException.class, () -> parser.parse(templateInput + PREFIX_ADDRESS));

        // Prefix address with an input
        assertThrows(ParseException.class, () ->
                parser.parse(templateInput + PREFIX_ADDRESS + VALID_VENDOR_ADDRESS_ARGUMENT));

        //Prefix phone with empty input
        assertThrows(ParseException.class, () -> parser.parse(templateInput + PREFIX_PHONE));

        // Prefix phone with an input
        assertThrows(ParseException.class, () ->
                parser.parse(templateInput + PREFIX_PHONE + VALID_VENDOR_PHONE_ARGUMENT));

        //Prefix email with empty input
        assertThrows(ParseException.class, () -> parser.parse(templateInput + PREFIX_EMAIL));

        // Prefix email with an input
        assertThrows(ParseException.class, () ->
                        parser.parse(templateInput + PREFIX_EMAIL + VALID_VENDOR_EMAIL_ARGUMENT));

        //Prefix email with empty input
        assertThrows(ParseException.class, () -> parser.parse(templateInput + PREFIX_TAG));

        // Prefix email with an input
        assertThrows(ParseException.class, () ->
                        parser.parse(templateInput + PREFIX_TAG + VALID_TAG_ARGUMENT_1));
    }
}

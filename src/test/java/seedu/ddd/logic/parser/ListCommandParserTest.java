/*package seedu.ddd.logic.parser;

import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_EVENT;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ddd.logic.parser.CommandParserTestUtil.assertParseSuccess;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.commands.ListCommand;
import seedu.ddd.logic.commands.ListEventCommand;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.contact.common.predicate.ContactPredicateBuilder;
import seedu.ddd.model.event.common.predicate.EventPredicateBuilder;


public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_NAME, "Alice Bob");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);
        // no leading and trailing whitespaces
        ListCommand expectedListCommand =
                new ListCommand(predicateBuilder.build());
        assertParseSuccess(parser, "Alice Bob", expectedListCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedListCommand);
    }

    @Test
    public void parse_validArgs_returnsListEventCommand() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(FLAG_EVENT, "");
        argMultimap.put(PREFIX_DESC, "Alice Bob");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);
        // no leading and trailing whitespaces
        ListEventCommand expectedListEventCommand =
                new ListEventCommand(predicateBuilder.build());
        assertParseSuccess(parser, "Alice Bob", expectedListEventCommand);
        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedListEventCommand);
    }
}
*/




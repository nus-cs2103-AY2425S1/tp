package seedu.ddd.logic.parser;

import static seedu.ddd.logic.parser.CliFlags.FLAG_EVENT;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.commands.ListEventCommand;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.event.common.predicate.EventPredicateBuilder;


public class ListEventCommandParserTest {
    private ListEventCommandParser parser = new ListEventCommandParser();
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
}

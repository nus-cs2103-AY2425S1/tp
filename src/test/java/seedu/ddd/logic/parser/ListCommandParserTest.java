/* package seedu.ddd.logic.parser;

import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ddd.logic.parser.CommandParserTestUtil.assertParseSuccess;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import seedu.ddd.logic.commands.ListCommand;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.model.contact.common.Predicate.ContactPredicateBuilder;
import seedu.ddd.model.contact.common.Predicate.NameContainsKeywordsPredicate;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();
    /*
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
    */


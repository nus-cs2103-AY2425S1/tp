package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIELD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.model.person.AddressContainsKeywordsPredicate;

public class SearchCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsForAddressField_returnsSearchCommand() {
        SearchCommand expectedSearchCommand =
                new SearchCommand(new AddressContainsKeywordsPredicate(Arrays.asList("Avenue", "Street")));
        assertParseSuccess(parser, "search by/Address Avenue Street", expectedSearchCommand);
    }

    @Test
    public void parse_invalidArgsForAddressField_returnsSearchCommand() {
        SearchCommand expectedSearchCommand =
                new SearchCommand(new AddressContainsKeywordsPredicate(Arrays.asList("Avenue", "Street")));
        assertParseFailure(parser, "search Address Avenue Street",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateArgsForAddressField_returnsSearchCommand() {
        SearchCommand expectedSearchCommand =
                new SearchCommand(new AddressContainsKeywordsPredicate(Arrays.asList("Avenue", "Street")));
        assertParseFailure(parser, "search by/Address by/Address",
                MESSAGE_DUPLICATE_FIELDS + PREFIX_FIELD);
    }
}

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
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

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

    @Test
    public void parse_validArgsForEmailField_returnsSearchCommand() {
        SearchCommand expectedSearchCommand =
                new SearchCommand(new EmailContainsKeywordsPredicate(Arrays.asList("example", "test.com")));
        assertParseSuccess(parser, "search by/Email example test.com", expectedSearchCommand);
    }

    @Test
    public void parse_invalidArgsForEmailField_returnsParseException() {
        assertParseFailure(parser, "search Email example test.com",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateArgsForEmailField_throwsParseException() {
        assertParseFailure(parser, "search by/Email by/Email",
                MESSAGE_DUPLICATE_FIELDS + PREFIX_FIELD);
    }

    @Test
    public void parse_validArgsForNameField_returnsSearchCommand() {
        SearchCommand expectedSearchCommand =
                new SearchCommand(new NameContainsKeywordsPredicate(Arrays.asList("John", "Doe")));
        assertParseSuccess(parser, "search by/Name John Doe", expectedSearchCommand);
    }

    @Test
    public void parse_invalidArgsForNameField_returnsParseException() {
        assertParseFailure(parser, "search Name John Doe",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateArgsForNameField_throwsParseException() {
        assertParseFailure(parser, "search by/Name by/Name",
                MESSAGE_DUPLICATE_FIELDS + PREFIX_FIELD);
    }

    @Test
    public void parse_validArgsForPhoneField_returnsSearchCommand() {
        SearchCommand expectedSearchCommand =
                new SearchCommand(new PhoneContainsKeywordsPredicate(Arrays.asList("12345", "67890")));
        assertParseSuccess(parser, "search by/Phone 12345 67890", expectedSearchCommand);
    }

    @Test
    public void parse_invalidArgsForPhoneField_returnsParseException() {
        assertParseFailure(parser, "search Phone 12345 67890",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateArgsForPhoneField_throwsParseException() {
        assertParseFailure(parser, "search by/Phone by/Phone",
                MESSAGE_DUPLICATE_FIELDS + PREFIX_FIELD);
    }

    @Test
    public void parse_validArgsForTagField_returnsSearchCommand() {
        SearchCommand expectedSearchCommand =
                new SearchCommand(new TagContainsKeywordsPredicate(Arrays.asList("VIP", "Friend")));
        assertParseSuccess(parser, "search by/Tags VIP Friend", expectedSearchCommand);
    }

    @Test
    public void parse_invalidArgsForTagField_returnsParseException() {
        assertParseFailure(parser, "search Tags VIP Friend",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateArgsForTagField_throwsParseException() {
        assertParseFailure(parser, "search by/Tags by/Tags",
                MESSAGE_DUPLICATE_FIELDS + PREFIX_FIELD);
    }
}

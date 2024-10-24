package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindAddressCommand;
import seedu.address.logic.commands.FindClientTypeCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindNameCommand;
import seedu.address.logic.commands.FindPhoneCommand;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.ClientTypeContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.PhoneBeginsWithKeywordPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsName_returnsFindNameCommand() {
        // no leading and trailing whitespaces
        FindNameCommand expectedFindNameCommand =
                new FindNameCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "n/Alice Bob", expectedFindNameCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "n/ Alice   Bob  ", expectedFindNameCommand);
    }

    @Test
    public void parse_invalidNameArgs_throwsParseException() {
        assertParseFailure(parser, "n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNameCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyNameKeyword_throwsParseException() {
        assertParseFailure(parser, "n/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNameCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleSpacesAfterPrefix_throwsParseException() {
        assertParseFailure(parser, "n/     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindNameCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsPhone_returnsFindPhoneCommand() {
        // valid phone number
        FindPhoneCommand expectedFindPhoneCommand =
                new FindPhoneCommand(new PhoneBeginsWithKeywordPredicate("98765432"));
        assertParseSuccess(parser, "p/98765432", expectedFindPhoneCommand);
    }

    @Test
    public void parse_invalidPhoneArgs_throwsParseException() {
        // missing phone number
        assertParseFailure(parser, "p/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPhoneCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsAddress_returnsFindAddressCommand() {
        // valid address
        FindAddressCommand expectedFindAddressCommand =
                new FindAddressCommand(new AddressContainsKeywordsPredicate("Serangoon Road"));
        assertParseSuccess(parser, "a/Serangoon Road", expectedFindAddressCommand);

        // address with multiple words
        FindAddressCommand expectedMultiWordAddressCommand =
                new FindAddressCommand(new AddressContainsKeywordsPredicate("123 Main Street"));
        assertParseSuccess(parser, "a/123 Main Street", expectedMultiWordAddressCommand);
    }

    @Test
    public void parse_invalidAddressArgs_throwsParseException() {
        // missing address
        assertParseFailure(parser, "a/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindAddressCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsClientType_returnsFindClientTypeCommand() {
        // valid client type
        FindClientTypeCommand expectedFindClientTypeCommand =
                new FindClientTypeCommand(new ClientTypeContainsKeywordsPredicate(List.of("VIP")));
        assertParseSuccess(parser, "c/VIP", expectedFindClientTypeCommand);
    }

    @Test
    public void parse_invalidClientTypeArgs_throwsParseException() {
        // missing client type
        assertParseFailure(parser, "c/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindClientTypeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        // invalid prefix
        assertParseFailure(parser, "e/email@example.com", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }
}

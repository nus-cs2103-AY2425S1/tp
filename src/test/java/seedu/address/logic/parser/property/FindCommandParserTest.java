package seedu.address.logic.parser.property;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_FIND_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FIND_PROPERTY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LANDLORD_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LANDLORD_FIND_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.property.FindCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.property.FindCommand;
import seedu.address.model.property.Address;
import seedu.address.model.property.AddressContainsKeywordsPredicate;
import seedu.address.model.property.LandlordName;
import seedu.address.model.property.LandlordNameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_validAddress_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList("Jurong", "Clementi")));

        assertParseSuccess(parser, ADDRESS_FIND_DESC, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ADDRESS_FIND_DESC, expectedFindCommand);
    }

    @Test
    public void parse_emptyAddressFormat_failParse() {
        assertParseFailure(parser, INVALID_ADDRESS_DESC,
                String.format(Address.MESSAGE_CONSTRAINTS, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validLandlord_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new LandlordNameContainsKeywordsPredicate(Arrays.asList("Jerald", "James")));

        assertParseSuccess(parser, LANDLORD_FIND_DESC, expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + LANDLORD_FIND_DESC, expectedFindCommand);
    }

    @Test
    public void parse_invalidNameFormat_failParse() {
        assertParseFailure(parser, INVALID_LANDLORD_NAME_DESC,
                String.format(LandlordName.MESSAGE_CONSTRAINTS, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleTags_failParse() {
        assertParseFailure(parser, INVALID_FIND_PROPERTY_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalVendors.AMY;
import static seedu.address.testutil.TypicalVendors.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CreateVendorCommand;
import seedu.address.model.name.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.vendor.Description;
import seedu.address.model.vendor.Phone;
import seedu.address.model.vendor.Vendor;
import seedu.address.testutil.VendorBuilder;

public class CreateVendorCommandParserTest {
    private CreateVendorCommandParser parser = new CreateVendorCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Vendor expectedVendor = new VendorBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + " " + PREFIX_VENDOR + NAME_DESC_BOB + PHONE_DESC_BOB
                        + DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND,
                new CreateVendorCommand(expectedVendor));

        // multiple tags - all accepted
        Vendor expectedVendorMultipleTags = new VendorBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                " " + PREFIX_VENDOR + NAME_DESC_BOB + PHONE_DESC_BOB + DESCRIPTION_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new CreateVendorCommand(expectedVendorMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedVendorString = " " + PREFIX_VENDOR
                + NAME_DESC_BOB + PHONE_DESC_BOB + DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple descriptions
        assertParseFailure(parser, DESCRIPTION_DESC_AMY + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedVendorString + PHONE_DESC_AMY + NAME_DESC_AMY + DESCRIPTION_DESC_AMY
                        + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid description
        assertParseFailure(parser, INVALID_DESCRIPTION_DESC + validExpectedVendorString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedVendorString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid phone
        assertParseFailure(parser, validExpectedVendorString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid description
        assertParseFailure(parser, validExpectedVendorString + INVALID_DESCRIPTION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESCRIPTION));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Vendor expectedVendor = new VendorBuilder(AMY).withTags().build();
        assertParseSuccess(parser, " " + PREFIX_VENDOR + NAME_DESC_AMY + PHONE_DESC_AMY + DESCRIPTION_DESC_AMY,
                new CreateVendorCommand(expectedVendor));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateVendorCommand.MESSAGE_USAGE);

        // missing vendor prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + DESCRIPTION_DESC_BOB, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, " " + PREFIX_VENDOR
                + VALID_NAME_BOB + PHONE_DESC_BOB + DESCRIPTION_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, " " + PREFIX_VENDOR
                + NAME_DESC_BOB + VALID_PHONE_BOB + DESCRIPTION_DESC_BOB, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, " " + PREFIX_VENDOR
                + NAME_DESC_BOB + PHONE_DESC_BOB + VALID_DESCRIPTION_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, " " + PREFIX_VENDOR
                + VALID_NAME_BOB + VALID_PHONE_BOB + VALID_DESCRIPTION_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser,
                " " + PREFIX_VENDOR + INVALID_NAME_DESC + PHONE_DESC_BOB + DESCRIPTION_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser,
                " " + PREFIX_VENDOR + NAME_DESC_BOB + INVALID_PHONE_DESC + DESCRIPTION_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser,
                " " + PREFIX_VENDOR + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_DESCRIPTION_DESC
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Description.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser,
                " " + PREFIX_VENDOR + NAME_DESC_BOB + PHONE_DESC_BOB + DESCRIPTION_DESC_BOB + INVALID_TAG_DESC
                        + VALID_TAG_FRIEND,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, " " + PREFIX_VENDOR + INVALID_NAME_DESC + PHONE_DESC_BOB
                        + INVALID_DESCRIPTION_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + " " + PREFIX_VENDOR + NAME_DESC_BOB + PHONE_DESC_BOB
                        + DESCRIPTION_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateVendorCommand.MESSAGE_USAGE));
    }
}

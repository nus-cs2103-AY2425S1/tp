package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddBuyerProfile;
import seedu.address.logic.commands.AddSellerProfile;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Seller;
import seedu.address.testutil.PersonBuilder;

public class AddClientProfileParserTest {

    @Test
    public void parse_addBuyer_success() {
        AddClientParser parser = new AddClientParser("buyer");
        Buyer expectedBuyer = new PersonBuilder()
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .buildBuyer();

        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                new AddBuyerProfile(expectedBuyer));
    }

    @Test
    public void parse_addSeller_success() {
        AddClientParser parser = new AddClientParser("seller");
        Seller expectedSeller = new PersonBuilder()
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .buildSeller();

        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                new AddSellerProfile(expectedSeller));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        AddClientParser buyerParser = new AddClientParser("buyer");
        AddClientParser sellerParser = new AddClientParser("seller");

        // Buyer case
        Buyer expectedBuyer = new PersonBuilder()
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_FRIEND)
                .buildBuyer();

        assertParseSuccess(buyerParser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_FRIEND,
                new AddBuyerProfile(expectedBuyer));

        // Seller case
        Seller expectedSeller = new PersonBuilder()
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_FRIEND)
                .buildSeller();

        assertParseSuccess(sellerParser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_FRIEND,
                new AddSellerProfile(expectedSeller));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        AddClientParser buyerParser = new AddClientParser("buyer");
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB;

        // multiple names
        assertParseFailure(buyerParser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(buyerParser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(buyerParser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple fields repeated
        assertParseFailure(buyerParser,
                validExpectedPersonString + PHONE_DESC_AMY + NAME_DESC_AMY + EMAIL_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        AddClientParser buyerParser = new AddClientParser("buyer");
        AddClientParser sellerParser = new AddClientParser("seller");

        // Buyer case
        Buyer expectedBuyer = new PersonBuilder(AMY).withTags().buildBuyer();
        assertParseSuccess(buyerParser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY,
                new AddBuyerProfile(expectedBuyer));

        // Seller case
        Seller expectedSeller = new PersonBuilder(AMY).withTags().buildSeller();
        assertParseSuccess(sellerParser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY,
                new AddSellerProfile(expectedSeller));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        AddClientParser buyerParser = new AddClientParser("buyer");
        AddClientParser sellerParser = new AddClientParser("seller");

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBuyerProfile.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(buyerParser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(buyerParser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(buyerParser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(buyerParser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        AddClientParser buyerParser = new AddClientParser("buyer");

        // invalid name
        assertParseFailure(buyerParser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(buyerParser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(buyerParser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(buyerParser, INVALID_NAME_DESC + INVALID_PHONE_DESC + EMAIL_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);
    }
}

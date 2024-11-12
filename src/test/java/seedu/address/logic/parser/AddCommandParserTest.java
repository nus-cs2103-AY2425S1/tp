package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDAY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INTERESTS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MAJOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNIVERSITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WORKEXP_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MAJOR_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.UNIVERSITY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTEREST_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.WORKEXP_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WORKEXP_DESC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WORKEXP;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.University;
import seedu.address.model.person.WorkExp;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB)
                .withTags(VALID_TAG_FRIEND)
                .withInterests(VALID_INTEREST_BOB)
                .build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + WORKEXP_DESC_BOB + TAG_DESC_FRIEND + UNIVERSITY_DESC_BOB + MAJOR_DESC_BOB
                        + INTERESTS_DESC_BOB + BIRTHDAY_DESC_BOB,
                new AddCommand(expectedPerson));


        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + WORKEXP_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND + UNIVERSITY_DESC_BOB + MAJOR_DESC_BOB + INTERESTS_DESC_BOB
                        + BIRTHDAY_DESC_BOB,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + WORKEXP_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple workExp
        assertParseFailure(parser, WORKEXP_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WORKEXP));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + ADDRESS_DESC_AMY + WORKEXP_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL,
                        PREFIX_PHONE, PREFIX_WORKEXP));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid address
        assertParseFailure(parser, INVALID_WORKEXP_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WORKEXP));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        assertParseFailure(parser, validExpectedPersonString + INVALID_WORKEXP_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WORKEXP));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }
    @Test
    public void parse_invalidInterestWithComma_failure() {
        // Interest with comma should be invalid
        String invalidInterestWithComma = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + WORKEXP_DESC_BOB + UNIVERSITY_DESC_BOB + MAJOR_DESC_BOB + " i/swimming,diving"
                + TAG_DESC_FRIEND + BIRTHDAY_DESC_BOB;

        assertParseFailure(parser, invalidInterestWithComma,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validInterestWithoutComma_success() {
        // Valid interest with no commas
        Person expectedPerson = new PersonBuilder(BOB)
                .withInterests("rock climbing") // Modify as needed for valid interest
                .build();

        String validInterestWithoutComma = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + WORKEXP_DESC_BOB + UNIVERSITY_DESC_BOB + MAJOR_DESC_BOB + " i/rock climbing"
                + TAG_DESC_FRIEND + BIRTHDAY_DESC_BOB;

        assertParseSuccess(parser, validInterestWithoutComma, new AddCommand(expectedPerson));
    }


    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + WORKEXP_DESC_BOB + UNIVERSITY_DESC_BOB + MAJOR_DESC_BOB + TAG_DESC_FRIEND
                + BIRTHDAY_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + WORKEXP_DESC_BOB + UNIVERSITY_DESC_BOB + MAJOR_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                        + BIRTHDAY_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                        + WORKEXP_DESC_BOB + UNIVERSITY_DESC_BOB + MAJOR_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                        + BIRTHDAY_DESC_BOB,
                Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                        + WORKEXP_DESC_BOB + UNIVERSITY_DESC_BOB + MAJOR_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                        + BIRTHDAY_DESC_BOB,
                Address.MESSAGE_CONSTRAINTS);

        // invalid workExp
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + INVALID_WORKEXP_DESC + UNIVERSITY_DESC_BOB + MAJOR_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                        + BIRTHDAY_DESC_BOB,
                WorkExp.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + WORKEXP_DESC_BOB + UNIVERSITY_DESC_BOB + MAJOR_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND
                        + BIRTHDAY_DESC_BOB,
                Tag.MESSAGE_CONSTRAINTS);

        // invalid university
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + WORKEXP_DESC_BOB + INVALID_UNIVERSITY_DESC + MAJOR_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND + BIRTHDAY_DESC_BOB, University.MESSAGE_CONSTRAINTS);

        // invalid major
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + WORKEXP_DESC_BOB + UNIVERSITY_DESC_BOB + INVALID_MAJOR_DESC + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND + BIRTHDAY_DESC_BOB, Major.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                        + WORKEXP_DESC_BOB + UNIVERSITY_DESC_BOB + MAJOR_DESC_BOB + BIRTHDAY_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);
    }

}

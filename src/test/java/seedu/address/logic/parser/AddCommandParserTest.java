package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INCOME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INCOME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INCOME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_JOB_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.JOB_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.JOB_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NEW_REMARK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TIER_DESC_GOLD;
import static seedu.address.logic.commands.CommandTestUtil.TIER_DESC_REJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INCOME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_NONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIER_GOLD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Income;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.status.Status;
import seedu.address.model.tier.Tier;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTier(VALID_TIER_GOLD).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + JOB_DESC_BOB + INCOME_DESC_BOB + TIER_DESC_GOLD + NEW_REMARK_DESC_BOB,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_repeatedNonTierValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + JOB_DESC_BOB + INCOME_DESC_BOB + TIER_DESC_GOLD
                + NEW_REMARK_DESC_BOB + STATUS_DESC_BOB;

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

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple jobs
        assertParseFailure(parser, JOB_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_JOB));

        // multiple incomes
        assertParseFailure(parser, INCOME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INCOME));

        // multiple tiers
        assertParseFailure(parser, TIER_DESC_REJECT + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TIER));

        // multiple remarks
        assertParseFailure(parser, NEW_REMARK_DESC_BOB + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NEW_REMARK));

        // multiple status
        assertParseFailure(parser, STATUS_DESC_BOB + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STATUS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + validExpectedPersonString + VALID_STATUS_NONE,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_JOB, PREFIX_INCOME, PREFIX_TIER, PREFIX_NEW_REMARK, PREFIX_STATUS));

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
    }
    @Test
    public void parse_optionalFieldsMissing_success() {
        // no tier
        Person expectedPerson = new PersonBuilder(AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + JOB_DESC_AMY + INCOME_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + JOB_DESC_BOB + INCOME_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + JOB_DESC_BOB + INCOME_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                        + JOB_DESC_BOB + INCOME_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                        + JOB_DESC_BOB + INCOME_DESC_BOB,
                expectedMessage);

        // missing job prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + VALID_JOB_BOB + INCOME_DESC_BOB,
                expectedMessage);

        // missing income prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + JOB_DESC_BOB + VALID_INCOME_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                + VALID_JOB_BOB + VALID_INCOME_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INCOME_DESC_BOB + JOB_DESC_BOB + TIER_DESC_GOLD, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INCOME_DESC_BOB + JOB_DESC_BOB + TIER_DESC_GOLD, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + INCOME_DESC_BOB + JOB_DESC_BOB + TIER_DESC_GOLD, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + INCOME_DESC_BOB + JOB_DESC_BOB + TIER_DESC_GOLD, Address.MESSAGE_CONSTRAINTS);

        // invalid income
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_INCOME_DESC + JOB_DESC_BOB + TIER_DESC_GOLD, Income.MESSAGE_CONSTRAINTS);

        // invalid job
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INCOME_DESC_BOB + INVALID_JOB_DESC + TIER_DESC_GOLD, Job.MESSAGE_CONSTRAINTS);

        // invalid tier
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INCOME_DESC_BOB + JOB_DESC_BOB + INVALID_TIER_DESC + VALID_TIER_GOLD, Tier.MESSAGE_CONSTRAINTS);

        // invalid remark
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INCOME_DESC_BOB + JOB_DESC_BOB + TIER_DESC_GOLD + INVALID_REMARK_DESC, Tier.MESSAGE_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INCOME_DESC_BOB + JOB_DESC_BOB + TIER_DESC_GOLD + INVALID_STATUS_DESC, Status.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + INCOME_DESC_BOB + JOB_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + INCOME_DESC_BOB + JOB_DESC_BOB + TIER_DESC_REJECT + TIER_DESC_GOLD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

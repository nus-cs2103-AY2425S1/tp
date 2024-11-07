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
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TIER_DESC_GOLD;
import static seedu.address.logic.commands.CommandTestUtil.TIER_DESC_REJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INCOME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_NA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIER_GOLD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INCOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalClients.AMY;
import static seedu.address.testutil.TypicalClients.BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.client.Address;
import seedu.address.model.client.Client;
import seedu.address.model.client.Email;
import seedu.address.model.client.Income;
import seedu.address.model.client.Job;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Remark;
import seedu.address.model.status.Status;
import seedu.address.model.tier.Tier;
import seedu.address.testutil.ClientBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Client expectedClient = new ClientBuilder(BOB).withTier(VALID_TIER_GOLD).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + JOB_DESC_BOB + INCOME_DESC_BOB + TIER_DESC_GOLD + REMARK_DESC_BOB
                        + STATUS_DESC_BOB, new AddCommand(expectedClient));
    }

    @Test
    public void parse_repeatedNonTierValue_failure() {
        String validExpectedClientString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + JOB_DESC_BOB + INCOME_DESC_BOB + TIER_DESC_GOLD
                + REMARK_DESC_BOB + STATUS_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedClientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedClientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedClientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedClientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedClientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple jobs
        assertParseFailure(parser, JOB_DESC_AMY + validExpectedClientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_JOB));

        // multiple incomes
        assertParseFailure(parser, INCOME_DESC_AMY + validExpectedClientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INCOME));

        // multiple tiers
        assertParseFailure(parser, TIER_DESC_REJECT + validExpectedClientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TIER));

        // multiple remarks
        assertParseFailure(parser, REMARK_DESC_BOB + validExpectedClientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK));

        // multiple status
        assertParseFailure(parser, STATUS_DESC_BOB + validExpectedClientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STATUS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedClientString + validExpectedClientString + VALID_STATUS_NA,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_JOB, PREFIX_INCOME, PREFIX_TIER, PREFIX_REMARK, PREFIX_STATUS));

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedClientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedClientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedClientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedClientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedClientString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedClientString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedClientString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedClientString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }
    @Test
    public void parse_optionalFieldsMissing_success() {
        // no tier
        Client expectedClient = new ClientBuilder(AMY).build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + JOB_DESC_AMY + INCOME_DESC_AMY,
                new AddCommand(expectedClient));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + JOB_DESC_BOB + INCOME_DESC_BOB,
                AddCommand.MISSING_PREFIX_MESSAGE_START + "[" + PREFIX_NAME + "]\n" + expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + JOB_DESC_BOB + INCOME_DESC_BOB,
                AddCommand.MISSING_PREFIX_MESSAGE_START + "[" + PREFIX_PHONE + "]\n" + expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                        + JOB_DESC_BOB + INCOME_DESC_BOB,
                AddCommand.MISSING_PREFIX_MESSAGE_START + "[" + PREFIX_EMAIL + "]\n" + expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB
                        + JOB_DESC_BOB + INCOME_DESC_BOB,
                AddCommand.MISSING_PREFIX_MESSAGE_START + "[" + PREFIX_ADDRESS + "]\n" + expectedMessage);

        // missing job prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + VALID_JOB_BOB + INCOME_DESC_BOB,
                AddCommand.MISSING_PREFIX_MESSAGE_START + "[" + PREFIX_JOB + "]\n" + expectedMessage);

        // missing income prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + JOB_DESC_BOB + VALID_INCOME_BOB,
                AddCommand.MISSING_PREFIX_MESSAGE_START + "[" + PREFIX_INCOME + "]\n" + expectedMessage);

        // all prefixes missing
        List<Prefix> missingPrefixList = new ArrayList<>(Arrays.asList(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_JOB, PREFIX_INCOME));
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                + VALID_JOB_BOB + VALID_INCOME_BOB,
                AddCommand.MISSING_PREFIX_MESSAGE_START + missingPrefixList + "\n" + expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INCOME_DESC_BOB + JOB_DESC_BOB + TIER_DESC_GOLD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Name.MESSAGE_CONSTRAINTS + "\n"
                        + AddCommand.MESSAGE_USAGE));

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INCOME_DESC_BOB + JOB_DESC_BOB + TIER_DESC_GOLD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Phone.MESSAGE_CONSTRAINTS + "\n"
                        + AddCommand.MESSAGE_USAGE));

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + INCOME_DESC_BOB + JOB_DESC_BOB + TIER_DESC_GOLD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Email.MESSAGE_CONSTRAINTS + "\n"
                        + AddCommand.MESSAGE_USAGE));

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + INCOME_DESC_BOB + JOB_DESC_BOB + TIER_DESC_GOLD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Address.MESSAGE_CONSTRAINTS + "\n"
                        + AddCommand.MESSAGE_USAGE));

        // invalid income
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_INCOME_DESC + JOB_DESC_BOB + TIER_DESC_GOLD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Income.MESSAGE_CONSTRAINTS + "\n"
                        + AddCommand.MESSAGE_USAGE));

        // invalid job
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INCOME_DESC_BOB + INVALID_JOB_DESC + TIER_DESC_GOLD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Job.MESSAGE_CONSTRAINTS + "\n"
                        + AddCommand.MESSAGE_USAGE));

        // invalid tier
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INCOME_DESC_BOB + JOB_DESC_BOB + INVALID_TIER_DESC + VALID_TIER_GOLD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Tier.MESSAGE_CONSTRAINTS + "\n"
                        + AddCommand.MESSAGE_USAGE));

        // invalid remark
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INCOME_DESC_BOB + JOB_DESC_BOB + TIER_DESC_GOLD + INVALID_REMARK_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Remark.MESSAGE_CONSTRAINTS + "\n"
                        + AddCommand.MESSAGE_USAGE));

        // invalid status
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INCOME_DESC_BOB + JOB_DESC_BOB + TIER_DESC_GOLD + INVALID_STATUS_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Status.MESSAGE_CONSTRAINTS + "\n"
                        + AddCommand.MESSAGE_USAGE));

        // two invalid values, both are shown
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + INCOME_DESC_BOB + JOB_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        Name.MESSAGE_CONSTRAINTS + "\n" + Address.MESSAGE_CONSTRAINTS + "\n"
                        + AddCommand.MESSAGE_USAGE));

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + INCOME_DESC_BOB + JOB_DESC_BOB + TIER_DESC_REJECT + TIER_DESC_GOLD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.APPEND_REMARK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INCOME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.INCOME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPEND_REMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_INCOME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NEW_REMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NEW_REMARK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TIER_DESC_GOLD;
import static seedu.address.logic.commands.CommandTestUtil.TIER_DESC_REJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INCOME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INCOME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_NON_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIER_GOLD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIER_REJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CLIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CLIENT;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditClientDescriptor;
import seedu.address.model.client.Address;
import seedu.address.model.client.Email;
import seedu.address.model.client.Income;
import seedu.address.model.client.Name;
import seedu.address.model.client.Phone;
import seedu.address.model.client.Remark;
import seedu.address.model.status.Status;
import seedu.address.model.tier.Tier;
import seedu.address.testutil.EditClientDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TIER_EMPTY = " " + PREFIX_TIER;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED + "\n"
                + EditCommand.MESSAGE_USAGE_OPTIONAL_PARAMETERS);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 z/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, Name.MESSAGE_CONSTRAINTS)); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, Phone.MESSAGE_CONSTRAINTS)); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, Email.MESSAGE_CONSTRAINTS)); // invalid email
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, Address.MESSAGE_CONSTRAINTS)); // invalid address
        assertParseFailure(parser, "1" + INVALID_INCOME_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, Income.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, "1" + INVALID_TIER_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, Tier.MESSAGE_CONSTRAINTS));
        // invalid new remark which is empty
        assertParseFailure(parser, "1" + INVALID_NEW_REMARK_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, Remark.MESSAGE_CONSTRAINTS));
        // invalid appended remark which is empty
        assertParseFailure(parser, "1" + INVALID_APPEND_REMARK_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, Remark.MESSAGE_CONSTRAINTS));
        assertParseFailure(parser, "1" + INVALID_STATUS_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, Status.MESSAGE_CONSTRAINTS));

        // multiple invalid values, all are expected
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY
                + VALID_PHONE_AMY, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                Name.MESSAGE_CONSTRAINTS) + "\n" + Email.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "1" + INVALID_TIER_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, Tier.MESSAGE_CONSTRAINTS));

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, Phone.MESSAGE_CONSTRAINTS));

        // multiple invalid values, all are captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC
                + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, Name.MESSAGE_CONSTRAINTS)
                        + "\n" + Email.MESSAGE_CONSTRAINTS);

        // multiple invalid values but with purely optional fields, the error messages are captured in order of the
        // attributes' parsing
        assertParseFailure(parser, "1" + INVALID_STATUS_DESC + INVALID_TIER_DESC
                + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, Tier.MESSAGE_CONSTRAINTS)
                + "\n" + Status.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_CLIENT;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + TIER_DESC_REJECT
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY;

        EditClientDescriptor descriptor = new EditClientDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTier(VALID_TIER_REJECT).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_CLIENT;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditClientDescriptor descriptor = new EditClientDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_CLIENT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditClientDescriptor descriptor = new EditClientDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditClientDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditClientDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
        descriptor = new EditClientDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // income
        userInput = targetIndex.getOneBased() + INCOME_DESC_AMY;
        descriptor = new EditClientDescriptorBuilder().withIncome(new BigInteger(VALID_INCOME_AMY)).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // income but 0 dollars
        userInput = targetIndex.getOneBased() + INCOME_DESC_BOB;
        descriptor = new EditClientDescriptorBuilder().withIncome(new BigInteger(VALID_INCOME_BOB)).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tier
        userInput = targetIndex.getOneBased() + TIER_DESC_GOLD;
        descriptor = new EditClientDescriptorBuilder().withTier(VALID_TIER_GOLD).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // new remark
        userInput = targetIndex.getOneBased() + NEW_REMARK_DESC_BOB;
        descriptor = new EditClientDescriptorBuilder().withNewRemark(VALID_REMARK_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remark append
        userInput = targetIndex.getOneBased() + APPEND_REMARK_DESC_BOB;
        descriptor = new EditClientDescriptorBuilder().withAppendedRemark(VALID_REMARK_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // status
        userInput = targetIndex.getOneBased() + STATUS_DESC_BOB;
        descriptor = new EditClientDescriptorBuilder().withStatus(VALID_STATUS_NON_URGENT).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTierValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_CLIENT;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + TIER_DESC_GOLD + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TIER_DESC_GOLD
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TIER_DESC_REJECT;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TIER));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));
    }

    @Test
    public void parse_remarkNewRemarkAppend_failure() {
        Index targetIndex = INDEX_FIRST_CLIENT;
        String userInput = targetIndex.getOneBased() + APPEND_REMARK_DESC_BOB + NEW_REMARK_DESC_BOB;
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        Messages.MESSAGE_CONCURRENT_RN_RA_FIELDS + EditCommand.MESSAGE_USAGE));

    }

}

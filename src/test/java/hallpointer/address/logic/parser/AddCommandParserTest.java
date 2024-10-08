package hallpointer.address.logic.parser;

import static hallpointer.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static hallpointer.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static hallpointer.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static hallpointer.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static hallpointer.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static hallpointer.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static hallpointer.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static hallpointer.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static hallpointer.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static hallpointer.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static hallpointer.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static hallpointer.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static hallpointer.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static hallpointer.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_NAME;
import static hallpointer.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static hallpointer.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static hallpointer.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static hallpointer.address.testutil.TypicalMembers.AMY;
import static hallpointer.address.testutil.TypicalMembers.BOB;

import org.junit.jupiter.api.Test;

import hallpointer.address.logic.Messages;
import hallpointer.address.logic.commands.AddCommand;
import hallpointer.address.model.member.Email;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.Name;
import hallpointer.address.model.member.Phone;
import hallpointer.address.model.member.Room;
import hallpointer.address.model.tag.Tag;
import hallpointer.address.testutil.MemberBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Member expectedMember = new MemberBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedMember));


        // multiple tags - all accepted
        Member expectedMemberMultipleTags = new MemberBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedMemberMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedMemberString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedMemberString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedMemberString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedMemberString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple rooms
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedMemberString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedMemberString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + validExpectedMemberString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedMemberString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedMemberString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedMemberString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid room
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedMemberString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedMemberString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedMemberString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedMemberString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid room
        assertParseFailure(parser, validExpectedMemberString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Member expectedMember = new MemberBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCommand(expectedMember));
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

        // missing room prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid room
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Room.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

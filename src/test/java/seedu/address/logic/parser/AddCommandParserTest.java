package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LAST_SEEN_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORGANISATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LAST_SEEN_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LAST_SEEN_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ORGANISATION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ORGANISATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_LOW;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_HANDSOME;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_PRETTY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LOW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAST_SEEN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.LastSeen;
import seedu.address.model.person.Name;
import seedu.address.model.person.Organisation;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Priority;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND)
                                    .withPriority(VALID_PRIORITY_LOW).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ORGANISATION_DESC_BOB + LAST_SEEN_DESC_BOB + TAG_DESC_FRIEND
                + PRIORITY_DESC_LOW + REMARK_DESC_HANDSOME, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                                            .withPriority(VALID_PRIORITY_LOW).build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                           + ORGANISATION_DESC_BOB + LAST_SEEN_DESC_BOB + TAG_DESC_HUSBAND
                        + TAG_DESC_FRIEND + PRIORITY_DESC_LOW + REMARK_DESC_HANDSOME,
                           new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ORGANISATION_DESC_BOB + LAST_SEEN_DESC_BOB
                + TAG_DESC_FRIEND + PRIORITY_DESC_LOW + REMARK_DESC_HANDSOME;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple organisations
        assertParseFailure(parser, ORGANISATION_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ORGANISATION));

        // multiple last seen
        assertParseFailure(parser, LAST_SEEN_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LAST_SEEN));

        // multiple remarks
        assertParseFailure(parser, REMARK_DESC_PRETTY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + ORGANISATION_DESC_AMY + LAST_SEEN_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_EMAIL, PREFIX_ORGANISATION,
                        PREFIX_LAST_SEEN, PREFIX_PHONE, PREFIX_PRIORITY, PREFIX_REMARK));

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

        // invalid organisation
        assertParseFailure(parser, INVALID_ORGANISATION_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ORGANISATION));

        // invalid remark
        assertParseFailure(parser, INVALID_REMARK_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK));

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

        // invalid organisation
        assertParseFailure(parser, validExpectedPersonString + INVALID_ORGANISATION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ORGANISATION));

        // invalid remark
        assertParseFailure(parser, validExpectedPersonString + INVALID_REMARK_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                           + ORGANISATION_DESC_AMY + LAST_SEEN_DESC_AMY + PRIORITY_DESC_LOW + REMARK_DESC_PRETTY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB ,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ORGANISATION_DESC_BOB + LAST_SEEN_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                + PRIORITY_DESC_LOW + REMARK_DESC_HANDSOME, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ORGANISATION_DESC_BOB + LAST_SEEN_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                + PRIORITY_DESC_LOW + REMARK_DESC_HANDSOME, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + ORGANISATION_DESC_BOB + LAST_SEEN_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                + PRIORITY_DESC_LOW + REMARK_DESC_HANDSOME, Email.MESSAGE_CONSTRAINTS);

        // invalid organisation
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ORGANISATION_DESC + LAST_SEEN_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                + INVALID_PRIORITY_DESC + REMARK_DESC_HANDSOME, Organisation.MESSAGE_CONSTRAINTS);

        // invalid lastSeen
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ORGANISATION_DESC_BOB + INVALID_LAST_SEEN_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                + PRIORITY_DESC_LOW + REMARK_DESC_HANDSOME, LastSeen.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ORGANISATION_DESC_BOB + LAST_SEEN_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND
                + PRIORITY_DESC_LOW + REMARK_DESC_HANDSOME, Tag.MESSAGE_CONSTRAINTS);

        //invalid priority
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ORGANISATION_DESC_BOB + LAST_SEEN_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                + INVALID_PRIORITY_DESC + REMARK_DESC_HANDSOME, Priority.MESSAGE_CONSTRAINTS);

        //invalid remark
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ORGANISATION_DESC_BOB + LAST_SEEN_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                + PRIORITY_DESC_LOW + INVALID_REMARK_DESC, Remark.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + ORGANISATION_DESC_BOB + LAST_SEEN_DESC_BOB + TAG_DESC_HUSBAND
                + PRIORITY_DESC_LOW + REMARK_DESC_HANDSOME, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ORGANISATION_DESC_BOB + LAST_SEEN_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND + PRIORITY_DESC_LOW + REMARK_DESC_HANDSOME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

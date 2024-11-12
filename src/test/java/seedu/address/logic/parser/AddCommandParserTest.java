package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CONTACTTYPE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CONTACTTYPE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTACTTYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MODNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TELEHANDLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MODNAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MODNAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TELEHANDLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TELEHANDLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTACTTYPE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEHANDLE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACTTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEHANDLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.ContactType;
import seedu.address.model.person.Email;
import seedu.address.model.person.ModuleName;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.TelegramHandle;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + CONTACTTYPE_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + TELEHANDLE_DESC_BOB + MODNAME_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));


        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                CONTACTTYPE_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + TELEHANDLE_DESC_BOB
                        + MODNAME_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = CONTACTTYPE_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TELEHANDLE_DESC_BOB + MODNAME_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple telegram handles
        assertParseFailure(parser, TELEHANDLE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEHANDLE));

        //multiple contact types
        assertParseFailure(parser, CONTACTTYPE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CONTACTTYPE));

        // multiple module names
        assertParseFailure(parser, MODNAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MOD));

        // multiple remarks
        assertParseFailure(parser, REMARK_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK));

        // multiple fields repeated
        assertParseFailure(parser, PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_EMAIL, PREFIX_PHONE));

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

        // invalid telegram handle
        assertParseFailure(parser, INVALID_TELEHANDLE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEHANDLE));

        // invalid contact type
        assertParseFailure(parser, INVALID_CONTACTTYPE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CONTACTTYPE));

        // invalid module name
        assertParseFailure(parser, INVALID_MODNAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MOD));

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

        // invalid telegram handle
        assertParseFailure(parser, validExpectedPersonString + INVALID_TELEHANDLE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEHANDLE));

        // invalid contact type
        assertParseFailure(parser, validExpectedPersonString + INVALID_CONTACTTYPE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CONTACTTYPE));

        // invalid module name
        assertParseFailure(parser, validExpectedPersonString + INVALID_MODNAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MOD));

        // invalid remark
        assertParseFailure(parser, validExpectedPersonString + INVALID_REMARK_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags, no phone number, email, module name or remark
        Person expectedPersonWithTelegramHandle = new PersonBuilder(AMY).withPhone(null).withEmail(null)
                .withModuleName(null).withRemark(null).withTags().build();
        assertParseSuccess(parser, CONTACTTYPE_DESC_AMY + NAME_DESC_AMY + TELEHANDLE_DESC_AMY,
                new AddCommand(expectedPersonWithTelegramHandle));

        // zero tags, no telegram handle, email, module name or remark
        Person expectedPersonWithPhone = new PersonBuilder(AMY).withTelegramHandle(null).withEmail(null)
                .withModuleName(null).withRemark(null).withTags().build();
        assertParseSuccess(parser, CONTACTTYPE_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY,
                new AddCommand(expectedPersonWithPhone));

        // zero tags, no telegram handle, phone number, module name or remark
        Person expectedPersonWithEmail = new PersonBuilder(AMY).withTelegramHandle(null).withPhone(null)
                .withModuleName(null).withRemark(null).withTags().build();
        assertParseSuccess(parser, CONTACTTYPE_DESC_AMY + NAME_DESC_AMY + EMAIL_DESC_AMY,
                new AddCommand(expectedPersonWithEmail));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, CONTACTTYPE_DESC_BOB + VALID_NAME_BOB + TELEHANDLE_DESC_BOB,
                expectedMessage);

        // missing all 3 of telegram handle, phone and email prefixes
        assertParseFailure(parser, CONTACTTYPE_DESC_BOB + NAME_DESC_BOB + VALID_TELEHANDLE_BOB,
                expectedMessage);

        // missing contact type prefix
        assertParseFailure(parser, VALID_CONTACTTYPE_BOB + NAME_DESC_BOB + TELEHANDLE_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_CONTACTTYPE_BOB + VALID_NAME_BOB + VALID_TELEHANDLE_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, CONTACTTYPE_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                 + TELEHANDLE_DESC_BOB + MODNAME_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, CONTACTTYPE_DESC_BOB + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + TELEHANDLE_DESC_BOB + MODNAME_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, CONTACTTYPE_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + TELEHANDLE_DESC_BOB + MODNAME_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Email.MESSAGE_CONSTRAINTS);

        // invalid telegram handle
        assertParseFailure(parser, CONTACTTYPE_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_TELEHANDLE_DESC + MODNAME_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                TelegramHandle.MESSAGE_CONSTRAINTS);

        // invalid contact type
        assertParseFailure(parser, INVALID_CONTACTTYPE_DESC + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TELEHANDLE_DESC_BOB + MODNAME_DESC_BOB + REMARK_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                ContactType.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, CONTACTTYPE_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TELEHANDLE_DESC_BOB + MODNAME_DESC_BOB + REMARK_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_CONSTRAINTS);

        // invalid module name
        assertParseFailure(parser, CONTACTTYPE_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + TELEHANDLE_DESC_BOB + INVALID_MODNAME_DESC + REMARK_DESC_BOB + TAG_DESC_HUSBAND
                        + VALID_TAG_FRIEND, ModuleName.MESSAGE_CONSTRAINTS);

        // invalid remark
        assertParseFailure(parser, CONTACTTYPE_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TELEHANDLE_DESC_BOB + MODNAME_DESC_BOB + INVALID_REMARK_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Remark.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, CONTACTTYPE_DESC_BOB + INVALID_NAME_DESC + INVALID_PHONE_DESC
                + EMAIL_DESC_BOB + TELEHANDLE_DESC_BOB + MODNAME_DESC_BOB + REMARK_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + CONTACTTYPE_DESC_BOB + NAME_DESC_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + TELEHANDLE_DESC_BOB + MODNAME_DESC_BOB + REMARK_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

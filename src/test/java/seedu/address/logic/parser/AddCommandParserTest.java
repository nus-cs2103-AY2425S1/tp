package seedu.address.logic.parser;

import static seedu.address.logic.commands.AddCommand.MESSAGE_MISSING_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TELEGRAM_HANDLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_ADMIN;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_PRESIDENT;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_STATUS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_STATUS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TELEGRAM_HANDLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TELEGRAM_HANDLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_ADMIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_PRESIDENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_HANDLE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM_HANDLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalContacts.AMY;
import static seedu.address.testutil.TypicalContacts.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Name;
import seedu.address.testutil.ContactBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Contact expectedContact = new ContactBuilder(BOB).withRoles(VALID_ROLE_ADMIN).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB
                + TELEGRAM_HANDLE_DESC_BOB + EMAIL_DESC_BOB
                + STUDENT_STATUS_DESC_BOB + ROLE_DESC_ADMIN, new AddCommand(expectedContact));


        // multiple roles - all accepted
        Contact expectedContactMultipleRoles = new ContactBuilder(BOB).withRoles(VALID_ROLE_ADMIN, VALID_ROLE_PRESIDENT)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + TELEGRAM_HANDLE_DESC_BOB + EMAIL_DESC_BOB
                        + STUDENT_STATUS_DESC_BOB + ROLE_DESC_PRESIDENT
                        + ROLE_DESC_ADMIN,
                new AddCommand(expectedContactMultipleRoles));
    }

    @Test
    public void parse_repeatedNonRoleValue_failure() {
        String validExpectedContactString = NAME_DESC_BOB + TELEGRAM_HANDLE_DESC_BOB + EMAIL_DESC_BOB
                + STUDENT_STATUS_DESC_BOB + ROLE_DESC_ADMIN;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple telegramHandles
        assertParseFailure(parser, TELEGRAM_HANDLE_DESC_AMY + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEGRAM_HANDLE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple student statuses
        assertParseFailure(parser, STUDENT_STATUS_DESC_AMY + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_STATUS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedContactString + TELEGRAM_HANDLE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + STUDENT_STATUS_DESC_AMY + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_TELEGRAM_HANDLE, PREFIX_EMAIL,
                        PREFIX_STUDENT_STATUS));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid telegramHandle
        assertParseFailure(parser, INVALID_TELEGRAM_HANDLE_DESC + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEGRAM_HANDLE));

        // invalid student status
        assertParseFailure(parser, INVALID_STUDENT_STATUS_DESC + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_STATUS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedContactString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedContactString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid telegramHandle
        assertParseFailure(parser, validExpectedContactString + INVALID_TELEGRAM_HANDLE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEGRAM_HANDLE));

        // invalid student status
        assertParseFailure(parser, validExpectedContactString + INVALID_STUDENT_STATUS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_STATUS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero roles
        Contact expectedContact = new ContactBuilder(AMY).withRoles(VALID_ROLE_ADMIN).build();
        assertParseSuccess(parser, NAME_DESC_AMY + TELEGRAM_HANDLE_DESC_AMY + EMAIL_DESC_AMY
                        + STUDENT_STATUS_DESC_AMY + ROLE_DESC_ADMIN,
                new AddCommand(expectedContact));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // everything is missing
        assertParseFailure(parser, "",
                MESSAGE_MISSING_DESCRIPTION);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + TELEGRAM_HANDLE_DESC_BOB + EMAIL_DESC_BOB
                        + STUDENT_STATUS_DESC_BOB + ROLE_DESC_ADMIN,
                String.format(AddCommand.MESSAGE_MISSING_PREFIXES, PREFIX_NAME));

        // missing telegramHandle prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_TELEGRAM_HANDLE_BOB + EMAIL_DESC_BOB
                        + STUDENT_STATUS_DESC_BOB + ROLE_DESC_ADMIN,
                String.format(AddCommand.MESSAGE_MISSING_PREFIXES, PREFIX_TELEGRAM_HANDLE));

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_HANDLE_DESC_BOB + VALID_EMAIL_BOB
                        + STUDENT_STATUS_DESC_BOB + ROLE_DESC_ADMIN,
                String.format(AddCommand.MESSAGE_MISSING_PREFIXES, PREFIX_EMAIL));

        // missing address prefix // the following does not look like missing address prefix
        /*
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_HANDLE_DESC_BOB + EMAIL_DESC_BOB
                        + VALID_STUDENT_STATUS_BOB + ROLE_DESC_ADMIN,
                String.format(AddCommand.MESSAGE_MISSING_PREFIXES, PREFIX_NAME));*/

        // missing role prefix
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_HANDLE_DESC_BOB
                        + EMAIL_DESC_BOB + STUDENT_STATUS_DESC_BOB
                        + VALID_ROLE_PRESIDENT,
                String.format(AddCommand.MESSAGE_MISSING_PREFIXES, PREFIX_ROLE));

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_TELEGRAM_HANDLE_BOB + VALID_EMAIL_BOB
                        + VALID_STUDENT_STATUS_BOB + VALID_ROLE_PRESIDENT,
                String.format(AddCommand.MESSAGE_MISSING_PREFIXES, "n/  ss/  th/  e/  r/"));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + TELEGRAM_HANDLE_DESC_BOB
                + EMAIL_DESC_BOB + STUDENT_STATUS_DESC_BOB
                + ROLE_DESC_PRESIDENT + ROLE_DESC_ADMIN, "Invalid Name!\n" + Name.MESSAGE_CONSTRAINTS);
        // not so good code but it reflects actual code for now

        // invalid telegramHandle
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_TELEGRAM_HANDLE_DESC
                + EMAIL_DESC_BOB + STUDENT_STATUS_DESC_BOB
                + ROLE_DESC_PRESIDENT + ROLE_DESC_ADMIN, ParserUtil.MESSAGE_INVALID_TELEGRAM_HANDLE_FIELD);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_HANDLE_DESC_BOB
                + INVALID_EMAIL_DESC + STUDENT_STATUS_DESC_BOB
                + ROLE_DESC_PRESIDENT + ROLE_DESC_ADMIN, ParserUtil.MESSAGE_INVALID_EMAIL_FIELD);

        // invalid student status
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_HANDLE_DESC_BOB
                + EMAIL_DESC_BOB + INVALID_STUDENT_STATUS_DESC
                + ROLE_DESC_PRESIDENT + ROLE_DESC_ADMIN, ParserUtil.MESSAGE_STUDENT_STATUS_FIELD_CANNOT_BLANK);

        // invalid role
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_HANDLE_DESC_BOB
                + EMAIL_DESC_BOB + STUDENT_STATUS_DESC_BOB
                + INVALID_ROLE_DESC + VALID_ROLE_ADMIN, ParserUtil.MESSAGE_INVALID_ROLE_FIELD);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + TELEGRAM_HANDLE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_STUDENT_STATUS_DESC + ROLE_DESC_PRESIDENT,
                ParserUtil.MESSAGE_INVALID_NAME_FIELD);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + TELEGRAM_HANDLE_DESC_BOB + EMAIL_DESC_BOB
                + STUDENT_STATUS_DESC_BOB + ROLE_DESC_PRESIDENT + ROLE_DESC_ADMIN,
                AddCommand.MESSAGE_NOTHING_AFTER_COMMAND_AND_BEFORE_PREFIX);
    }
}

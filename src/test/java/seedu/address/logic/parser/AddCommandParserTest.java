package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TELEGRAM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_ADMIN;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_PRESIDENT;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_STATUS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_STATUS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TELEGRAM_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TELEGRAM_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_ADMIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_PRESIDENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentStatus;
import seedu.address.model.person.Telegram;
import seedu.address.model.tag.Role;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withRoles(VALID_ROLE_ADMIN).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB
                + STUDENT_STATUS_DESC_BOB + ROLE_DESC_ADMIN, new AddCommand(expectedPerson));


        // multiple roles - all accepted
        Person expectedPersonMultipleRoles = new PersonBuilder(BOB).withRoles(VALID_ROLE_ADMIN, VALID_ROLE_PRESIDENT)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB + STUDENT_STATUS_DESC_BOB + ROLE_DESC_PRESIDENT
                        + ROLE_DESC_ADMIN,
                new AddCommand(expectedPersonMultipleRoles));
    }

    @Test
    public void parse_repeatedNonRoleValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB
                + STUDENT_STATUS_DESC_BOB + ROLE_DESC_ADMIN;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple telegrams
        assertParseFailure(parser, TELEGRAM_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEGRAM));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple student statuses
        assertParseFailure(parser, STUDENT_STATUS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_STATUS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + TELEGRAM_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + STUDENT_STATUS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_TELEGRAM, PREFIX_EMAIL,
                        PREFIX_STUDENT_STATUS));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid telegram
        assertParseFailure(parser, INVALID_TELEGRAM_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEGRAM));

        // invalid student status
        assertParseFailure(parser, INVALID_STUDENT_STATUS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_STATUS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid telegram
        assertParseFailure(parser, validExpectedPersonString + INVALID_TELEGRAM_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEGRAM));

        // invalid student status
        assertParseFailure(parser, validExpectedPersonString + INVALID_STUDENT_STATUS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENT_STATUS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero roles
        Person expectedPerson = new PersonBuilder(AMY).withRoles(VALID_ROLE_ADMIN).build();
        assertParseSuccess(parser, NAME_DESC_AMY + TELEGRAM_DESC_AMY + EMAIL_DESC_AMY
                        + STUDENT_STATUS_DESC_AMY + ROLE_DESC_ADMIN,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB
                        + STUDENT_STATUS_DESC_BOB + ROLE_DESC_ADMIN,
                expectedMessage);

        // missing telegram prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_TELEGRAM_BOB + EMAIL_DESC_BOB
                        + STUDENT_STATUS_DESC_BOB + ROLE_DESC_ADMIN,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_DESC_BOB + VALID_EMAIL_BOB
                        + STUDENT_STATUS_DESC_BOB + ROLE_DESC_ADMIN,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB
                        + VALID_STUDENT_STATUS_BOB + ROLE_DESC_ADMIN,
                expectedMessage);

        // missing role prefix
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB + STUDENT_STATUS_DESC_BOB
                        + VALID_ROLE_PRESIDENT,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_TELEGRAM_BOB + VALID_EMAIL_BOB
                        + VALID_STUDENT_STATUS_BOB + VALID_ROLE_PRESIDENT,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB + STUDENT_STATUS_DESC_BOB
                + ROLE_DESC_PRESIDENT + ROLE_DESC_ADMIN, Name.MESSAGE_CONSTRAINTS);

        // invalid telegram
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_TELEGRAM_DESC + EMAIL_DESC_BOB + STUDENT_STATUS_DESC_BOB
                + ROLE_DESC_PRESIDENT + ROLE_DESC_ADMIN, Telegram.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_DESC_BOB + INVALID_EMAIL_DESC + STUDENT_STATUS_DESC_BOB
                + ROLE_DESC_PRESIDENT + ROLE_DESC_ADMIN, Email.MESSAGE_CONSTRAINTS);

        // invalid student status
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB + INVALID_STUDENT_STATUS_DESC
                + ROLE_DESC_PRESIDENT + ROLE_DESC_ADMIN, StudentStatus.MESSAGE_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, NAME_DESC_BOB + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB + STUDENT_STATUS_DESC_BOB
                + INVALID_ROLE_DESC + VALID_ROLE_ADMIN, Role.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_STUDENT_STATUS_DESC + ROLE_DESC_PRESIDENT,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + TELEGRAM_DESC_BOB + EMAIL_DESC_BOB
                + STUDENT_STATUS_DESC_BOB + ROLE_DESC_PRESIDENT + ROLE_DESC_ADMIN,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

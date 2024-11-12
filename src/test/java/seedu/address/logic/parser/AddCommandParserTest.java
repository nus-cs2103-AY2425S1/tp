package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MAJOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENTID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MAJOR_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MAJOR_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.STUDENTID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENTID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NETID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.group.Group;
import seedu.address.model.person.Email;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withGroups(VALID_GROUP_TWO).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + STUDENTID_DESC_BOB + EMAIL_DESC_BOB
                + MAJOR_DESC_BOB + YEAR_DESC_BOB + GROUP_DESC_TWO, new AddCommand(expectedPerson));


        // multiple groups - all accepted
        Person expectedPersonMultipleGroups = new PersonBuilder(BOB).withGroups(VALID_GROUP_TWO, VALID_GROUP_ONE)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + STUDENTID_DESC_BOB + EMAIL_DESC_BOB + MAJOR_DESC_BOB + YEAR_DESC_BOB
                        + GROUP_DESC_ONE,
                new AddCommand(expectedPersonMultipleGroups));
    }

    @Test
    public void parse_repeatedNonGroupValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + STUDENTID_DESC_BOB + EMAIL_DESC_BOB
                + MAJOR_DESC_BOB + GROUP_DESC_TWO;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, STUDENTID_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENTID));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NETID));

        // multiple addresses
        assertParseFailure(parser, MAJOR_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MAJOR));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + STUDENTID_DESC_AMY + EMAIL_DESC_AMY
                        + NAME_DESC_AMY + MAJOR_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_STUDENTID,
                        PREFIX_NETID, PREFIX_MAJOR));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NETID));

        // invalid phone
        assertParseFailure(parser, INVALID_STUDENTID_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENTID));

        // invalid address
        assertParseFailure(parser, INVALID_MAJOR_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MAJOR));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NETID));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_STUDENTID_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENTID));

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_MAJOR_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MAJOR));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero groups
        Person expectedPerson = new PersonBuilder(AMY).withGroups().build();
        assertParseSuccess(parser, NAME_DESC_AMY + STUDENTID_DESC_AMY + EMAIL_DESC_AMY + MAJOR_DESC_AMY
                        + YEAR_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + STUDENTID_DESC_BOB + EMAIL_DESC_BOB + MAJOR_DESC_BOB,
                expectedMessage);

        // missing studentId prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_STUDENTID_BOB + EMAIL_DESC_BOB + MAJOR_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_STUDENTID_BOB + VALID_EMAIL_BOB + VALID_MAJOR_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + STUDENTID_DESC_BOB + EMAIL_DESC_BOB + MAJOR_DESC_BOB
                + GROUP_DESC_ONE, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_STUDENTID_DESC + EMAIL_DESC_BOB + MAJOR_DESC_BOB
                + GROUP_DESC_ONE, StudentId.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + STUDENTID_DESC_BOB + INVALID_EMAIL_DESC + MAJOR_DESC_BOB
                + GROUP_DESC_ONE, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + STUDENTID_DESC_BOB + EMAIL_DESC_BOB + INVALID_MAJOR_DESC
                + GROUP_DESC_ONE, Major.MESSAGE_CONSTRAINTS);

        // invalid group
        assertParseFailure(parser, NAME_DESC_BOB + STUDENTID_DESC_BOB + EMAIL_DESC_BOB + MAJOR_DESC_BOB
                + INVALID_GROUP_DESC + VALID_GROUP_TWO, Group.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + STUDENTID_DESC_BOB + EMAIL_DESC_BOB + INVALID_MAJOR_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + STUDENTID_DESC_BOB + EMAIL_DESC_BOB
                + MAJOR_DESC_BOB + GROUP_DESC_ONE + GROUP_DESC_TWO,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

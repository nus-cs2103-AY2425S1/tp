package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static keycontacts.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static keycontacts.logic.commands.CommandTestUtil.GRADE_LEVEL_DESC_AMY;
import static keycontacts.logic.commands.CommandTestUtil.GRADE_LEVEL_DESC_BOB;
import static keycontacts.logic.commands.CommandTestUtil.GROUP_DESC_AMY;
import static keycontacts.logic.commands.CommandTestUtil.GROUP_DESC_BOB;
import static keycontacts.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static keycontacts.logic.commands.CommandTestUtil.INVALID_GRADE_LEVEL_DESC;
import static keycontacts.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static keycontacts.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static keycontacts.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static keycontacts.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static keycontacts.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static keycontacts.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static keycontacts.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static keycontacts.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static keycontacts.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static keycontacts.logic.commands.CommandTestUtil.VALID_GRADE_LEVEL_BOB;
import static keycontacts.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static keycontacts.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static keycontacts.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static keycontacts.logic.parser.CliSyntax.PREFIX_GRADE_LEVEL;
import static keycontacts.logic.parser.CliSyntax.PREFIX_GROUP;
import static keycontacts.logic.parser.CliSyntax.PREFIX_NAME;
import static keycontacts.logic.parser.CliSyntax.PREFIX_PHONE;
import static keycontacts.logic.parser.CommandParserTestUtil.assertParseFailure;
import static keycontacts.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static keycontacts.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.Test;

import keycontacts.logic.Messages;
import keycontacts.logic.commands.AddCommand;
import keycontacts.model.student.Address;
import keycontacts.model.student.GradeLevel;
import keycontacts.model.student.Group;
import keycontacts.model.student.Name;
import keycontacts.model.student.Phone;
import keycontacts.model.student.Student;
import keycontacts.testutil.StudentBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Student expectedStudent = new StudentBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB
                        + ADDRESS_DESC_BOB + GRADE_LEVEL_DESC_BOB + GROUP_DESC_BOB, new AddCommand(expectedStudent));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Student expectedStudent = new StudentBuilder(BOB).withGroup(Group.NO_GROUP_STRING).build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + GRADE_LEVEL_DESC_BOB, new AddCommand(expectedStudent));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedStudentString = NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB + GRADE_LEVEL_DESC_BOB
                + GROUP_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple grade levels
        assertParseFailure(parser, GRADE_LEVEL_DESC_AMY + validExpectedStudentString,
                        Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GRADE_LEVEL));

        // multiple groups
        assertParseFailure(parser, GROUP_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GROUP));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedStudentString + GROUP_DESC_AMY + GRADE_LEVEL_DESC_AMY + PHONE_DESC_AMY
                        + NAME_DESC_AMY + ADDRESS_DESC_AMY + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE,
                        PREFIX_GRADE_LEVEL, PREFIX_GROUP));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedStudentString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedStudentString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid phone
        assertParseFailure(parser, validExpectedStudentString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedStudentString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB + GRADE_LEVEL_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + ADDRESS_DESC_BOB + GRADE_LEVEL_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_ADDRESS_BOB + GRADE_LEVEL_DESC_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_ADDRESS_BOB
                        + VALID_GRADE_LEVEL_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + ADDRESS_DESC_BOB + GRADE_LEVEL_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB + GRADE_LEVEL_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_ADDRESS_DESC + GRADE_LEVEL_DESC_BOB,
                Address.MESSAGE_CONSTRAINTS);

        // invalid grade level
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB + INVALID_GRADE_LEVEL_DESC,
                        GradeLevel.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + INVALID_ADDRESS_DESC
                        + GRADE_LEVEL_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                        + GRADE_LEVEL_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

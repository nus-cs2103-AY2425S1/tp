package careconnect.logic.parser;

import static careconnect.logic.parser.CliSyntax.PREFIX_DATE;
import static careconnect.logic.parser.CliSyntax.PREFIX_REMARK;
import static careconnect.logic.parser.CommandParserTestUtil.assertParseFailure;
import static careconnect.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static careconnect.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import careconnect.logic.Messages;
import careconnect.logic.commands.AddLogCommand;
import careconnect.logic.commands.CommandTestUtil;
import careconnect.model.log.Log;

public class AddLogCommandParserTest {
    private AddLogCommandParser parser = new AddLogCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Log log = new Log(CommandTestUtil.DATE, "Meeting 1");

        assertParseSuccess(parser, CommandTestUtil.INDEX_FIRST_PERSON
                        + CommandTestUtil.LOG_DATE
                        + CommandTestUtil.LOG_REMARK,
                new AddLogCommand(INDEX_FIRST_PERSON, log));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        assertParseSuccess(parser, CommandTestUtil.INDEX_FIRST_PERSON
                        + CommandTestUtil.LOG_REMARK,
                new AddLogCommand(INDEX_FIRST_PERSON, new Log("Meeting 1")));
    }

    @Test
    public void parse_repeated_failure() {
        String validAddLogString = CommandTestUtil.INDEX_FIRST_PERSON
                + CommandTestUtil.LOG_DATE
                + CommandTestUtil.LOG_REMARK;

        // multiple remarks
        assertParseFailure(parser, validAddLogString + CommandTestUtil.LOG_REMARK,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK));

        // multiple dates
        assertParseFailure(parser, validAddLogString + CommandTestUtil.LOG_DATE,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddLogCommand.MESSAGE_USAGE);

        // missing index
        assertParseFailure(parser, CommandTestUtil.LOG_DATE + CommandTestUtil.LOG_REMARK,
                expectedMessage);

        // missing remark
        assertParseFailure(parser, CommandTestUtil.INDEX_FIRST_PERSON + CommandTestUtil.LOG_DATE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, CommandTestUtil.INDEX_FIRST_PERSON,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser,
                CommandTestUtil.INDEX_FIRST_PERSON + CommandTestUtil.INVALID_DATE_DESC
                + CommandTestUtil.LOG_REMARK, Log.MESSAGE_CONSTRAINTS);

        // invalid remark
        assertParseFailure(parser, CommandTestUtil.INDEX_FIRST_PERSON + CommandTestUtil.LOG_DATE
                + CommandTestUtil.INVALID_REMARK_DESC, Log.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser,
                CommandTestUtil.INVALID_INDEX_DESC + CommandTestUtil.INVALID_DATE_DESC
                        + CommandTestUtil.LOG_REMARK,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                        AddLogCommand.MESSAGE_USAGE));
    }


//        // multiple emails
//        assertParseFailure(parser, CommandTestUtil.EMAIL_DESC_AMY + validExpectedPersonString,
//                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));
//
//        // multiple addresses
//        assertParseFailure(parser, CommandTestUtil.ADDRESS_DESC_AMY + validExpectedPersonString,
//                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
//
//        // multiple fields repeated
//        assertParseFailure(parser,
//                validExpectedPersonString + CommandTestUtil.PHONE_DESC_AMY + CommandTestUtil.EMAIL_DESC_AMY
//                        + CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.ADDRESS_DESC_AMY
//                        + validExpectedPersonString,
//                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));
//
//        // invalid value followed by valid value
//
//        // invalid name
//        assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC + validExpectedPersonString,
//                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
//
//        // invalid email
//        assertParseFailure(parser, CommandTestUtil.INVALID_EMAIL_DESC + validExpectedPersonString,
//                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));
//
//        // invalid phone
//        assertParseFailure(parser, CommandTestUtil.INVALID_PHONE_DESC + validExpectedPersonString,
//                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
//
//        // invalid address
//        assertParseFailure(parser, CommandTestUtil.INVALID_ADDRESS_DESC + validExpectedPersonString,
//                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
//
//        // valid value followed by invalid value
//
//        // invalid name
//        assertParseFailure(parser, validExpectedPersonString + CommandTestUtil.INVALID_NAME_DESC,
//                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
//
//        // invalid email
//        assertParseFailure(parser, validExpectedPersonString + CommandTestUtil.INVALID_EMAIL_DESC,
//                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));
//
//        // invalid phone
//        assertParseFailure(parser, validExpectedPersonString + CommandTestUtil.INVALID_PHONE_DESC,
//                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
//
//        // invalid address
//        assertParseFailure(parser, validExpectedPersonString + CommandTestUtil.INVALID_ADDRESS_DESC,
//                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
//    }
//
//    @Test
//    public void parse_optionalFieldsMissing_success() {
//        // zero tags
//        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
//        assertParseSuccess(parser, CommandTestUtil.NAME_DESC_AMY + CommandTestUtil.PHONE_DESC_AMY
//                        + CommandTestUtil.EMAIL_DESC_AMY + CommandTestUtil.ADDRESS_DESC_AMY,
//                new AddLogCommand(expectedPerson));
//    }
//
//    @Test
//    public void parse_compulsoryFieldMissing_failure() {
//        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLogCommand.MESSAGE_USAGE);
//
//        // missing name prefix
//        assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB + CommandTestUtil.PHONE_DESC_BOB
//                        + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB,
//                expectedMessage);
//
//        // missing phone prefix
//        assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.VALID_PHONE_BOB
//                        + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB,
//                expectedMessage);
//
//        // missing email prefix
//        assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB
//                        + CommandTestUtil.VALID_EMAIL_BOB + CommandTestUtil.ADDRESS_DESC_BOB,
//                expectedMessage);
//
//        // missing address prefix
//        assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB
//                        + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.VALID_ADDRESS_BOB,
//                expectedMessage);
//
//        // all prefixes missing
//        assertParseFailure(parser, CommandTestUtil.VALID_NAME_BOB + CommandTestUtil.VALID_PHONE_BOB
//                        + CommandTestUtil.VALID_EMAIL_BOB + CommandTestUtil.VALID_ADDRESS_BOB,
//                expectedMessage);
//    }
//
//    @Test
//    public void parse_invalidValue_failure() {
//        // invalid name
//        assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC + CommandTestUtil.PHONE_DESC_BOB
//                + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
//                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);
//
//        // invalid phone
//        assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.INVALID_PHONE_DESC
//                + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
//                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);
//
//        // invalid email
//        assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB
//                + CommandTestUtil.INVALID_EMAIL_DESC + CommandTestUtil.ADDRESS_DESC_BOB
//                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);
//
//        // invalid address
//        assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB
//                + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.INVALID_ADDRESS_DESC
//                + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);
//
//        // invalid tag
//        assertParseFailure(parser, CommandTestUtil.NAME_DESC_BOB + CommandTestUtil.PHONE_DESC_BOB
//                + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.ADDRESS_DESC_BOB
//                + CommandTestUtil.INVALID_TAG_DESC + CommandTestUtil.VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);
//
//        // two invalid values, only first invalid value reported
//        assertParseFailure(parser, CommandTestUtil.INVALID_NAME_DESC + CommandTestUtil.PHONE_DESC_BOB
//                        + CommandTestUtil.EMAIL_DESC_BOB + CommandTestUtil.INVALID_ADDRESS_DESC,
//                Name.MESSAGE_CONSTRAINTS);
//
//        // non-empty preamble
//        assertParseFailure(parser, CommandTestUtil.PREAMBLE_NON_EMPTY + CommandTestUtil.NAME_DESC_BOB
//                        + CommandTestUtil.PHONE_DESC_BOB + CommandTestUtil.EMAIL_DESC_BOB
//                        + CommandTestUtil.ADDRESS_DESC_BOB + CommandTestUtil.TAG_DESC_HUSBAND + CommandTestUtil.TAG_DESC_FRIEND,
//                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLogCommand.MESSAGE_USAGE));
//    }
}

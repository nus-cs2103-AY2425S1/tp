package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTHDATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPatients.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.patient.Birthdate;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Sex;
import seedu.address.testutil.PatientBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Patient expectedPatient = new PatientBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB
                + BIRTHDATE_DESC_BOB + PHONE_DESC_BOB, new AddCommand(expectedPatient));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedPatientString = NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB
                + BIRTHDATE_DESC_BOB + PHONE_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPatientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple NRICs
        assertParseFailure(parser, NRIC_DESC_AMY + validExpectedPatientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // multiple sexes
        assertParseFailure(parser, SEX_DESC_AMY + validExpectedPatientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEX));

        // multiple birthdates
        assertParseFailure(parser, BIRTHDATE_DESC_AMY + validExpectedPatientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BIRTHDATE));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPatientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPatientString + NAME_DESC_AMY + NRIC_DESC_AMY + SEX_DESC_AMY + BIRTHDATE_DESC_AMY
                        + PHONE_DESC_AMY + validExpectedPatientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_NRIC, PREFIX_SEX,
                        PREFIX_BIRTHDATE, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPatientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid NRIC
        assertParseFailure(parser, INVALID_NRIC_DESC + validExpectedPatientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // invalid Sex
        assertParseFailure(parser, INVALID_SEX_DESC + validExpectedPatientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEX));

        // invalid Birthdate
        assertParseFailure(parser, INVALID_BIRTHDATE_DESC + validExpectedPatientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BIRTHDATE));

        // invalid Phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPatientString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPatientString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid NRIC
        assertParseFailure(parser, validExpectedPatientString + INVALID_NRIC_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // invalid Sex
        assertParseFailure(parser, validExpectedPatientString + INVALID_SEX_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEX));

        // invalid Birthdate
        assertParseFailure(parser, validExpectedPatientString + INVALID_BIRTHDATE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BIRTHDATE));

        // invalid Phone
        assertParseFailure(parser, validExpectedPatientString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        String expectedMessageForNoPrefix = String.format("No valid prefixes found \n" + AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                        + PHONE_DESC_BOB, expectedMessage);

        // missing NRIC prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_NRIC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + PHONE_DESC_BOB, expectedMessage);

        // missing SEX prefix
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + VALID_SEX_BOB + BIRTHDATE_DESC_BOB
                        + PHONE_DESC_BOB, expectedMessage);

        // missing Birthdate prefix
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + VALID_BIRTHDATE_BOB
                        + PHONE_DESC_BOB, expectedMessage);

        // missing Phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + VALID_PHONE_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_NRIC_BOB + VALID_SEX_BOB + VALID_BIRTHDATE_BOB
                + VALID_PHONE_BOB, expectedMessageForNoPrefix);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                        + PHONE_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid NRIC
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_NRIC_DESC + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                        + PHONE_DESC_BOB, Nric.MESSAGE_CONSTRAINTS);

        // invalid Sex
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + INVALID_SEX_DESC + BIRTHDATE_DESC_BOB
                + PHONE_DESC_BOB, Sex.MESSAGE_CONSTRAINTS);

        // invalid Birthdate
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + INVALID_BIRTHDATE_DESC
                        + PHONE_DESC_BOB, Birthdate.MESSAGE_CONSTRAINTS);

        // invalid Phone
        assertParseFailure(parser, NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);


        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + NRIC_DESC_BOB + SEX_DESC_BOB + INVALID_BIRTHDATE_DESC
                        + PHONE_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB
                        + BIRTHDATE_DESC_BOB + PHONE_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}

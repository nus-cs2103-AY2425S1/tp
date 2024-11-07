package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DIAGNOSIS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DIAGNOSIS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DIAGNOSIS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEDICATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WARD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MEDICATION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MEDICATION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIAGNOSIS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICATION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WARD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.WARD_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WARD_DESC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIAGNOSIS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WARD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Diagnosis;
import seedu.address.model.person.Id;
import seedu.address.model.person.Medication;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Ward;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_compulsoryFieldsOnly_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withId(VALID_ID_BOB)
                .withWard(VALID_WARD_BOB).build();

        // name, id, ward present
        assertParseSuccess(parser, NAME_DESC_BOB + ID_DESC_BOB + WARD_DESC_BOB,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldsWithDiagnosis_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withId(VALID_ID_BOB)
                .withWard(VALID_WARD_BOB).withDiagnosis(VALID_DIAGNOSIS_BOB).build();

        // name, id, ward, (diagnosis) present
        assertParseSuccess(parser, NAME_DESC_BOB + ID_DESC_BOB + WARD_DESC_BOB + DIAGNOSIS_DESC_BOB,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldsWithMedication_success() {
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_BOB).withId(VALID_ID_BOB)
                .withWard(VALID_WARD_BOB).withMedication(VALID_MEDICATION_BOB).build();

        // name, id, ward, (medication) present
        assertParseSuccess(parser, NAME_DESC_BOB + ID_DESC_BOB + WARD_DESC_BOB + MEDICATION_DESC_BOB,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + ID_DESC_BOB + WARD_DESC_BOB
                        + DIAGNOSIS_DESC_BOB + MEDICATION_DESC_AMY,
                new AddCommand(expectedPerson));

    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + ID_DESC_BOB + WARD_DESC_BOB + DIAGNOSIS_DESC_BOB
                + MEDICATION_DESC_BOB;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
        // multiple id
        assertParseFailure(parser, ID_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ID));
        // multiple ward
        assertParseFailure(parser, WARD_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WARD));
        // multiple diagnosis
        assertParseFailure(parser, DIAGNOSIS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DIAGNOSIS));
        // multiple medication
        assertParseFailure(parser, MEDICATION_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEDICATION));


        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + ID_DESC_AMY + WARD_DESC_AMY + NAME_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ID, PREFIX_WARD));


        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid id
        assertParseFailure(parser, INVALID_ID_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ID));
        // invalid ward
        assertParseFailure(parser, INVALID_WARD_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WARD));
        // invalid diagnosis
        assertParseFailure(parser, INVALID_DIAGNOSIS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DIAGNOSIS));
        // invalid medication
        assertParseFailure(parser, INVALID_MEDICATION_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEDICATION));


        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid id
        assertParseFailure(parser, validExpectedPersonString + INVALID_ID_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ID));

        // invalid ward
        assertParseFailure(parser, validExpectedPersonString + INVALID_WARD_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_WARD));

        // invalid diagnosis
        assertParseFailure(parser, validExpectedPersonString + INVALID_DIAGNOSIS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DIAGNOSIS));

        // invalid medication
        assertParseFailure(parser, validExpectedPersonString + INVALID_MEDICATION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MEDICATION));

    }
    /* for JC and Nic to look at
    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    */

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + ID_DESC_BOB + WARD_DESC_BOB + DIAGNOSIS_DESC_BOB
                        + MEDICATION_DESC_BOB, expectedMessage);

        // missing id prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_ID_BOB + WARD_DESC_BOB + DIAGNOSIS_DESC_BOB
                        + MEDICATION_DESC_BOB, expectedMessage);

        // missing ward prefix
        assertParseFailure(parser, NAME_DESC_BOB + ID_DESC_BOB + VALID_WARD_BOB + DIAGNOSIS_DESC_BOB
                        + MEDICATION_DESC_BOB, expectedMessage);

        // missing diagnosis prefix
        assertParseFailure(parser, NAME_DESC_BOB + ID_DESC_BOB + VALID_WARD_BOB + DIAGNOSIS_DESC_BOB
                        + MEDICATION_DESC_BOB, expectedMessage);

        // missing medication prefix
        assertParseFailure(parser, NAME_DESC_BOB + ID_DESC_BOB + VALID_WARD_BOB + DIAGNOSIS_DESC_BOB
                + MEDICATION_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_ID_BOB + VALID_WARD_BOB + VALID_DIAGNOSIS_BOB
                + VALID_MEDICATION_BOB,
                expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + ID_DESC_BOB + WARD_DESC_BOB + DIAGNOSIS_DESC_BOB
                        + MEDICATION_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid id
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_ID_DESC + WARD_DESC_BOB + DIAGNOSIS_DESC_BOB
                        + MEDICATION_DESC_BOB, Id.MESSAGE_CONSTRAINTS);

        // invalid ward
        assertParseFailure(parser, NAME_DESC_BOB + ID_DESC_BOB + INVALID_WARD_DESC + DIAGNOSIS_DESC_BOB
                        + MEDICATION_DESC_BOB, Ward.MESSAGE_CONSTRAINTS);

        // invalid diagnosis
        assertParseFailure(parser, NAME_DESC_BOB + ID_DESC_BOB + WARD_DESC_BOB + INVALID_DIAGNOSIS_DESC
                        + MEDICATION_DESC_BOB, Diagnosis.MESSAGE_CONSTRAINTS);

        // invalid medication prefix
        assertParseFailure(parser, NAME_DESC_BOB + ID_DESC_BOB + WARD_DESC_BOB + DIAGNOSIS_DESC_BOB
                + INVALID_MEDICATION_DESC, Medication.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + ID_DESC_BOB + WARD_DESC_BOB + INVALID_DIAGNOSIS_DESC
                + MEDICATION_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + ID_DESC_BOB + WARD_DESC_BOB
                + DIAGNOSIS_DESC_BOB + MEDICATION_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }

}

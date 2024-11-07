package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGIES_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGIES_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGIES_TO_REMOVE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EXISTINGCONDITION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EXISTINGCONDITION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.HEALTHRISK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.HEALTHRISK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTHDATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NOKNAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NOKNAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NOKPHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NOKPHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NOTE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGIES_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGIES_TO_REMOVE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXISTINGCONDITION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEALTHRISK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKPHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_AMY;
import static seedu.address.logic.commands.EditCommand.MESSAGE_NRIC_EMPTY;
import static seedu.address.logic.commands.EditCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXISTINGCONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHRISK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.patient.Nric.MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.address.model.patient.Birthdate;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Sex;
import seedu.address.testutil.EditPatientDescriptorBuilder;


public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);

    private static final String MESSAGE_EMPTY_NRIC = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_NRIC_EMPTY);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no NRIC specified
        assertParseFailure(parser, NAME_DESC_AMY + SEX_DESC_AMY + BIRTHDATE_DESC_AMY, MESSAGE_EMPTY_NRIC);

        // no field specified
        assertParseFailure(parser, VALID_NRIC_AMY, EditCommand.MESSAGE_NOT_EDITED);

        // no NRIC and no field specified
        assertParseFailure(parser, "", MESSAGE_EMPTY_NRIC);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // Invalid NRIC with lowercase
        assertParseFailure(parser, INVALID_NRIC_DESC + NAME_DESC_AMY, MESSAGE_CONSTRAINTS);

        // Invalid NRIC with wrong number of characters
        assertParseFailure(parser, "T012345R" + NAME_DESC_AMY, MESSAGE_CONSTRAINTS);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, VALID_NRIC_AMY + " some random string" + NAME_DESC_AMY,
                MESSAGE_CONSTRAINTS);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, VALID_NRIC_AMY + " pn/ string" + NAME_DESC_AMY, MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, VALID_NRIC_AMY + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, VALID_NRIC_AMY + INVALID_NRIC_DESC,
                MESSAGE_CONSTRAINTS); // invalid nric
        assertParseFailure(parser, VALID_NRIC_AMY + INVALID_SEX_DESC,
                Sex.MESSAGE_CONSTRAINTS); // invalid sex
        assertParseFailure(parser, VALID_NRIC_AMY + INVALID_BIRTHDATE_DESC,
                Birthdate.MESSAGE_CONSTRAINTS); // invalid birthdate

        assertParseFailure(parser, VALID_NRIC_AMY + INVALID_NRIC_DESC + SEX_DESC_AMY,
                MESSAGE_CONSTRAINTS); // invalid nric followed by valid sex

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, VALID_NRIC_AMY + INVALID_NAME_DESC + INVALID_NRIC_DESC
                        + BIRTHDATE_DESC_AMY + SEX_DESC_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Nric targetNric = new Nric("T0123456A");
        String userInput = targetNric + NAME_DESC_AMY + NRIC_DESC_AMY + SEX_DESC_AMY + BIRTHDATE_DESC_AMY
                + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + BLOODTYPE_DESC_AMY + NOKNAME_DESC_AMY + NOKPHONE_DESC_AMY + ALLERGIES_DESC_AMY + HEALTHRISK_DESC_AMY
                + EXISTINGCONDITION_DESC_AMY + NOTE_DESC_AMY;

        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withName(VALID_NAME_AMY)
                .withNric(VALID_NRIC_AMY).withSex(VALID_SEX_AMY).withBirthDate(VALID_BIRTHDATE_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withBloodType(VALID_BLOODTYPE_AMY).withNokName(VALID_NOKNAME_AMY).withNokPhone(VALID_NOKPHONE_AMY)
                .withAllergiesToAdd(VALID_ALLERGIES_AMY).withHealthRisk(VALID_HEALTHRISK_AMY)
                .withExistingCondition(VALID_EXISTINGCONDITION_AMY).withNote(VALID_NOTE_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetNric, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Nric targetNric = new Nric("T0123456A");
        String userInput = targetNric + NRIC_DESC_BOB + SEX_DESC_AMY;

        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withNric(VALID_NRIC_BOB)
                .withSex(VALID_SEX_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetNric, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Nric targetNric = new Nric("T0123456A");

        // name
        String userInput = targetNric + NAME_DESC_AMY;
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // nric
        userInput = targetNric + NRIC_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withNric(VALID_NRIC_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // sex
        userInput = targetNric + SEX_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withSex(VALID_SEX_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // birthdate
        userInput = targetNric + BIRTHDATE_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withBirthDate(VALID_BIRTHDATE_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetNric + PHONE_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetNric + EMAIL_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetNric + ADDRESS_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // blood type
        userInput = targetNric + BLOODTYPE_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withBloodType(VALID_BLOODTYPE_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // next-of-kin name
        userInput = targetNric + NOKNAME_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withNokName(VALID_NOKNAME_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // next-of-kin phone
        userInput = targetNric + NOKPHONE_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withNokPhone(VALID_NOKPHONE_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // add allergies
        userInput = targetNric + ALLERGIES_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withAllergiesToAdd(VALID_ALLERGIES_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // remove allergies
        userInput = targetNric + ALLERGIES_TO_REMOVE_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withAllergiesToRemove(VALID_ALLERGIES_TO_REMOVE_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // health risk
        userInput = targetNric + HEALTHRISK_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withHealthRisk(VALID_HEALTHRISK_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // existing condition
        userInput = targetNric + EXISTINGCONDITION_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withExistingCondition(VALID_EXISTINGCONDITION_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // note
        userInput = targetNric + NOTE_DESC_AMY;
        descriptor = new EditPatientDescriptorBuilder().withNote(VALID_NOTE_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonHealthServiceValue_failure()

        Nric targetNric = new Nric("T0123456A");

        // valid followed by invalid
        String userInput = targetNric + NRIC_DESC_BOB + INVALID_NRIC_DESC;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // invalid followed by valid
        userInput = targetNric + INVALID_NRIC_DESC + NRIC_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // mulltiple valid name fields repeated
        userInput = targetNric + NAME_DESC_BOB + NAME_DESC_AMY + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGIES_DESC_BOB + HEALTHRISK_DESC_BOB
                + EXISTINGCONDITION_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple valid nric fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + NRIC_DESC_AMY + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGIES_DESC_BOB + HEALTHRISK_DESC_BOB
                + EXISTINGCONDITION_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // multiple valid sex fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + SEX_DESC_AMY + BIRTHDATE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGIES_DESC_BOB + HEALTHRISK_DESC_BOB
                + EXISTINGCONDITION_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEX));

        // multiple valid birthdate fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB + BIRTHDATE_DESC_AMY
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGIES_DESC_BOB + HEALTHRISK_DESC_BOB
                + EXISTINGCONDITION_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BIRTHDATE));

        // multiple valid phone fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + PHONE_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGIES_DESC_BOB + HEALTHRISK_DESC_BOB
                + EXISTINGCONDITION_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid email fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + EMAIL_DESC_AMY + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGIES_DESC_BOB + HEALTHRISK_DESC_BOB
                + EXISTINGCONDITION_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple valid address fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + ADDRESS_DESC_AMY
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGIES_DESC_BOB + HEALTHRISK_DESC_BOB
                + EXISTINGCONDITION_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple valid blood type fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + BLOODTYPE_DESC_AMY + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGIES_DESC_BOB
                + HEALTHRISK_DESC_BOB + EXISTINGCONDITION_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BLOODTYPE));

        // multiple valid next-of-kin name fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKNAME_DESC_AMY + NOKPHONE_DESC_BOB + ALLERGIES_DESC_BOB
                + HEALTHRISK_DESC_BOB + EXISTINGCONDITION_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NOKNAME));

        // multiple valid next-of-kin phone fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + NOKPHONE_DESC_AMY + ALLERGIES_DESC_BOB
                + HEALTHRISK_DESC_BOB + EXISTINGCONDITION_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NOKPHONE));

        // multiple valid health risk fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGIES_DESC_BOB + HEALTHRISK_DESC_BOB
                + HEALTHRISK_DESC_AMY + EXISTINGCONDITION_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HEALTHRISK));

        // multiple valid existing condition fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGIES_DESC_BOB + HEALTHRISK_DESC_BOB
                + EXISTINGCONDITION_DESC_BOB + EXISTINGCONDITION_DESC_AMY + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EXISTINGCONDITION));

        // multiple valid note fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGIES_DESC_BOB + HEALTHRISK_DESC_BOB
                + EXISTINGCONDITION_DESC_BOB + NOTE_DESC_BOB + NOTE_DESC_AMY;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NOTE));


        // multiple invalid values
        userInput = targetNric + INVALID_NRIC_DESC + INVALID_BIRTHDATE_DESC + INVALID_SEX_DESC
                + INVALID_NRIC_DESC + INVALID_BIRTHDATE_DESC + INVALID_SEX_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(
                PREFIX_NRIC, PREFIX_BIRTHDATE, PREFIX_SEX));
    }
}

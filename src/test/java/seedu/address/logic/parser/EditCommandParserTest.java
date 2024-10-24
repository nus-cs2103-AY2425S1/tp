package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BLOODTYPE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.HEALTHRECORD_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.HEALTHRECORD_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.HEALTHRISK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.HEALTHRISK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.HEALTHSERVICE_DESC_BLOOD_TEST;
import static seedu.address.logic.commands.CommandTestUtil.HEALTHSERVICE_DESC_VACCINATION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTHDATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HEALTHSERVICE_DESC;
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
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BLOODTYPE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEALTHRECORD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEALTHRISK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEALTHSERVICE_VACCINATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOKPHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODTYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHRECORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHRISK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHSERVICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOKPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.healthservice.HealthService;
import seedu.address.model.person.Birthdate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Sex;
import seedu.address.testutil.EditPersonDescriptorBuilder;


public class EditCommandParserTest {

    private static final String HEALTHSERVICE_EMPTY = " " + PREFIX_HEALTHSERVICE;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no NRIC specified
        assertParseFailure(parser, NAME_DESC_AMY + SEX_DESC_AMY + BIRTHDATE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, VALID_NRIC_AMY, EditCommand.MESSAGE_NOT_EDITED);

        // no NRIC and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // Invalid NRIC with lowercase
        assertParseFailure(parser, INVALID_NRIC_DESC + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // Invalid NRIC with wrong number of characters
        assertParseFailure(parser, "T012345R" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, VALID_NRIC_AMY + " some random string" + NAME_DESC_AMY,
                MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, VALID_NRIC_AMY + " pn/ string" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, VALID_NRIC_AMY + INVALID_NAME_DESC,
                Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, VALID_NRIC_AMY + INVALID_NRIC_DESC,
                Nric.MESSAGE_CONSTRAINTS); // invalid nric
        assertParseFailure(parser, VALID_NRIC_AMY + INVALID_SEX_DESC,
                Sex.MESSAGE_CONSTRAINTS); // invalid sex
        assertParseFailure(parser, VALID_NRIC_AMY + INVALID_BIRTHDATE_DESC,
                Birthdate.MESSAGE_CONSTRAINTS); // invalid birthdate
        assertParseFailure(parser, VALID_NRIC_AMY + INVALID_HEALTHSERVICE_DESC,
                HealthService.MESSAGE_CONSTRAINTS); // invalid health service

        assertParseFailure(parser, VALID_NRIC_AMY + INVALID_NRIC_DESC + SEX_DESC_AMY,
                Nric.MESSAGE_CONSTRAINTS); // invalid nric followed by valid sex

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, VALID_NRIC_AMY + INVALID_NAME_DESC + INVALID_NRIC_DESC
                        + BIRTHDATE_DESC_AMY + SEX_DESC_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Nric targetNric = new Nric("T0123456A");
        String userInput = targetNric + NAME_DESC_AMY + NRIC_DESC_AMY + SEX_DESC_AMY + BIRTHDATE_DESC_AMY
                + HEALTHSERVICE_DESC_VACCINATION + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + BLOODTYPE_DESC_AMY + NOKNAME_DESC_AMY + NOKPHONE_DESC_AMY + ALLERGY_DESC_AMY + HEALTHRISK_DESC_AMY
                + HEALTHRECORD_DESC_AMY + APPOINTMENT_DESC_AMY + NOTE_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withNric(VALID_NRIC_AMY).withSex(VALID_SEX_AMY).withBirthDate(VALID_BIRTHDATE_AMY)
                .withHealthServices(VALID_HEALTHSERVICE_VACCINATION).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withBloodType(VALID_BLOODTYPE_AMY)
                .withNokName(VALID_NOKNAME_AMY).withNokPhone(VALID_NOKPHONE_AMY).withAllergy(VALID_ALLERGY_AMY)
                .withHealthRisk(VALID_HEALTHRISK_AMY).withHealthRecord(VALID_HEALTHRECORD_AMY)
                .withAppts(VALID_APPOINTMENT_AMY).withNote(VALID_NOTE_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetNric, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Nric targetNric = new Nric("T0123456A");
        String userInput = targetNric + NRIC_DESC_BOB + SEX_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withNric(VALID_NRIC_BOB)
                .withSex(VALID_SEX_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetNric, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Nric targetNric = new Nric("T0123456A");

        // name
        String userInput = targetNric + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // nric
        userInput = targetNric + NRIC_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withNric(VALID_NRIC_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // sex
        userInput = targetNric + SEX_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withSex(VALID_SEX_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // birthdate
        userInput = targetNric + BIRTHDATE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withBirthDate(VALID_BIRTHDATE_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // health services
        userInput = targetNric + HEALTHSERVICE_DESC_VACCINATION;
        descriptor = new EditPersonDescriptorBuilder().withHealthServices(VALID_HEALTHSERVICE_VACCINATION).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetNric + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetNric + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetNric + ADDRESS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // blood type
        userInput = targetNric + BLOODTYPE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withBloodType(VALID_BLOODTYPE_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // next-of-kin name
        userInput = targetNric + NOKNAME_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withNokName(VALID_NOKNAME_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // next-of-kin phone
        userInput = targetNric + NOKPHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withNokPhone(VALID_NOKPHONE_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // allergy
        userInput = targetNric + ALLERGY_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAllergy(VALID_ALLERGY_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // health risk
        userInput = targetNric + HEALTHRISK_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withHealthRisk(VALID_HEALTHRISK_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // health record
        userInput = targetNric + HEALTHRECORD_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withHealthRecord(VALID_HEALTHRECORD_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // appointment
        userInput = targetNric + APPOINTMENT_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAppts(VALID_APPOINTMENT_AMY).build();
        expectedCommand = new EditCommand(targetNric, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // note
        userInput = targetNric + NOTE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withNote(VALID_NOTE_AMY).build();
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
                + HEALTHSERVICE_DESC_VACCINATION + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGY_DESC_BOB + HEALTHRISK_DESC_BOB
                + HEALTHRECORD_DESC_BOB + APPOINTMENT_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple valid nric fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + NRIC_DESC_AMY + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + HEALTHSERVICE_DESC_VACCINATION + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGY_DESC_BOB + HEALTHRISK_DESC_BOB
                + HEALTHRECORD_DESC_BOB + APPOINTMENT_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // multiple valid sex fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + SEX_DESC_AMY + BIRTHDATE_DESC_BOB
                + HEALTHSERVICE_DESC_VACCINATION + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGY_DESC_BOB + HEALTHRISK_DESC_BOB
                + HEALTHRECORD_DESC_BOB + APPOINTMENT_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SEX));

        // multiple valid birthdate fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB + BIRTHDATE_DESC_AMY
                + HEALTHSERVICE_DESC_VACCINATION + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGY_DESC_BOB + HEALTHRISK_DESC_BOB
                + HEALTHRECORD_DESC_BOB + APPOINTMENT_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BIRTHDATE));

        // multiple valid health service fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + HEALTHSERVICE_DESC_VACCINATION + HEALTHSERVICE_DESC_BLOOD_TEST + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGY_DESC_BOB
                + HEALTHRISK_DESC_BOB + HEALTHRECORD_DESC_BOB + APPOINTMENT_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HEALTHSERVICE));

        // multiple valid phone fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + HEALTHSERVICE_DESC_VACCINATION + PHONE_DESC_BOB + PHONE_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGY_DESC_BOB + HEALTHRISK_DESC_BOB
                + HEALTHRECORD_DESC_BOB + APPOINTMENT_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid email fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + HEALTHSERVICE_DESC_VACCINATION + PHONE_DESC_BOB + EMAIL_DESC_BOB + EMAIL_DESC_AMY + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGY_DESC_BOB + HEALTHRISK_DESC_BOB
                + HEALTHRECORD_DESC_BOB + APPOINTMENT_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple valid address fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + HEALTHSERVICE_DESC_VACCINATION + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + ADDRESS_DESC_AMY
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGY_DESC_BOB + HEALTHRISK_DESC_BOB
                + HEALTHRECORD_DESC_BOB + APPOINTMENT_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple valid blood type fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + HEALTHSERVICE_DESC_VACCINATION + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + BLOODTYPE_DESC_AMY + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGY_DESC_BOB
                + HEALTHRISK_DESC_BOB + HEALTHRECORD_DESC_BOB + APPOINTMENT_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BLOODTYPE));

        // multiple valid next-of-kin name fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + HEALTHSERVICE_DESC_VACCINATION + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKNAME_DESC_AMY + NOKPHONE_DESC_BOB + ALLERGY_DESC_BOB
                + HEALTHRISK_DESC_BOB + HEALTHRECORD_DESC_BOB + APPOINTMENT_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NOKNAME));

        // multiple valid next-of-kin phone fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + HEALTHSERVICE_DESC_VACCINATION + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + NOKPHONE_DESC_AMY + ALLERGY_DESC_BOB
                + HEALTHRISK_DESC_BOB + HEALTHRECORD_DESC_BOB + APPOINTMENT_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NOKPHONE));

        // multiple valid allergy fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + HEALTHSERVICE_DESC_VACCINATION + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGY_DESC_BOB + ALLERGY_DESC_AMY
                + HEALTHRISK_DESC_BOB + HEALTHRECORD_DESC_BOB + APPOINTMENT_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ALLERGY));

        // multiple valid health risk fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + HEALTHSERVICE_DESC_VACCINATION + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGY_DESC_BOB + HEALTHRISK_DESC_BOB
                + HEALTHRISK_DESC_AMY + HEALTHRECORD_DESC_BOB + APPOINTMENT_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HEALTHRISK));

        // multiple valid health record fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + HEALTHSERVICE_DESC_VACCINATION + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGY_DESC_BOB + HEALTHRISK_DESC_BOB
                + HEALTHRECORD_DESC_BOB + HEALTHRECORD_DESC_AMY + APPOINTMENT_DESC_BOB + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HEALTHRECORD));

        // multiple valid appointment fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + HEALTHSERVICE_DESC_VACCINATION + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGY_DESC_BOB + HEALTHRISK_DESC_BOB
                + HEALTHRECORD_DESC_BOB + APPOINTMENT_DESC_BOB + APPOINTMENT_DESC_AMY + NOTE_DESC_BOB;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_APPOINTMENT));

        // multiple valid note fields repeated
        userInput = targetNric + NAME_DESC_BOB + NRIC_DESC_BOB + SEX_DESC_BOB + BIRTHDATE_DESC_BOB
                + HEALTHSERVICE_DESC_VACCINATION + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + BLOODTYPE_DESC_BOB + NOKNAME_DESC_BOB + NOKPHONE_DESC_BOB + ALLERGY_DESC_BOB + HEALTHRISK_DESC_BOB
                + HEALTHRECORD_DESC_BOB + APPOINTMENT_DESC_BOB + NOTE_DESC_BOB + NOTE_DESC_AMY;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NOTE));


        // multiple invalid values
        userInput = targetNric + INVALID_NRIC_DESC + INVALID_BIRTHDATE_DESC + INVALID_SEX_DESC
                + INVALID_NRIC_DESC + INVALID_BIRTHDATE_DESC + INVALID_SEX_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(
                PREFIX_NRIC, PREFIX_BIRTHDATE, PREFIX_SEX));
    }

    @Test
    public void parse_resetHealthServices_success() {
        Nric targetNric = new Nric("T0123456A");
        String userInput = targetNric + HEALTHSERVICE_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withHealthServices().build();
        EditCommand expectedCommand = new EditCommand(targetNric, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

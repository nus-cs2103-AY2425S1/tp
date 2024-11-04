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
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIAGNOSIS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICATION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WARD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WARD_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WARD_DESC_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIAGNOSIS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WARD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Diagnosis;
import seedu.address.model.person.Id;
import seedu.address.model.person.Medication;
import seedu.address.model.person.Name;
import seedu.address.model.person.Ward;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    // private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();
    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 s/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_ID_DESC, Id.MESSAGE_CONSTRAINTS); // invalid id
        assertParseFailure(parser, "1" + INVALID_WARD_DESC, Ward.MESSAGE_CONSTRAINTS); // invalid ward
        assertParseFailure(parser, "1" + INVALID_DIAGNOSIS_DESC,
                Diagnosis.MESSAGE_CONSTRAINTS); // invalid diagnosis
        assertParseFailure(parser, "1" + INVALID_MEDICATION_DESC,
                Medication.MESSAGE_CONSTRAINTS); // invalid medication

        // invalid id followed by valid ward
        assertParseFailure(parser, "1" + INVALID_ID_DESC + WARD_DESC_AMY, Id.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_ID_DESC + INVALID_WARD_DESC,
                // + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + ID_DESC_BOB + NAME_DESC_AMY + WARD_DESC_AMY
                + DIAGNOSIS_DESC_AMY + MEDICATION_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withId(VALID_ID_BOB).withWard(VALID_WARD_AMY).withDiagnosis(VALID_DIAGNOSIS_AMY)
                .withMedication(VALID_MEDICATION_AMY).build();

        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + ID_DESC_BOB + DIAGNOSIS_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withId(VALID_ID_BOB)
                .withDiagnosis(VALID_DIAGNOSIS_AMY)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // id
        userInput = targetIndex.getOneBased() + ID_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withId(VALID_ID_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // ward
        userInput = targetIndex.getOneBased() + WARD_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withWard(VALID_WARD_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // diagnosis
        userInput = targetIndex.getOneBased() + DIAGNOSIS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withDiagnosis(VALID_DIAGNOSIS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // medication
        userInput = targetIndex.getOneBased() + MEDICATION_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withMedication(VALID_MEDICATION_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_ID_DESC + ID_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ID));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + ID_DESC_BOB + INVALID_ID_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ID));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + ID_DESC_AMY + ID_DESC_BOB;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ID));

        userInput = targetIndex.getOneBased() + ID_DESC_AMY + WARD_DESC_AMY + DIAGNOSIS_DESC_AMY + MEDICATION_DESC_AMY
                + ID_DESC_AMY + WARD_DESC_AMY + DIAGNOSIS_DESC_AMY + MEDICATION_DESC_AMY
                + ID_DESC_BOB + WARD_DESC_BOB + DIAGNOSIS_DESC_BOB + MEDICATION_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ID,
                PREFIX_WARD, PREFIX_DIAGNOSIS, PREFIX_MEDICATION));


        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_ID_DESC + INVALID_ID_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ID));

        userInput = targetIndex.getOneBased() + INVALID_ID_DESC + INVALID_WARD_DESC + INVALID_DIAGNOSIS_DESC
                + INVALID_MEDICATION_DESC + INVALID_ID_DESC + INVALID_WARD_DESC + INVALID_DIAGNOSIS_DESC
                + INVALID_MEDICATION_DESC;


        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ID, PREFIX_WARD,
                PREFIX_DIAGNOSIS, PREFIX_MEDICATION));

    }

}

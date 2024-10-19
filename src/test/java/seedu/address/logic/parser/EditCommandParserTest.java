package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.BIRTHDATE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.HEALTHSERVICE_DESC_BLOOD_TEST;
import static seedu.address.logic.commands.CommandTestUtil.HEALTHSERVICE_DESC_VACCINATION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BIRTHDATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HEALTHSERVICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.SEX_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEALTHSERVICE_BLOOD_TEST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEALTHSERVICE_VACCINATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEALTHSERVICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
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
        assertParseFailure(parser, "1 f/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_NRIC_DESC, Nric.MESSAGE_CONSTRAINTS); // invalid nric
        assertParseFailure(parser, "1" + INVALID_SEX_DESC, Sex.MESSAGE_CONSTRAINTS); // invalid sex
        assertParseFailure(parser, "1" + INVALID_BIRTHDATE_DESC,
                Birthdate.MESSAGE_CONSTRAINTS); // invalid birthdate
        assertParseFailure(parser, "1" + INVALID_HEALTHSERVICE_DESC,
                HealthService.MESSAGE_CONSTRAINTS); // invalid health service

        // invalid nric followed by valid sex
        assertParseFailure(parser, "1" + INVALID_NRIC_DESC + SEX_DESC_AMY, Nric.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_HEALTHSERVICE} alone will reset the
        // health services of the {@code Person} being edited,
        // parsing it together with a valid health service results in error
        assertParseFailure(parser, "1" + HEALTHSERVICE_DESC_VACCINATION + HEALTHSERVICE_DESC_BLOOD_TEST
                + HEALTHSERVICE_EMPTY, HealthService.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + HEALTHSERVICE_DESC_VACCINATION + HEALTHSERVICE_EMPTY
                + HEALTHSERVICE_DESC_BLOOD_TEST, HealthService.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + HEALTHSERVICE_EMPTY + HEALTHSERVICE_DESC_VACCINATION
                + HEALTHSERVICE_DESC_BLOOD_TEST, HealthService.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_NRIC_DESC
                        + VALID_BIRTHDATE_AMY + VALID_SEX_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + NRIC_DESC_BOB + HEALTHSERVICE_DESC_VACCINATION
                + SEX_DESC_AMY + BIRTHDATE_DESC_AMY + NAME_DESC_AMY + HEALTHSERVICE_DESC_BLOOD_TEST;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withNric(VALID_NRIC_BOB).withSex(VALID_SEX_AMY).withBirthDate(VALID_BIRTHDATE_AMY)
                .withHealthServices(VALID_HEALTHSERVICE_VACCINATION, VALID_HEALTHSERVICE_BLOOD_TEST).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + NRIC_DESC_BOB + SEX_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withNric(VALID_NRIC_BOB)
                .withSex(VALID_SEX_AMY).build();
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

        // nric
        userInput = targetIndex.getOneBased() + NRIC_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withNric(VALID_NRIC_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // sex
        userInput = targetIndex.getOneBased() + SEX_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withSex(VALID_SEX_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // birthdate
        userInput = targetIndex.getOneBased() + BIRTHDATE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withBirthDate(VALID_BIRTHDATE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // health services
        userInput = targetIndex.getOneBased() + HEALTHSERVICE_DESC_VACCINATION;
        descriptor = new EditPersonDescriptorBuilder().withHealthServices(VALID_HEALTHSERVICE_VACCINATION).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonHealthServiceValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_NRIC_DESC + NRIC_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + NRIC_DESC_BOB + INVALID_NRIC_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + NRIC_DESC_AMY + BIRTHDATE_DESC_AMY + SEX_DESC_AMY
                + HEALTHSERVICE_DESC_VACCINATION + NRIC_DESC_AMY + BIRTHDATE_DESC_AMY + SEX_DESC_AMY
                + HEALTHSERVICE_DESC_VACCINATION + NRIC_DESC_BOB + BIRTHDATE_DESC_BOB + SEX_DESC_BOB
                + HEALTHSERVICE_DESC_VACCINATION;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_NRIC_DESC + INVALID_BIRTHDATE_DESC + INVALID_SEX_DESC
                + INVALID_NRIC_DESC + INVALID_BIRTHDATE_DESC + INVALID_SEX_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));
    }

    @Test
    public void parse_resetHealthServices_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + HEALTHSERVICE_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withHealthServices().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

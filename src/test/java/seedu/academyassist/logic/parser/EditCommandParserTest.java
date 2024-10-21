package seedu.academyassist.logic.parser;

import static seedu.academyassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.academyassist.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.academyassist.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.academyassist.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.academyassist.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.academyassist.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.SUBJECT_DESC_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.academyassist.logic.commands.CommandTestUtil.VALID_SUBJECT_AMY;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.academyassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.academyassist.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.academyassist.logic.Messages;
import seedu.academyassist.logic.commands.EditCommand;
import seedu.academyassist.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.academyassist.model.person.Address;
import seedu.academyassist.model.person.Email;
import seedu.academyassist.model.person.Name;
import seedu.academyassist.model.person.Phone;
import seedu.academyassist.model.person.StudentId;
import seedu.academyassist.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no id specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "S10001", EditCommand.MESSAGE_NOT_EDITED);

        // no id and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // exceeds 5 digits
        assertParseFailure(parser, "S100010" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // incorrect Student ID format
        assertParseFailure(parser, "S0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "S10001 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "S10001 j/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "S10001" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "S10001" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "S10001" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "S10001" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address

        // invalid phone followed by valid email
        assertParseFailure(parser, "S10001" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "S10001" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY
                        + VALID_PHONE_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        StudentId targetStudentId = new StudentId("S10001");
        String userInput = targetStudentId + PHONE_DESC_BOB + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .build();
        EditCommand expectedCommand = new EditCommand(targetStudentId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        StudentId targetStudentId = new StudentId("S10001");
        String userInput = targetStudentId + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetStudentId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        StudentId targetStudentId = new StudentId("S10001");
        String userInput = targetStudentId + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetStudentId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetStudentId + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetStudentId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetStudentId + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetStudentId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetStudentId + ADDRESS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new EditCommand(targetStudentId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetStudentId + SUBJECT_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withSubjects(VALID_SUBJECT_AMY).build();
        expectedCommand = new EditCommand(targetStudentId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        String targetId = "S10001";
        String userInput = targetId + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetId + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = targetId + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + EMAIL_DESC_BOB;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));

        // multiple invalid values
        userInput = targetId + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));
    }
}

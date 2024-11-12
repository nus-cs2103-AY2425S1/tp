package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COURSE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENTID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_STUDENT;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_TUTOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_STUDENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Course;
import seedu.address.model.person.Email;
import seedu.address.model.person.Module;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.StudentId;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String ROLE_EMPTY = " " + PREFIX_ROLE;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_validModuleChange_success() {
        String studentId = "12345678";

        String userInput = studentId + " m/ GEC1044 CS1231S";

        Module oldModule = new Module("GEC1044");
        Module newModule = new Module("CS1231S");

        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        descriptor.setOldModule(oldModule);
        descriptor.setNewModule(newModule);

        EditCommand expectedCommand = new EditCommand(new StudentId(studentId), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingModuleArguments_failure() {
        String studentId = "12345678";
        String userInput = studentId + " m/";

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_moduleAndOtherFieldsPresent_failure() {
        String studentId = "12345678";
        String userInputWithExtraName = studentId + " m/ CS1010S CS1231S n/ Amy Bee";
        String userInputWithExtraPhone = studentId + " m/ CS1010S CS1231S p/ 11111111";
        String userInputWithExtraEmail = studentId + " m/ CS1010S CS1231S e/ amybee@gmail.com";
        String userInputWithExtraAddress = studentId + " m/ CS1010S CS1231S a/ 321 Clementi";
        String userInputWithExtraCourse = studentId + " m/ CS1010S CS1231S c/ Physics";
        String userInputWithExtraRole = studentId + " m/ CS1010S CS1231S r/ Student";

        assertParseFailure(parser, userInputWithExtraName, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, userInputWithExtraPhone, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, userInputWithExtraEmail, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, userInputWithExtraAddress, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, userInputWithExtraCourse, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditCommand.MESSAGE_USAGE));
        assertParseFailure(parser, userInputWithExtraRole, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noModulesProvided_noModuleChange() {
        String studentId = "12345678";
        String userInput = studentId + " " + NAME_DESC_AMY + " " + PHONE_DESC_BOB;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB)
                .build();
        EditCommand expectedCommand = new EditCommand(new StudentId(studentId), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedModuleFields_failure() {
        String studentId = "12345678";
        String userInput = studentId + " m/ GEC1044 CS1231S m/ CS1232S";

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_MODULE));
    }

    @Test
    public void parse_threeArgumentsForModule_failure() {
        String studentId = "12345678";
        // Simulating three module arguments
        String userInput = studentId + " m/ GEC1044 CS1231S CS1232S";

        // Expecting a ParseException due to the invalid number of arguments for modules
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "12345678", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid id
        assertParseFailure(parser, "invalid-id" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "12345678 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "12345678 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_STUDENTID_DESC, MESSAGE_INVALID_FORMAT); // invalid studentID
        assertParseFailure(parser, "12345678" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "12345678" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "12345678" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "12345678" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "12345678" + INVALID_COURSE_DESC, Course.MESSAGE_CONSTRAINTS); // invalid studentID
        assertParseFailure(parser, "12345678" + INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS); // invalid role

        // invalid phone followed by valid email
        assertParseFailure(parser, "12345678" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "12345678" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY
                        + VALID_PHONE_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String studentId = "12345678";
        String userInput = studentId + PHONE_DESC_BOB
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + ROLE_DESC_STUDENT;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withStudentId(studentId)
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withRole(VALID_ROLE_STUDENT).build();
        EditCommand expectedCommand = new EditCommand(new StudentId(studentId), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String studentId = "12345678";
        String userInput = studentId + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withStudentId(studentId)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY)
                .build();
        EditCommand expectedCommand = new EditCommand(new StudentId(studentId), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        String studentId = "12345678";

        // name
        String userInput = studentId + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withStudentId(studentId)
                .withName(VALID_NAME_AMY)
                .build();
        EditCommand expectedCommand = new EditCommand(new StudentId(studentId), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = studentId + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder()
                .withStudentId(studentId)
                .withPhone(VALID_PHONE_AMY)
                .build();
        expectedCommand = new EditCommand(new StudentId(studentId), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = studentId + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder()
                .withStudentId(studentId)
                .withEmail(VALID_EMAIL_AMY)
                .build();
        expectedCommand = new EditCommand(new StudentId(studentId), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = studentId + ADDRESS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder()
                .withStudentId(studentId)
                .withAddress(VALID_ADDRESS_AMY)
                .build();
        expectedCommand = new EditCommand(new StudentId(studentId), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // roles
        userInput = studentId + ROLE_DESC_STUDENT;
        descriptor = new EditPersonDescriptorBuilder()
                .withStudentId(studentId)
                .withRole(VALID_ROLE_STUDENT)
                .build();
        expectedCommand = new EditCommand(new StudentId(studentId), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonRoleValue_failure()

        // valid followed by invalid
        String studentId = "12345678";
        String userInput = studentId + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = studentId + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = studentId + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
                + ROLE_DESC_STUDENT + PHONE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + ROLE_DESC_STUDENT
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + ROLE_DESC_TUTOR;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_ROLE));

        // multiple invalid values
        userInput = studentId + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));
    }


}

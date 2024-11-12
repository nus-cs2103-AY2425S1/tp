package seedu.eventfulnus.logic.parser;

import static seedu.eventfulnus.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.ROLE_DESC_ATHLETE;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.ROLE_DESC_VOLUNTEER;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.VALID_ROLE_ATHLETE;
import static seedu.eventfulnus.logic.commands.CommandTestUtil.VALID_ROLE_VOLUNTEER;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.eventfulnus.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.eventfulnus.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eventfulnus.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.eventfulnus.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.eventfulnus.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.eventfulnus.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.eventfulnus.commons.core.index.Index;
import seedu.eventfulnus.logic.Messages;
import seedu.eventfulnus.logic.commands.EditCommand;
import seedu.eventfulnus.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.eventfulnus.model.person.Email;
import seedu.eventfulnus.model.person.Name;
import seedu.eventfulnus.model.person.Phone;
import seedu.eventfulnus.model.person.role.Role;
import seedu.eventfulnus.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String ROLE_EMPTY = " " + PREFIX_ROLE;

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
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS); // invalid role

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_ROLE} alone will reset the roles of the {@code Person} being edited,
        // parsing it together with a valid role results in error
        assertParseFailure(parser, "1" + ROLE_DESC_VOLUNTEER + ROLE_DESC_ATHLETE + ROLE_EMPTY,
                Role.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ROLE_DESC_VOLUNTEER + ROLE_EMPTY + ROLE_DESC_ATHLETE,
                Role.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ROLE_EMPTY + ROLE_DESC_VOLUNTEER + ROLE_DESC_ATHLETE,
                Role.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + ROLE_DESC_ATHLETE
                           + EMAIL_DESC_AMY + NAME_DESC_AMY + ROLE_DESC_VOLUNTEER;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY)
                .withRoles(VALID_ROLE_ATHLETE, VALID_ROLE_VOLUNTEER).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
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

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // roles
        userInput = targetIndex.getOneBased() + ROLE_DESC_VOLUNTEER;
        descriptor = new EditPersonDescriptorBuilder().withRoles(VALID_ROLE_VOLUNTEER).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonRoleValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_AMY + EMAIL_DESC_AMY
                    + ROLE_DESC_VOLUNTEER + PHONE_DESC_AMY + EMAIL_DESC_AMY + ROLE_DESC_VOLUNTEER
                    + PHONE_DESC_BOB + EMAIL_DESC_BOB + ROLE_DESC_ATHLETE;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL));
    }

    @Test
    public void parse_resetRoles_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + ROLE_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withRoles().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SKILL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_CUDA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_PYTHON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SKILL;
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
import seedu.address.model.common.Name;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.skill.Skill;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String SKILL_EMPTY = " " + PREFIX_SKILL;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, EditCommand.ENTITY_WORD
                + " " + VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, EditCommand.ENTITY_WORD + " 1", EditCommand.MESSAGE_NOT_EDITED);

        // no entity specified
        assertParseFailure(parser, "1 " + VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, EditCommand.ENTITY_WORD, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, EditCommand.ENTITY_WORD + " -5" + NAME_DESC_AMY, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, EditCommand.ENTITY_WORD + " 0" + NAME_DESC_AMY, MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, EditCommand.ENTITY_WORD + " 1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, EditCommand.ENTITY_WORD + " 1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, EditCommand.ENTITY_WORD + " 1"
                + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, EditCommand.ENTITY_WORD + " 1"
                + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, EditCommand.ENTITY_WORD + " 1"
                + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, EditCommand.ENTITY_WORD + " 1"
                + INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS); // invalid role
        assertParseFailure(parser, EditCommand.ENTITY_WORD + " 1"
                + INVALID_SKILL_DESC, Skill.MESSAGE_CONSTRAINTS); // invalid skill

        // invalid phone followed by valid email
        assertParseFailure(parser, EditCommand.ENTITY_WORD + " 1"
                + INVALID_PHONE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_SKILL} alone will reset the skills of the {@code Person} being edited,
        // parsing it together with a valid skill results in error
        assertParseFailure(parser, EditCommand.ENTITY_WORD + " 1"
                + SKILL_DESC_FRIEND + SKILL_DESC_HUSBAND
                + SKILL_EMPTY, Skill.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, EditCommand.ENTITY_WORD + " 1"
                + SKILL_DESC_FRIEND + SKILL_EMPTY
                + SKILL_DESC_HUSBAND, Skill.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, EditCommand.ENTITY_WORD + " 1"
                + SKILL_EMPTY + SKILL_DESC_FRIEND
                + SKILL_DESC_HUSBAND, Skill.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, EditCommand.ENTITY_WORD + " 1"
                        + INVALID_NAME_DESC + INVALID_EMAIL_DESC
                        + VALID_ROLE_AMY + VALID_PHONE_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = EditCommand.ENTITY_WORD + " " + targetIndex.getOneBased()
                + PHONE_DESC_BOB + SKILL_DESC_HUSBAND + EMAIL_DESC_AMY + ROLE_DESC_AMY
                + NAME_DESC_AMY + SKILL_DESC_FRIEND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_AMY).withRole(VALID_ROLE_AMY)
                .withSkills(VALID_SKILL_CUDA, VALID_SKILL_PYTHON).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = EditCommand.ENTITY_WORD + " " + targetIndex.getOneBased()
                + PHONE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = EditCommand.ENTITY_WORD + " " + targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = EditCommand.ENTITY_WORD + " " + targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = EditCommand.ENTITY_WORD + " " + targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // roles
        userInput = EditCommand.ENTITY_WORD + " " + targetIndex.getOneBased() + ROLE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withRole(VALID_ROLE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // skills
        userInput = EditCommand.ENTITY_WORD + " " + targetIndex.getOneBased() + SKILL_DESC_FRIEND;
        descriptor = new EditPersonDescriptorBuilder().withSkills(VALID_SKILL_PYTHON).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = EditCommand.ENTITY_WORD + " " + targetIndex.getOneBased()
                + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = EditCommand.ENTITY_WORD + " " + targetIndex.getOneBased()
                + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = EditCommand.ENTITY_WORD + " " + targetIndex.getOneBased()
                + PHONE_DESC_AMY + ROLE_DESC_AMY + EMAIL_DESC_AMY
                + SKILL_DESC_FRIEND + PHONE_DESC_AMY + ROLE_DESC_AMY + EMAIL_DESC_AMY + SKILL_DESC_FRIEND
                + PHONE_DESC_BOB + ROLE_DESC_BOB + EMAIL_DESC_BOB + SKILL_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ROLE));

        // multiple invalid values
        userInput = EditCommand.ENTITY_WORD + " " + targetIndex.getOneBased()
                + INVALID_PHONE_DESC + INVALID_ROLE_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_ROLE_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ROLE));
    }

    @Test
    public void parse_resetSkills_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = EditCommand.ENTITY_WORD + " " + targetIndex.getOneBased() + SKILL_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withSkills().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

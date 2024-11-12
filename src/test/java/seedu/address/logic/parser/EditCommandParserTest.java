package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.AGE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DETAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_AGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GENDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMOVE_TAG_DESC_1A;
import static seedu.address.logic.commands.CommandTestUtil.STUDY_GROUP_TAG_DESC_1A;
import static seedu.address.logic.commands.CommandTestUtil.STUDY_GROUP_TAG_DESC_2B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDY_GROUP_TAG_1A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDY_GROUP_TAG_2B;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDY_GROUP_TAG;
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
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.tag.StudyGroupTag;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String STUDY_GROUP_TAG_EMPTY = " " + PREFIX_STUDY_GROUP_TAG;

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditCommand.MESSAGE_USAGE);

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
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email

        // invalid gender followed by valid age

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code
        // Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + STUDY_GROUP_TAG_DESC_2B + STUDY_GROUP_TAG_DESC_1A + STUDY_GROUP_TAG_EMPTY,
                StudyGroupTag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + STUDY_GROUP_TAG_DESC_2B + STUDY_GROUP_TAG_EMPTY + STUDY_GROUP_TAG_DESC_1A,
                StudyGroupTag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + STUDY_GROUP_TAG_EMPTY + STUDY_GROUP_TAG_DESC_2B + STUDY_GROUP_TAG_DESC_1A,
                StudyGroupTag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_GENDER_AMY
                        + VALID_AGE_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + GENDER_DESC_BOB + STUDY_GROUP_TAG_DESC_1A
                + EMAIL_DESC_AMY + AGE_DESC_AMY + NAME_DESC_AMY + DETAIL_DESC_BOB + STUDY_GROUP_TAG_DESC_2B
                + REMOVE_TAG_DESC_1A;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withEmail(VALID_EMAIL_AMY).withGender(VALID_GENDER_BOB).withAge(VALID_AGE_AMY)
                .withDetail(VALID_DETAIL_BOB).withStudyGroupTags(VALID_STUDY_GROUP_TAG_1A, VALID_STUDY_GROUP_TAG_2B)
                .withTagsToRemove(VALID_STUDY_GROUP_TAG_1A)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + GENDER_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY)
                .withGender("M").build();
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

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // study groups
        userInput = targetIndex.getOneBased() + STUDY_GROUP_TAG_DESC_2B;
        descriptor = new EditPersonDescriptorBuilder().withStudyGroupTags(VALID_STUDY_GROUP_TAG_2B).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_EMAIL_DESC + EMAIL_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + GENDER_DESC_AMY + AGE_DESC_AMY + EMAIL_DESC_AMY
                + STUDY_GROUP_TAG_DESC_2B + GENDER_DESC_AMY + AGE_DESC_AMY + EMAIL_DESC_AMY + STUDY_GROUP_TAG_DESC_2B
                + GENDER_DESC_BOB + AGE_DESC_BOB + EMAIL_DESC_BOB + STUDY_GROUP_TAG_DESC_1A;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL, PREFIX_GENDER, PREFIX_AGE));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_GENDER_DESC + INVALID_AGE_DESC + INVALID_EMAIL_DESC
                + INVALID_GENDER_DESC + INVALID_AGE_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL, PREFIX_GENDER, PREFIX_AGE));
    }

    @Test
    public void parse_resetStudyGroupTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + STUDY_GROUP_TAG_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withStudyGroupTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

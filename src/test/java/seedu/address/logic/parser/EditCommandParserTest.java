package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GROUP_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MAJOR_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENTID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MAJOR_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MAJOR_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENTID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENTID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAJOR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_YEAR_AMY;
import static seedu.address.logic.commands.CommandTestUtil.YEAR_DESC_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NETID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.group.Group;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.StudentId;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String GROUP_EMPTY = " " + PREFIX_GROUP;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        String errorMessage = String.format("%s \n%s",
                MESSAGE_INVALID_INDEX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, errorMessage);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", errorMessage);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        String errorMessage = String.format("%s \n%s",
                MESSAGE_INVALID_INDEX,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, errorMessage);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, errorMessage);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", errorMessage);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", errorMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_STUDENTID_DESC, StudentId.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_GROUP_DESC, Group.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_STUDENTID_DESC + EMAIL_DESC_AMY, StudentId.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_GROUP} alone will reset the groups of the {@code Person} being edited,
        // parsing it together with a valid group results in error
        assertParseFailure(parser, "1" + GROUP_DESC_TWO + GROUP_DESC_ONE + GROUP_EMPTY,
                Group.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + GROUP_DESC_TWO + GROUP_EMPTY + GROUP_DESC_ONE,
                Group.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + GROUP_EMPTY + GROUP_DESC_TWO + GROUP_DESC_ONE,
                Group.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_MAJOR_AMY
                        + VALID_STUDENTID_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + STUDENTID_DESC_BOB + YEAR_DESC_AMY
                + EMAIL_DESC_AMY + MAJOR_DESC_AMY + NAME_DESC_AMY + GROUP_DESC_TWO;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withStudentId(VALID_STUDENTID_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_MAJOR_AMY)
                .withYear(VALID_YEAR_AMY).withGroups(VALID_GROUP_TWO).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + STUDENTID_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withStudentId(VALID_STUDENTID_BOB)
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

        // studentId
        userInput = targetIndex.getOneBased() + STUDENTID_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withStudentId(VALID_STUDENTID_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + MAJOR_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_MAJOR_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // groups
        userInput = targetIndex.getOneBased() + GROUP_DESC_TWO;
        descriptor = new EditPersonDescriptorBuilder().withGroups(VALID_GROUP_TWO).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_STUDENTID_DESC + STUDENTID_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENTID));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + STUDENTID_DESC_BOB + INVALID_STUDENTID_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENTID));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + STUDENTID_DESC_AMY + MAJOR_DESC_AMY + EMAIL_DESC_AMY
                + GROUP_DESC_TWO + STUDENTID_DESC_AMY + MAJOR_DESC_AMY + EMAIL_DESC_AMY + GROUP_DESC_TWO
                + STUDENTID_DESC_BOB + MAJOR_DESC_BOB + EMAIL_DESC_BOB + GROUP_DESC_ONE;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENTID, PREFIX_NETID,
                        PREFIX_MAJOR));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_STUDENTID_DESC + INVALID_MAJOR_DESC + INVALID_EMAIL_DESC
                + INVALID_STUDENTID_DESC + INVALID_MAJOR_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENTID, PREFIX_NETID, PREFIX_MAJOR));
    }

    @Test
    public void parse_resetGroups_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + GROUP_EMPTY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withGroups().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

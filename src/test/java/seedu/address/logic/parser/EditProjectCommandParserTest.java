package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROJECT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_ID_DESC_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_NAME_DESC_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_ALPHA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PROJECT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditProjectCommand;
import seedu.address.model.project.ProjectName;
import seedu.address.testutil.EditProjectDescriptorBuilder;

public class EditProjectCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditProjectCommand.MESSAGE_USAGE);

    private EditProjectCommandParser parser = new EditProjectCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_PROJECT_NAME_ALPHA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditProjectCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + PROJECT_NAME_DESC_ALPHA, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + PROJECT_NAME_DESC_ALPHA, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_PROJECT_NAME_DESC, ProjectName.MESSAGE_CONSTRAINTS); // invalid name
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PROJECT;
        String userInput = targetIndex.getOneBased() + PROJECT_NAME_DESC_ALPHA;

        EditProjectCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder()
                .withName(VALID_PROJECT_NAME_ALPHA).build();
        EditProjectCommand expectedCommand = new EditProjectCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PROJECT;
        String userInput = targetIndex.getOneBased() + PROJECT_NAME_DESC_ALPHA;

        EditProjectCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder()
                .withName(VALID_PROJECT_NAME_ALPHA).build();
        EditProjectCommand expectedCommand = new EditProjectCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PROJECT;
        String userInput = targetIndex.getOneBased() + PROJECT_NAME_DESC_ALPHA;
        EditProjectCommand.EditProjectDescriptor descriptor = new EditProjectDescriptorBuilder()
                .withName(VALID_PROJECT_NAME_ALPHA).build();
        EditProjectCommand expectedCommand = new EditProjectCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_editEmployeeId_failure() {
        // employee id
        Index targetIndex = INDEX_THIRD_PROJECT;
        String userInput = targetIndex.getOneBased() + PROJECT_ID_DESC_ALPHA;
        assertParseFailure(parser, userInput, EditProjectCommand.MESSAGE_EDIT_PROJECT_ID);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PROJECT;

        // mulltiple valid fields repeated
        String userInput = targetIndex.getOneBased() + PROJECT_NAME_DESC_ALPHA + PROJECT_NAME_DESC_ALPHA;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PROJECT_NAME));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PROJECT_NAME_DESC + INVALID_PROJECT_NAME_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PROJECT_NAME));
    }

}

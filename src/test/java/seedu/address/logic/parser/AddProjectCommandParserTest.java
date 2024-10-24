package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROJECT_ID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PROJECT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_ID_DESC_BETA;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_NAME_DESC_ALPHA;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_NAME_DESC_BETA;
import static seedu.address.logic.commands.CommandTestUtil.PROJECT_SKILLS_DESC_BETA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_ID_BETA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_NAME_BETA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalProjects.BETA;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddProjectCommand;
import seedu.address.model.project.Project;
import seedu.address.model.project.ProjectId;
import seedu.address.model.project.ProjectName;
import seedu.address.testutil.ProjectBuilder;


public class AddProjectCommandParserTest {
    private AddProjectCommandParser parser = new AddProjectCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Project expectedProject = new ProjectBuilder(BETA).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PROJECT_ID_DESC_BETA
                        + PROJECT_NAME_DESC_BETA + PROJECT_SKILLS_DESC_BETA,
                new AddProjectCommand(expectedProject));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedProjectString = PROJECT_ID_DESC_BETA + PROJECT_NAME_DESC_BETA;

        // multiple names
        assertParseFailure(parser, PROJECT_NAME_DESC_BETA + validExpectedProjectString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PROJECT_NAME));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedProjectString + PROJECT_NAME_DESC_ALPHA + validExpectedProjectString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PROJECT_ID, PREFIX_PROJECT_NAME));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_PROJECT_NAME_DESC + validExpectedProjectString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PROJECT_NAME));

        // invalid name
        assertParseFailure(parser, INVALID_PROJECT_ID_DESC + validExpectedProjectString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PROJECT_ID));


        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedProjectString + INVALID_PROJECT_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PROJECT_NAME));

        // invalid name
        assertParseFailure(parser, validExpectedProjectString + INVALID_PROJECT_ID_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PROJECT_ID));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProjectCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, PROJECT_ID_DESC_BETA + VALID_PROJECT_NAME_BETA,
                expectedMessage);

        // missing id prefix
        assertParseFailure(parser, VALID_PROJECT_ID_BETA + PROJECT_NAME_DESC_BETA,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_PROJECT_ID_BETA + VALID_PROJECT_NAME_BETA,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, PROJECT_ID_DESC_BETA + INVALID_PROJECT_NAME_DESC,
                ProjectName.MESSAGE_CONSTRAINTS);

        // invalid id
        assertParseFailure(parser, INVALID_PROJECT_ID_DESC + PROJECT_NAME_DESC_BETA,
                ProjectId.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_PROJECT_ID_DESC + INVALID_PROJECT_NAME_DESC,
                ProjectId.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PROJECT_ID_DESC_BETA + PROJECT_NAME_DESC_BETA,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddProjectCommand.MESSAGE_USAGE));
    }
}

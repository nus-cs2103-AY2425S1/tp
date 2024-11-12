package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHILD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalNames.INVALID_CHILD_NAME_DESC;
import static seedu.address.testutil.TypicalNames.INVALID_PARENT_NAME_DESC;
import static seedu.address.testutil.TypicalNames.VALID_CHILD_NAME_DESC_JANE;
import static seedu.address.testutil.TypicalNames.VALID_CHILD_NAME_DESC_JOHN;
import static seedu.address.testutil.TypicalNames.VALID_NAME_JANE;
import static seedu.address.testutil.TypicalNames.VALID_NAME_JOHN;
import static seedu.address.testutil.TypicalNames.VALID_PARENT_NAME_DESC_JANE;
import static seedu.address.testutil.TypicalNames.VALID_PARENT_NAME_DESC_JOHN;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.LinkCommand;
import seedu.address.model.person.Name;

public class LinkCommandParserTest {

    private LinkCommandParser parser = new LinkCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, VALID_CHILD_NAME_DESC_JOHN + VALID_PARENT_NAME_DESC_JANE,
                new LinkCommand(VALID_NAME_JOHN, VALID_NAME_JANE));
    }

    @Test
    public void parse_nameFieldsMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE);

        // Missing child and parent name fields altogether
        assertParseFailure(parser, "", expectedMessage);

        // Missing parent name field
        assertParseFailure(parser, VALID_CHILD_NAME_DESC_JOHN, expectedMessage);

        // Missing child name field
        assertParseFailure(parser, VALID_PARENT_NAME_DESC_JANE, expectedMessage);
    }

    @Test
    public void parse_invalidStudentNameField_failure() {
        assertParseFailure(parser, INVALID_CHILD_NAME_DESC + VALID_PARENT_NAME_DESC_JOHN, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " " + PREFIX_CHILD + VALID_PARENT_NAME_DESC_JOHN, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidParentNameField_failure() {
        assertParseFailure(parser, INVALID_PARENT_NAME_DESC + VALID_CHILD_NAME_DESC_JANE, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " " + PREFIX_PARENT + VALID_CHILD_NAME_DESC_JANE, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidCommandFormat_failure() {
        assertParseFailure(parser, PREAMBLE_NON_EMPTY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_CHILD_NAME_DESC_JOHN,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateNameFields_failure() {
        assertParseFailure(parser, VALID_CHILD_NAME_DESC_JOHN + VALID_CHILD_NAME_DESC_JANE
                + VALID_PARENT_NAME_DESC_JOHN + VALID_PARENT_NAME_DESC_JANE,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CHILD, PREFIX_PARENT));
    }

    @Test
    public void parse_duplicateStudentNameField_failure() {
        assertParseFailure(parser, VALID_CHILD_NAME_DESC_JOHN + VALID_CHILD_NAME_DESC_JANE
                        + VALID_PARENT_NAME_DESC_JANE,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CHILD));
    }

    @Test
    public void parse_duplicateParentNameField_failure() {
        assertParseFailure(parser, VALID_PARENT_NAME_DESC_JOHN + VALID_PARENT_NAME_DESC_JANE
                        + VALID_CHILD_NAME_DESC_JANE,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PARENT));
    }
}

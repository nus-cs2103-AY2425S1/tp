package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHILD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalNames.INVALID_CHILD_NAME_DESC;
import static seedu.address.testutil.TypicalNames.VALID_CHILD_NAME_DESC_JANE;
import static seedu.address.testutil.TypicalNames.VALID_CHILD_NAME_DESC_JOHN;
import static seedu.address.testutil.TypicalNames.VALID_NAME_JOHN;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.UnlinkCommand;
import seedu.address.model.person.Name;

public class UnlinkCommandParserTest {

    private UnlinkCommandParser parser = new UnlinkCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, VALID_CHILD_NAME_DESC_JOHN, new UnlinkCommand(VALID_NAME_JOHN));
    }

    @Test
    public void parse_studentNameFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlinkCommand.MESSAGE_USAGE);

        // Missing child name field altogether
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidStudentNameField_failure() {
        assertParseFailure(parser, INVALID_CHILD_NAME_DESC, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " " + PREFIX_CHILD, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidCommandFormat_failure() {
        assertParseFailure(parser, PREAMBLE_NON_EMPTY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlinkCommand.MESSAGE_USAGE));
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_CHILD_NAME_DESC_JOHN,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlinkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateStudentNameField_failure() {
        assertParseFailure(parser, VALID_CHILD_NAME_DESC_JOHN + VALID_CHILD_NAME_DESC_JANE,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CHILD));
    }
}

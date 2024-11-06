package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalStudents.AMY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewCommand;

public class ViewCommandParserTest {
    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_nameSpecified_success() {
        ViewCommand expectedCommand = new ViewCommand(AMY.getName());
        assertParseSuccess(parser, NAME_DESC_AMY, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, "", expectedMessage);

        // no name specified
        assertParseFailure(parser, PHONE_DESC_AMY, expectedMessage);

        // non-empty preamble
        assertParseFailure(parser, " asdasdadcad" + NAME_DESC_AMY, expectedMessage);

        // duplicate prefixes
        assertParseFailure(parser, NAME_DESC_AMY + " " + NAME_DESC_BOB,
                "Multiple values specified for the following single-valued field(s): n/");
    }
}

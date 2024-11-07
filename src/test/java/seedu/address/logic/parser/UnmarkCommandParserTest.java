package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.UnmarkCommand;
import seedu.address.model.person.Name;

public class UnmarkCommandParserTest {

    private UnmarkCommandParser parser = new UnmarkCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Name expectedName = new Name("John Doe");
        int expectedWeek = 1;

        assertParseSuccess(parser, " n/John Doe w/1", new UnmarkCommand(expectedName, expectedWeek));
    }

    @Test
    public void parse_missingName_failure() {
        assertParseFailure(parser, " w/1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnmarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingWeek_failure() {
        assertParseFailure(parser, " n/John Doe", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UnmarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidName_failure() {
        assertParseFailure(parser, " n/!@#$ w/1", Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidWeek_failure() {
        assertParseFailure(parser, " n/John Doe w/notanumber", Messages.MESSAGE_INVALID_WEEK);
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListAppointmentsCommand;

public class ListAppointmentsCommandParserTest {

    private ListAppointmentsCommandParser parser = new ListAppointmentsCommandParser();

    @Test
    public void parse_validArgs_returnsListAppointmentsCommand() {
        ListAppointmentsCommand expectedCommand = new ListAppointmentsCommand();
        assertParseSuccess(parser, "", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "some arguments",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentsCommand.MESSAGE_USAGE));
    }
}

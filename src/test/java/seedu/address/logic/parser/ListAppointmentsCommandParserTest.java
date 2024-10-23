package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListAppointmentsCommand;

public class ListAppointmentsCommandParserTest {

    private ListAppointmentsCommandParser parser = new ListAppointmentsCommandParser();

    @Test
    public void parse_emptyArg_returnsListAppointmentsCommand() {
        ListAppointmentsCommand expectedCommand = new ListAppointmentsCommand(Optional.empty(),
                Optional.empty());
        assertParseSuccess(parser, "", expectedCommand);
    }

    @Test
    public void parse_validDateArg_returnsListAppointmentsCommand() {
        ListAppointmentsCommand expectedCommand = new ListAppointmentsCommand(
                Optional.of(LocalDate.of(2024, 10, 15)), Optional.empty());
        assertParseSuccess(parser, " d/2024-10-15", expectedCommand);
    }

    @Test
    public void parse_validDateTimeArg_returnsListAppointmentsCommand() {
        ListAppointmentsCommand expectedCommand = new ListAppointmentsCommand(
                Optional.of(LocalDate.of(2024, 10, 15)),
                Optional.of(LocalTime.of(14, 30)));
        assertParseSuccess(parser, " d/2024-10-15 1430", expectedCommand);
    }

    @Test
    public void parse_invalidDateArg_throwsParseException() {
        assertParseFailure(parser, " d/invalid-date",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTimeArg_throwsParseException() {
        assertParseFailure(parser, " d/2024-10-15 invalid-time",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAppointmentsCommand.MESSAGE_USAGE));
    }
}

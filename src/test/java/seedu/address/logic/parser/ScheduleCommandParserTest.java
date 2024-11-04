package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.eventcommands.ScheduleCommand;
import seedu.address.model.types.common.DateTime;
import seedu.address.model.types.common.EventInSchedulePredicate;

public class ScheduleCommandParserTest {
    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_blankString_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_int_failure() {
        // contains decimals
        assertParseFailure(parser, "1.5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-0.5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, ".5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-.5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1.0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-2.0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));

        // contains other symbols
        assertParseFailure(parser, "1 5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1-5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1,5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1*5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1^5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1+5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1/5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "#15",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1~5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "!5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1'5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "\"1\"",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "\"1\"5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "five",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_int_success() {
        assertParseSuccess(parser, "1",
                new ScheduleCommand(new EventInSchedulePredicate(1)));
        assertParseSuccess(parser, "-1",
                new ScheduleCommand(new EventInSchedulePredicate(-1)));
        assertParseSuccess(parser, "0",
                new ScheduleCommand(new EventInSchedulePredicate(0)));
        assertParseSuccess(parser, "00",
                new ScheduleCommand(new EventInSchedulePredicate(0)));
        assertParseSuccess(parser, "-0",
                new ScheduleCommand(new EventInSchedulePredicate(0)));
        assertParseSuccess(parser, "365",
                new ScheduleCommand(new EventInSchedulePredicate(365)));
        assertParseSuccess(parser, "-365",
                new ScheduleCommand(new EventInSchedulePredicate(-365)));
    }

    @Test
    public void parse_date_failure() {
        assertParseFailure(parser, "2024-10-15 14:",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "2024-10-15 :30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "2024-10- 14:30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "2024--15 14:30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-10-15 14:30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "2024 10 15 14:30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "2024-10-15 14 30",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_date_success() {
        assertParseSuccess(parser, "2024-10-15",
                new ScheduleCommand(new EventInSchedulePredicate(new DateTime("2024-10-15 00:00"))));
        assertParseSuccess(parser, "2024-02-29",
                new ScheduleCommand(new EventInSchedulePredicate(new DateTime("2024-02-29 00:00"))));
        assertParseSuccess(parser, "0001-10-15",
                new ScheduleCommand(new EventInSchedulePredicate(new DateTime("0001-10-15 00:00"))));
        assertParseSuccess(parser, "9999-10-15",
                new ScheduleCommand(new EventInSchedulePredicate(new DateTime("9999-10-15 00:00"))));
        assertParseSuccess(parser, "2000-01-01",
                new ScheduleCommand(new EventInSchedulePredicate(new DateTime("2000-01-01 00:00"))));
        assertParseSuccess(parser, "1970-01-01",
                new ScheduleCommand(new EventInSchedulePredicate(new DateTime("1970-01-01 00:00"))));
        assertParseSuccess(parser, "0001-12-25",
                new ScheduleCommand(new EventInSchedulePredicate(new DateTime("0001-12-25 00:00"))));
        assertParseSuccess(parser, "2024-10-15",
                new ScheduleCommand(new EventInSchedulePredicate(new DateTime("2024-10-15 00:00"))));
        assertParseSuccess(parser, "2024-10-15",
                new ScheduleCommand(new EventInSchedulePredicate(new DateTime("2024-10-15 00:00"))));
    }
}

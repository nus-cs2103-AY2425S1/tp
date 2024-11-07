package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SeeScheduleCommand;
import seedu.address.model.schedule.SameWeekAsDatePredicate;

public class SeeScheduleCommandParserTest {

    private final SeeScheduleCommandParser parser = new SeeScheduleCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, " d/05-11-2024", new SeeScheduleCommand(
                new SameWeekAsDatePredicate(LocalDate.parse("2024-11-05"))));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, "", String
                .format(MESSAGE_INVALID_COMMAND_FORMAT, SeeScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ", String
                .format(MESSAGE_INVALID_COMMAND_FORMAT, SeeScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " d/01-31-2024", String
                .format(MESSAGE_INVALID_COMMAND_FORMAT, SeeScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " d/1-1-2024", String
                .format(MESSAGE_INVALID_COMMAND_FORMAT, SeeScheduleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " 05-11-2024", String
                .format(MESSAGE_INVALID_COMMAND_FORMAT, SeeScheduleCommand.MESSAGE_USAGE));
    }
}

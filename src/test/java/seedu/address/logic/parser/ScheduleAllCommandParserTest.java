package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ScheduleAllCommand;

public class ScheduleAllCommandParserTest {

    private ScheduleAllCommandParser parser = new ScheduleAllCommandParser();

    @Test
    public void parse_validArgs_returnsScheduleAllCommand() {
        // Since schedule_all takes no arguments, it should succeed with empty input
        assertParseSuccess(parser, "", new ScheduleAllCommand());
    }

    @Test
    public void parse_invalidArgs_returnsScheduleAllCommand() {
        // Even with invalid input, schedule_all should succeed since it ignores arguments
        assertParseSuccess(parser, "invalid", new ScheduleAllCommand());
    }
}

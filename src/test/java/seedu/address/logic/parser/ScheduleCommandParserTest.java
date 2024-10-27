package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.model.person.Date;
import seedu.address.model.person.SchedulePredicate;

import java.time.LocalDateTime;

public class ScheduleCommandParserTest {
    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        ScheduleCommand expectedScheduleCommand =
              new ScheduleCommand(new SchedulePredicate(
                      new Date(LocalDateTime.of(2024, 2, 16, 18, 45))));
        assertParseSuccess(parser, " d/ 16/2/2024 1845", expectedScheduleCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n d/ 16/2/2024 1845 \n \t", expectedScheduleCommand);

        //empty preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " \n d/ 16/2/2024 1845 \n \t",
              expectedScheduleCommand);
    }

    @Test
    public void parse_invalidArgs_returnsFindCommand() {
        //invalid preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + " d/ 16 February 2024",
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        // multiple dates inputted
        assertParseFailure(parser, " d/ 16 February 2024 d/ 17 February 2024",
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));

        // invalid prefix syntax for date
        assertParseFailure(parser, " t/High Risk",
              String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }
}

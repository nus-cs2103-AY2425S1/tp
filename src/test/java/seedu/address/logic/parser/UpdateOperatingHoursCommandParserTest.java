package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CLOSINGHOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPENINGHOURS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_TIME_FORMAT;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateOperatingHoursCommand;
import seedu.address.model.OperatingHours;

public class UpdateOperatingHoursCommandParserTest {

    private UpdateOperatingHoursCommandParser parser = new UpdateOperatingHoursCommandParser();
    private OperatingHours defaultHours = new OperatingHours(null, null);
    private OperatingHours openingHours = new OperatingHours(LocalTime.of(8, 30), null);
    private OperatingHours closingHours = new OperatingHours(null, LocalTime.of(21, 30));
    private OperatingHours bothHours = new OperatingHours(LocalTime.of(8, 30), LocalTime.of(21, 30));

    @Test
    public void parse_emptyArg_returnsUpdateOperatingHoursCommand() {
        UpdateOperatingHoursCommand expectedCommand = new UpdateOperatingHoursCommand(defaultHours);

        assertParseSuccess(parser, "   ", expectedCommand);
    }

    @Test
    public void parse_onlyOpeningHoursGiven_returnsUpdateOperatingHoursCommand() {
        UpdateOperatingHoursCommand expectedCommand = new UpdateOperatingHoursCommand(openingHours);

        assertParseSuccess(parser, " " + PREFIX_OPENINGHOURS.getPrefix() + "08:30", expectedCommand);
    }

    @Test
    public void parse_onlyClosingHoursGiven_returnsUpdateOperatingHoursCommand() {
        UpdateOperatingHoursCommand expectedCommand = new UpdateOperatingHoursCommand(closingHours);

        assertParseSuccess(parser, " " + PREFIX_CLOSINGHOURS.getPrefix() + "21:30", expectedCommand);
    }

    @Test
    public void parse_bothHoursGiven_returnsUpdateOperatingHoursCommand() {
        UpdateOperatingHoursCommand expectedCommand = new UpdateOperatingHoursCommand(bothHours);

        assertParseSuccess(parser, " " + PREFIX_OPENINGHOURS.getPrefix() + "08:30"
                + " " + PREFIX_CLOSINGHOURS.getPrefix() + "21:30", expectedCommand);
    }

    @Test
    public void parse_wrongFormatGiven_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_OPENINGHOURS.getPrefix() + "8:30",
                String.format(MESSAGE_INVALID_TIME_FORMAT));
    }
}

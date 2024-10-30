package keycontacts.logic.parser;

import static keycontacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static keycontacts.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static keycontacts.logic.commands.CommandTestUtil.VALID_DATE;
import static keycontacts.logic.commands.CommandTestUtil.VALID_DATE_DESC;
import static keycontacts.logic.parser.CommandParserTestUtil.assertParseFailure;
import static keycontacts.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import keycontacts.logic.commands.ViewCommand;
import keycontacts.model.lesson.Date;

public class ViewCommandParserTest {
    private final String invalidFormatMessage =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);

    private final ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_noMissingParts_success() {
        ViewCommand expectedCommand = new ViewCommand(new Date(VALID_DATE));

        assertParseSuccess(parser, VALID_DATE_DESC, expectedCommand);
    }

    @Test
    public void parse_dateNotPresent_success() {
        assertParseSuccess(parser, "", new ViewCommand(new Date(LocalDate.now())));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, "0", invalidFormatMessage);
        assertParseFailure(parser, "1 r", invalidFormatMessage);
        assertParseFailure(parser, "1 i/s", invalidFormatMessage);
    }

    @Test
    public void parse_invalidDate_failure() {
        assertParseFailure(parser, INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS);
    }
}

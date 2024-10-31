package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DATE_FORMAT;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.BookingsCommand;

public class BookingsCommandParserTest {

    private BookingsCommandParser parser = new BookingsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, BookingsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validDateString_returnsBookingsCommand() {
        // no leading and trailing whitespaces
        BookingsCommand expectedBookingsCommand =
                new BookingsCommand(LocalDate.of(2024, 05, 03));
        assertParseSuccess(parser, "03/05/2024", expectedBookingsCommand);
        assertParseSuccess(parser, "03 05 2024", expectedBookingsCommand);
        assertParseSuccess(parser, "03-05-2024", expectedBookingsCommand);

        // whitespaces before and after date
        assertParseSuccess(parser, "   03-05-2024     ", expectedBookingsCommand);
    }

    @Test
    public void parse_nricArgs_throwsParseException() {
        assertParseFailure(parser, "03 05 20245", MESSAGE_INVALID_DATE_FORMAT);

        // do not accept date-time
        assertParseFailure(parser, "03 05 2024 03:00",
                MESSAGE_INVALID_DATE_FORMAT);
        assertParseFailure(parser, "03 053 2024",
                MESSAGE_INVALID_DATE_FORMAT);
        assertParseFailure(parser, "65 05 2024",
                MESSAGE_INVALID_DATE_FORMAT);
        assertParseFailure(parser, "01-99-2024",
                MESSAGE_INVALID_DATE_FORMAT);

    }


}

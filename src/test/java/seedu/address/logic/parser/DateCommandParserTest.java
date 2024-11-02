package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DateCommand;
import seedu.address.model.person.Date;


public class DateCommandParserTest {
    private DateCommandParser parser = new DateCommandParser();
    private final String nonEmptyDate = "d/ 18/2/2024 1800";

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, DateCommand.COMMAND_WORD, expectedMessage);
        // no Name, no Phone, no Email
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DateCommand.MESSAGE_USAGE);
        assertParseFailure(parser, DateCommand.COMMAND_WORD + " " + nonEmptyDate, expectedMessage);

        //invalid Phone
        expectedMessage = Messages.MESSSAGE_INVALID_PHONE_DETAILS;
        assertParseFailure(parser, DateCommand.COMMAND_WORD + " p/111102457 " + nonEmptyDate, expectedMessage);

        //invalid Email
        expectedMessage = Messages.MESSAGE_INVALID_EMAIL_DETAILS;
        assertParseFailure(parser, DateCommand.COMMAND_WORD + " e/invalid email " + nonEmptyDate, expectedMessage);


    }
    @Test
    public void parse_validArgs_returnsDateCommand() {
        //no trailing or leading spaces
        Date date = new Date(LocalDateTime.of(2024, 2, 18, 18, 0));
        DateCommand dateCommand = new DateCommand(Optional.of("Nayana"), Optional.of("88502457"),
              Optional.empty(), date);
        assertParseSuccess(parser, " n/ Nayana p/88502457 d/ 18/2/2024 1800", dateCommand);

        //trailing and leading spaces
        assertParseSuccess(parser, " \n n/ Nayana p/88502457 \n d/ 18/2/2024 1800 \t", dateCommand);
    }
}

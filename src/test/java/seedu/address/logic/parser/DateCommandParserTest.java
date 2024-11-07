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

        // No parameters (no identifying prefixes and no date prefix)
        assertParseFailure(parser, DateCommand.COMMAND_WORD,
                "At least one identifier (name, phone, or email) must be provided. " + expectedMessage);

        // Only date provided, but no Name, Phone, or Email
        assertParseFailure(parser, DateCommand.COMMAND_WORD + " " + nonEmptyDate,
                "At least one identifier (name, phone, or email) must be provided. " + expectedMessage);

        // Only Name provided, but no date prefix
        assertParseFailure(parser, DateCommand.COMMAND_WORD + " n/Nayana",
                "A date and time is required. Please include a date and time. " + expectedMessage);

        // Only Phone provided, but no date prefix
        assertParseFailure(parser, DateCommand.COMMAND_WORD + " p/12345678",
                "A date and time is required. Please include a date and time. " + expectedMessage);

        // Only Email provided, but no date prefix
        assertParseFailure(parser, DateCommand.COMMAND_WORD + " e/email@example.com",
                "A date and time is required. Please include a date and time. " + expectedMessage);
    }

    @Test
    public void parse_invalidFieldValues_failure() {
        // Invalid Phone number format
        String expectedMessage = Messages.MESSSAGE_INVALID_PHONE_DETAILS;
        assertParseFailure(parser, DateCommand.COMMAND_WORD + " p/111102457 " + nonEmptyDate, expectedMessage);

        // Invalid Email format
        expectedMessage = Messages.MESSAGE_INVALID_EMAIL_DETAILS;
        assertParseFailure(parser, DateCommand.COMMAND_WORD
                + " e/invalid email " + nonEmptyDate, expectedMessage);
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

    @Test
    public void parse_invalidPrefix_failure() {
        assertParseFailure(parser, " n/ Nayana p/88502457 d/ 18/2/2024 1800 t/High Risk",
              String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DateCommand.MESSAGE_USAGE));
    }

}

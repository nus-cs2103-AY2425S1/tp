package seedu.address.logic.parser.consultation;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.consultation.AddConsultCommand;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.consultation.Date;
import seedu.address.model.consultation.Time;

public class AddConsultCommandParserTest {
    private AddConsultCommandParser parser = new AddConsultCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AddConsultCommand expectedCommand = new AddConsultCommand(
                new Consultation(new Date("2024-10-20"), new Time("14:00"), List.of()));

        // no whitespace (besides the first initial blank)
        assertParseSuccess(parser, " d/2024-10-20 t/14:00", expectedCommand);

        // random whitespace
        assertParseSuccess(parser, "  \n \t d/2024-10-20 \t\n  t/14:00  \n  ", expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddConsultCommand.MESSAGE_USAGE);

        // missing date prefix
        assertParseFailure(parser, " t/14:00", expectedMessage);

        // missing time prefix
        assertParseFailure(parser, " d/2024-10-20", expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, " ", expectedMessage);
    }

    @Test
    public void parse_duplicateFields_failure() {
        // duplicate date prefix
        assertParseFailure(parser, " d/2024-10-20 t/14:00 d/2024-10-21",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // duplicate time prefix
        assertParseFailure(parser, " d/2024-10-20 t/14:00 t/13:00",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TIME));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser, " d/20-10-2024 t/14:00", Date.MESSAGE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, " d/2024-10-20 t/25:00", Time.MESSAGE_CONSTRAINTS);
    }
}

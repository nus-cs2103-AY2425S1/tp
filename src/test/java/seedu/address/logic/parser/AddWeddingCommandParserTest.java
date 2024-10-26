package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WEDDING_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_NAME_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.WEDDING_NAME_DESC_ONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEDDING_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddWeddingCommand;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;
import seedu.address.testutil.WeddingBuilder;

public class AddWeddingCommandParserTest {
    private AddWeddingCommandParser parser = new AddWeddingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Wedding expectedWedding = new WeddingBuilder(WEDDING_ONE).build();

        // whitespace only preamble
        assertParseSuccess(parser, " " + PREFIX_WEDDING_NAME + VALID_WEDDING_NAME_ONE + " "
                + PREFIX_VENUE + VALID_VENUE_ONE + " "
                + PREFIX_DATE + VALID_DATE_ONE, new AddWeddingCommand(expectedWedding));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid wedding name
        assertParseFailure(parser, INVALID_WEDDING_NAME_DESC + VENUE_DESC_ONE + DATE_DESC_ONE,
                WeddingName.MESSAGE_CONSTRAINTS);

        // invalid venue
        assertParseFailure(parser, WEDDING_NAME_DESC_ONE + INVALID_VENUE_DESC + DATE_DESC_ONE,
                Venue.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, WEDDING_NAME_DESC_ONE + VENUE_DESC_ONE + INVALID_DATE_DESC,
                Date.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_WEDDING_NAME_DESC + INVALID_VENUE_DESC + DATE_DESC_ONE,
                WeddingName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddWeddingCommand.MESSAGE_USAGE);

        // missing wedding name prefix
        assertParseFailure(parser, VALID_WEDDING_NAME_ONE + " " + PREFIX_VENUE + VALID_VENUE_ONE + " "
                        + PREFIX_DATE + VALID_DATE_ONE,
                expectedMessage);

        // missing venue prefix
        assertParseFailure(parser, PREFIX_WEDDING_NAME + VALID_WEDDING_NAME_ONE + " "
                        + VALID_VENUE_ONE + " "
                        + PREFIX_DATE + VALID_DATE_ONE,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, PREFIX_WEDDING_NAME + VALID_WEDDING_NAME_ONE + " "
                        + PREFIX_VENUE + VALID_VENUE_ONE + " "
                        + VALID_DATE_ONE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_WEDDING_NAME_ONE + " "
                        + VALID_VENUE_ONE + " "
                        + VALID_DATE_ONE,
                expectedMessage);
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        // Assuming you have repeated values in your commands, you could create cases here
    }
}

package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RatingCommand;
import seedu.address.model.restaurant.Rating;

public class RatingCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RatingCommand.MESSAGE_USAGE);

    private RatingCommandParser parser = new RatingCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, "r/5", MESSAGE_INVALID_FORMAT);

        // no rating specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no rating specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 r/5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 r/5", MESSAGE_INVALID_FORMAT);

        // invalid arguments as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid rating (e.g., out of expected bounds or format)
        assertParseFailure(parser, "1 r/invalid_rating", Rating.MESSAGE_CONSTRAINTS);
    }
}

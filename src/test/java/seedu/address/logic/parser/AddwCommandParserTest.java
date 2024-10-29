package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddwCommand;
import seedu.address.model.person.NameMatchesKeywordPredicate;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.WeddingBuilder;

import java.util.Arrays;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CLIENT_INDEX_DESC_ALICEWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.CLIENT_DESC_AMYWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.CLIENT_NAME_DESC_ALICEWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_ALICEWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_AMYWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_ALICEWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMYWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_INDEX_ALICEWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_ALICEWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ALICEWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_ALICEWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_ALICEWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_AMYWEDDING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class AddwCommandParserTest {

    private AddwCommandParser parser = new AddwCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Wedding expectedWedding = new WeddingBuilder()
                .withName(VALID_NAME_ALICEWEDDING)
                .withDate(VALID_DATE_ALICEWEDDING)
                .withVenue(VALID_VENUE_ALICEWEDDING)
                .build();

        // All possible fields present - client by index
        assertParseSuccess(parser, NAME_DESC_ALICEWEDDING + CLIENT_INDEX_DESC_ALICEWEDDING + DATE_DESC_ALICEWEDDING + VENUE_DESC_ALICEWEDDING,
                new AddwCommand(
                        Index.fromOneBased(Integer.valueOf(VALID_CLIENT_INDEX_ALICEWEDDING)), null, expectedWedding));

        // All possible fields present - client by name
        assertParseSuccess(parser, NAME_DESC_ALICEWEDDING + CLIENT_NAME_DESC_ALICEWEDDING + DATE_DESC_ALICEWEDDING + VENUE_DESC_ALICEWEDDING,
                new AddwCommand(null, new NameMatchesKeywordPredicate(Arrays.asList(CLIENT_NAME_DESC_ALICEWEDDING.split("\\s+"))), expectedWedding));

        // Single name only
        assertParseFailure(parser, NAME_DESC_ALICEWEDDING + NAME_DESC_AMYWEDDING + CLIENT_INDEX_DESC_ALICEWEDDING + DATE_DESC_ALICEWEDDING + VENUE_DESC_ALICEWEDDING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // Single client only
        assertParseFailure(parser, NAME_DESC_ALICEWEDDING + CLIENT_INDEX_DESC_ALICEWEDDING + CLIENT_DESC_AMYWEDDING + DATE_DESC_ALICEWEDDING + VENUE_DESC_ALICEWEDDING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CLIENT));

        // Single date only - additional date should not be allowed
        assertParseFailure(parser, NAME_DESC_ALICEWEDDING + CLIENT_INDEX_DESC_ALICEWEDDING + DATE_DESC_ALICEWEDDING + DATE_DESC_AMYWEDDING + VENUE_DESC_ALICEWEDDING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // Single venue only - additional venue should not be allowed
        assertParseFailure(parser, NAME_DESC_ALICEWEDDING + CLIENT_INDEX_DESC_ALICEWEDDING + DATE_DESC_ALICEWEDDING + VENUE_DESC_ALICEWEDDING + VENUE_DESC_AMYWEDDING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_VENUE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Wedding expectedWedding = new WeddingBuilder()
                .withName(VALID_NAME_ALICEWEDDING)
                .withDate(null)
                .withVenue(null)
                .build();

        assertParseSuccess(parser, NAME_DESC_ALICEWEDDING + CLIENT_INDEX_DESC_ALICEWEDDING,
                new AddwCommand(Index.fromOneBased(Integer.valueOf(VALID_CLIENT_INDEX_ALICEWEDDING)), null, expectedWedding));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddwCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_ALICEWEDDING + CLIENT_INDEX_DESC_ALICEWEDDING,
                expectedMessage);

        // missing client prefix
        assertParseFailure(parser, NAME_DESC_ALICEWEDDING + VALID_CLIENT_INDEX_ALICEWEDDING,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_ALICEWEDDING + VALID_CLIENT_INDEX_ALICEWEDDING,
                expectedMessage);
    }
}

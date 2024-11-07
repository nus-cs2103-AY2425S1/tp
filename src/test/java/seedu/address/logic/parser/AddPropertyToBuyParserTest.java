package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.BUYING_PRICE_DESC_1650000;
import static seedu.address.logic.commands.CommandTestUtil.HOUSING_TYPE_DESC_HDB;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_BUYING_PRICE_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSTAL_CODE_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNIT_NUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.POSTAL_CODE_DESC_567510;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NEAR_MRT;
import static seedu.address.logic.commands.CommandTestUtil.UNIT_NUMBER_DESC_03_11;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddPropertyToBuyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Hdb;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.Price;
import seedu.address.model.person.Property;
import seedu.address.model.person.UnitNumber;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.PropertyToBuyBuilder;

public class AddPropertyToBuyParserTest {
    private AddPropertyToBuyParser parser = new AddPropertyToBuyParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        Property expectedProperty = new Hdb(new PostalCode("567510"), new UnitNumber("03-11"),
                new Price("1650000"), SampleDataUtil.getTagSet("near MRT"));

        assertParseSuccess(parser, PREAMBLE_INDEX + HOUSING_TYPE_DESC_HDB + BUYING_PRICE_DESC_1650000
                        + POSTAL_CODE_DESC_567510 + UNIT_NUMBER_DESC_03_11 + TAG_DESC_NEAR_MRT,
                new AddPropertyToBuyCommand(Index.fromOneBased(1), expectedProperty));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Property expectedProperty = new PropertyToBuyBuilder().withPostalCode("567510").withUnitNumber("03-11")
                .withPrice("1650000").build();

        assertParseSuccess(parser, PREAMBLE_INDEX + HOUSING_TYPE_DESC_HDB + BUYING_PRICE_DESC_1650000
                        + POSTAL_CODE_DESC_567510 + UNIT_NUMBER_DESC_03_11,
                new AddPropertyToBuyCommand(Index.fromOneBased(1), expectedProperty));
    }

    @Test
    public void parse_compulsoryFieldsMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyToBuyCommand.MESSAGE_USAGE);

        // missing postal code prefix
        assertParseFailure(parser, PREAMBLE_INDEX + HOUSING_TYPE_DESC_HDB + BUYING_PRICE_DESC_1650000
                + UNIT_NUMBER_DESC_03_11 + TAG_DESC_NEAR_MRT, expectedMessage);

        // missing unit number prefix
        assertParseFailure(parser, PREAMBLE_INDEX + HOUSING_TYPE_DESC_HDB + BUYING_PRICE_DESC_1650000
                + POSTAL_CODE_DESC_567510 + TAG_DESC_NEAR_MRT, expectedMessage);

        // missing buying price prefix
        assertParseFailure(parser, PREAMBLE_INDEX + HOUSING_TYPE_DESC_HDB + POSTAL_CODE_DESC_567510
                + UNIT_NUMBER_DESC_03_11 + TAG_DESC_NEAR_MRT, expectedMessage);

        // missing housing type prefix
        assertParseFailure(parser, PREAMBLE_INDEX + BUYING_PRICE_DESC_1650000 + POSTAL_CODE_DESC_567510
                + UNIT_NUMBER_DESC_03_11 + TAG_DESC_NEAR_MRT, expectedMessage);
    }

    @Test
    public void parse_repeatedNonTagPrefix_failure() {
        String expectedMessageDuplicatePc = Messages.getErrorMessageForDuplicatePrefixes(new Prefix("pc/"));
        String expectedMessageDuplicateHt = Messages.getErrorMessageForDuplicatePrefixes(new Prefix("ht/"));
        String expectedMessageDuplicateBp = Messages.getErrorMessageForDuplicatePrefixes(new Prefix("bp/"));
        String expectedMessageDuplicateUn = Messages.getErrorMessageForDuplicatePrefixes(new Prefix("un/"));

        // repeated postal code prefix
        assertParseFailure(parser, PREAMBLE_INDEX + HOUSING_TYPE_DESC_HDB + BUYING_PRICE_DESC_1650000
                        + POSTAL_CODE_DESC_567510 + POSTAL_CODE_DESC_567510 + UNIT_NUMBER_DESC_03_11
                        + TAG_DESC_NEAR_MRT,
                expectedMessageDuplicatePc);

        // repeated unit number prefix
        assertParseFailure(parser, PREAMBLE_INDEX + HOUSING_TYPE_DESC_HDB + BUYING_PRICE_DESC_1650000
                        + POSTAL_CODE_DESC_567510 + UNIT_NUMBER_DESC_03_11 + UNIT_NUMBER_DESC_03_11
                        + TAG_DESC_NEAR_MRT,
                expectedMessageDuplicateUn);

        // repeated buying price prefix
        assertParseFailure(parser, PREAMBLE_INDEX + HOUSING_TYPE_DESC_HDB + BUYING_PRICE_DESC_1650000
                        + BUYING_PRICE_DESC_1650000 + POSTAL_CODE_DESC_567510 + UNIT_NUMBER_DESC_03_11
                        + TAG_DESC_NEAR_MRT,
                expectedMessageDuplicateBp);

        // repeated housing type prefix
        assertParseFailure(parser, PREAMBLE_INDEX + HOUSING_TYPE_DESC_HDB + HOUSING_TYPE_DESC_HDB
                        + BUYING_PRICE_DESC_1650000 + POSTAL_CODE_DESC_567510 + UNIT_NUMBER_DESC_03_11
                        + TAG_DESC_NEAR_MRT,
                expectedMessageDuplicateHt);
    }

    /*@Test
    public void parse_invalidValue_failure() {
        // invalid postal code
        assertParseFailure(parser, PREAMBLE_INDEX + HOUSING_TYPE_DESC_HDB + BUYING_PRICE_DESC_1650000
                        + INVALID_POSTAL_CODE_DESC + UNIT_NUMBER_DESC_03_11 + TAG_DESC_NEAR_MRT,
                PostalCode.MESSAGE_CONSTRAINTS);

        // invalid unit number
        assertParseFailure(parser, PREAMBLE_INDEX + HOUSING_TYPE_DESC_HDB + BUYING_PRICE_DESC_1650000
                        + POSTAL_CODE_DESC_567510 + INVALID_UNIT_NUMBER_DESC + TAG_DESC_NEAR_MRT,
                UnitNumber.MESSAGE_CONSTRAINTS);

        // invalid buying price
        assertParseFailure(parser, PREAMBLE_INDEX + HOUSING_TYPE_DESC_HDB + INVALID_BUYING_PRICE_DESC
                        + POSTAL_CODE_DESC_567510 + UNIT_NUMBER_DESC_03_11 + TAG_DESC_NEAR_MRT,
                Price.MESSAGE_CONSTRAINTS);
    }*/

    @Test
    public void parse_emptyPreamble_throwsParseException() {
        assertParseFailure(parser, " ht/HDB bp/1650000 pc/567510 un/03-11 t/near MRT",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyToBuyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_blankPreamble_throwsParseException() {
        assertParseFailure(parser, "  ht/HDB bp/1650000 pc/567510 un/03-11 t/near MRT",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyToBuyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndexInPreamble_throwsParseException() {
        assertParseFailure(parser, "a ht/HDB bp/1650000 pc/567510 un/03-11 t/near MRT",
                String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, AddPropertyToBuyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_tagLengthExceedsLimit_throwsParseException() {
        assertParseFailure(parser, PREAMBLE_INDEX + HOUSING_TYPE_DESC_HDB + BUYING_PRICE_DESC_1650000
                        + POSTAL_CODE_DESC_567510 + UNIT_NUMBER_DESC_03_11 + " t/thisisaverylongtag",
                Property.MESSAGE_PROPERTY_TAG_LENGTH_LIMIT);
    }

    @Test
    public void parse_tagCountExceedsLimit_throwsParseException() {
        assertParseFailure(parser, PREAMBLE_INDEX + HOUSING_TYPE_DESC_HDB + BUYING_PRICE_DESC_1650000
                        + POSTAL_CODE_DESC_567510 + UNIT_NUMBER_DESC_03_11
                        + " t/tag1 t/tag2 t/tag3 t/tag4 t/tag5 t/tag6",
                Property.MESSAGE_PROPERTY_TAG_LIMIT);
    }

    @Test
    public void parse_validTags_success() throws ParseException {
        Property expectedProperty = new Hdb(new PostalCode("567510"), new UnitNumber("03-11"),
                new Price("1650000"), SampleDataUtil.getTagSet("tag1", "tag2"));

        assertParseSuccess(parser, PREAMBLE_INDEX + HOUSING_TYPE_DESC_HDB + BUYING_PRICE_DESC_1650000
                        + POSTAL_CODE_DESC_567510 + UNIT_NUMBER_DESC_03_11 + " t/tag1 t/tag2",
                new AddPropertyToBuyCommand(Index.fromOneBased(1), expectedProperty));
    }
}

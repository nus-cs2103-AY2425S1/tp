package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUSING_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class AddPropertyToSellParserTest {

    private AddPropertyToSellParser parser = new AddPropertyToSellParser();

    @BeforeEach
    public void setUp() {
        parser = new AddPropertyToSellParser();
    }

    @Test
    public void parse_missingSellingPrice_throwsParseException() {
        // Given a user input missing the selling price
        String userInput = "1 "
                + PREFIX_HOUSING_TYPE + " apartment "
                + PREFIX_POSTAL_CODE + " 123456 "
                + PREFIX_UNIT_NUMBER + " 10-01 ";

        // When parsing, then a ParseException should be thrown
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidSellingPrice_throwsParseException() {
        // Given a user input with an invalid selling price
        String userInput = "1 "
                + PREFIX_HOUSING_TYPE + " apartment "
                + PREFIX_SELLING_PRICE + " notANumber "
                + PREFIX_POSTAL_CODE + " 123456 "
                + PREFIX_UNIT_NUMBER + " 10-01 ";

        // When parsing, then a ParseException should be thrown
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
    @Test
    public void parse_missingHousingType_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_SELLING_PRICE + "1000000 "
                + PREFIX_POSTAL_CODE + "123456 "
                + PREFIX_UNIT_NUMBER + "10-01 "
                + PREFIX_TAG + "New";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidHousingType_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_HOUSING_TYPE + "InvalidType "
                + PREFIX_SELLING_PRICE + "1000000 "
                + PREFIX_POSTAL_CODE + "123456 "
                + PREFIX_UNIT_NUMBER + "10-01 "
                + PREFIX_TAG + "New";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
    @Test
    public void parse_invalidPostalCode_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_HOUSING_TYPE + "Condo "
                + PREFIX_SELLING_PRICE + "1000000 "
                + PREFIX_POSTAL_CODE + "invalidPostalCode "
                + PREFIX_UNIT_NUMBER + "10-01 "
                + PREFIX_TAG + "New";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingRequiredFields_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_SELLING_PRICE + "1000000 "
                + PREFIX_POSTAL_CODE + "123456 "
                + PREFIX_TAG + "New";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidUnitNumber_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_HOUSING_TYPE + "Condo "
                + PREFIX_SELLING_PRICE + "1000000 "
                + PREFIX_POSTAL_CODE + "123456 "
                + PREFIX_UNIT_NUMBER + "invalidUnitNumber "
                + PREFIX_TAG + "New";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
    @Test
    public void parse_invalidTag_throwsParseException() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_HOUSING_TYPE + "Condo "
                + PREFIX_SELLING_PRICE + "1000000 "
                + PREFIX_POSTAL_CODE + "123456 "
                + PREFIX_UNIT_NUMBER + "10-01 "
                + PREFIX_TAG + "InvalidTag";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_HOUSING_TYPE + "a "
                + PREFIX_SELLING_PRICE + "1000000 "
                + PREFIX_POSTAL_CODE + "123456 "
                + PREFIX_UNIT_NUMBER + "10-01 "
                + PREFIX_TAG + "New";

        // No exception should be thrown
        parser.parse(userInput);
    }
}

package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOUSING_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNIT_NUMBER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class AddPropertyToSellParserTest {

    private AddPropertyToSellParser parser = new AddPropertyToSellParser();
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
}

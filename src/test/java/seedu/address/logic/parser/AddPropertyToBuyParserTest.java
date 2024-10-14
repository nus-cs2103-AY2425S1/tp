package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class AddPropertyToBuyParserTest {
    private AddPropertyToBuyParser parser = new AddPropertyToBuyParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        assertParseFailure(parser, "addBuy", "Invalid command format! \n"
    }
}

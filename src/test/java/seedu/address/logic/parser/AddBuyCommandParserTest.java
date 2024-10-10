package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class AddBuyCommandParserTest {
    private AddBuyCommandParser parser = new AddBuyCommandParser();

    @Test
    public void parse_throwParseException() throws ParseException {
        assertParseFailure(parser, " ", "not implemented yet");
    }
}

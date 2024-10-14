package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class AddPropertyToBuyParserTest {
    private AddPropertyToBuyParser parser = new AddPropertyToBuyParser();

    @Test
    public void parse_throwParseException() throws ParseException {
        assertParseFailure(parser, " ", "Invalid command format! \n"
                + "not implemented yet: AddPropertyToBuyCommand.MESSAGE_USAGE");
    }
}

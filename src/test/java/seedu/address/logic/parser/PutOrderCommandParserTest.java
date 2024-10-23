package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PutOrderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.Order;
import seedu.address.model.person.Name;

class PutOrderCommandParserTest {

    @Test
    void parse_missingOrder_throwsParseException() {
        PutOrderCommandParser p = new PutOrderCommandParser();
        assertThrows(ParseException.class, () -> p.parse("n/ Alex"));
    }

    @Test
    void parse_missingName_throwsParseException() {
        PutOrderCommandParser p = new PutOrderCommandParser();
        assertThrows(ParseException.class, () -> p.parse("cake "));
    }

    @Test
    void parse_validInput_success() {
        PutOrderCommandParser p = new PutOrderCommandParser();
        Order order = new Order("cake");
        Name name = new Name("Alex");
        try {
            assertEquals(new PutOrderCommand(order, name), p.parse("cake n/ Alex"));
        } catch (ParseException e) {
            System.out.println(e);
        }
    }

}

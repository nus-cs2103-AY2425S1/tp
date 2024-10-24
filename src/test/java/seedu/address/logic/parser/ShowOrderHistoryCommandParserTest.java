package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowOrderHistoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

class ShowOrderHistoryCommandParserTest {

    @Test
    void parse_missingName_throwsParseException() {
        ShowOrderHistoryCommandParser p = new ShowOrderHistoryCommandParser();
        assertThrows(ParseException.class, () -> p.parse(""));
    }

    @Test
    void parse_validInput_success() {
        ShowOrderHistoryCommandParser p = new ShowOrderHistoryCommandParser();
        Name name = new Name("Alex");
        try {
            assertEquals(new ShowOrderHistoryCommand(name), p.parse("Alex"));
        } catch (ParseException e) {
            System.out.println(e);
        }
    }

}

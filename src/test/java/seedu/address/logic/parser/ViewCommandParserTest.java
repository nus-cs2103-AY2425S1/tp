package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.TestContactDisplay;

public class ViewCommandParserTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private TestContactDisplay contactDisplay = new TestContactDisplay();


    @Test
    public void parse_invalidInput_throwsParseException() {
        ViewCommandParser parser = new ViewCommandParser();
        String input = "invalid";

        assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        ViewCommandParser parser = new ViewCommandParser();
        String input = "";

        assertThrows(ParseException.class, () -> {
            parser.parse(input);
        });
    }

}

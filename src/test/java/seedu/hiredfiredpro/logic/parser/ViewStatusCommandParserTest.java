package seedu.hiredfiredpro.logic.parser;

import static seedu.hiredfiredpro.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.hiredfiredpro.logic.parser.exceptions.ParseException;

public class ViewStatusCommandParserTest {

    private final ViewStatusCommandParser parser = new ViewStatusCommandParser();

    @Test
    public void parse_missingName_throwsParseException() {
        // Test missing name
        String userInput = "view j/Software Engineer";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingJob_throwsParseException() {
        // Test missing job
        String userInput = "view n/John Doe";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}

package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Suggestions class.
 */
public class SuggestionsTest {

    private Suggestions suggestions;

    @BeforeEach
    public void setUp() {
        suggestions = new Suggestions();
    }

    @Test
    public void checkAllCommands_emptyInput_returnsEmptyString() {
        assertEquals("", suggestions.checkAllCommands(""));
    }

    @Test
    public void checkAllCommands_nonMatchingInput_returnsInputAsIs() {
        String input = "nonexistent";
        assertEquals(input, suggestions.checkAllCommands(input));
    }

    @Test
    public void checkAllCommands_partialAddCommand_returnsSuggestion() {
        String input = "add";
        String expected = "add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG...]";
        assertEquals(expected, suggestions.checkAllCommands(input));
    }

    @Test
    public void checkAllCommands_partialDeleteCommand_returnsSuggestion() {
        String input = "delete";
        String expected = "delete INDEX/NAME";
        assertEquals(expected, suggestions.checkAllCommands(input));
    }

    @Test
    public void checkAllCommands_fullCommand_returnsInputWithaSpace() {
        String input = "list";
        String expected = "list ";
        assertEquals(expected, suggestions.checkAllCommands(input));
    }

    @Test
    public void checkAllCommands_caseInsensitiveInput_noSuggestion() {
        String input = "AdD";
        String expected = "AdD";
        assertEquals(expected, suggestions.checkAllCommands(input));
    }
}

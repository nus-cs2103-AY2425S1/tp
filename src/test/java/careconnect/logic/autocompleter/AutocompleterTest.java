package careconnect.logic.autocompleter;

import static careconnect.logic.Messages.MESSAGE_NO_AUTOCOMPLETE_OPTIONS;
import static careconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import careconnect.logic.autocompleter.exceptions.AutocompleteException;

public class AutocompleterTest {

    @Test
    public void autocompleteWithLexicalPriority_noAvailableOptions_throwsAutocompleteException() {
        Autocompleter autocompleter = new Autocompleter();
        String prefix = "a";
        List<String> list = new ArrayList<>();
        assertThrows(AutocompleteException.class, String.format(MESSAGE_NO_AUTOCOMPLETE_OPTIONS, prefix), ()
                -> autocompleter.autocompleteWithLexicalPriority(prefix, list));
    }

    @Test
    public void autocompleteWithLexicalPriority_nullValues_throwsNullPointerException() {
        Autocompleter autocompleter = new Autocompleter();
        String prefix = "a";
        List<String> list = new ArrayList<>();
        assertThrows(NullPointerException.class, () -> new Autocompleter().autocompleteWithLexicalPriority(
                null, list));
        assertThrows(NullPointerException.class, () -> new Autocompleter().autocompleteWithLexicalPriority(
                prefix, null));
        assertThrows(NullPointerException.class, () -> new Autocompleter().autocompleteWithLexicalPriority(
                null, null));
    }

    @Test
    public void autocompleteWithLexicalPriority_validValues_correctResults() throws Exception {
        Autocompleter autocompleter = new Autocompleter();
        List<String> list = new ArrayList<>();
        list.add("add");
        list.add("adda");
        assertEquals("add", autocompleter.autocompleteWithLexicalPriority("ad", list));
        list.add("abs");
        assertEquals("abs", autocompleter.autocompleteWithLexicalPriority("a", list));
    }
}

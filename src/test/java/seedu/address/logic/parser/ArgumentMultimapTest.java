package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class ArgumentMultimapTest {

    private ArgumentMultimap argumentMultimap;
    private final Prefix prefix1 = PREFIX_NAME;
    private final Prefix prefix2 = PREFIX_PHONE;

    @BeforeEach
    public void setUp() {
        argumentMultimap = new ArgumentMultimap();
    }

    @Test
    public void put_singleValue_success() {
        argumentMultimap.put(prefix1, "John");
        assertArgumentPresent(argumentMultimap, prefix1, "John");
    }

    @Test
    public void put_multipleValues_success() {
        argumentMultimap.put(prefix1, "John");
        argumentMultimap.put(prefix1, "Mary");
        assertArgumentPresent(argumentMultimap, prefix1, "John", "Mary");
    }

    @Test
    public void getValue_existingPrefix_success() {
        argumentMultimap.put(prefix1, "John");
        Optional<String> value = argumentMultimap.getValue(prefix1);
        assertTrue(value.isPresent());
        assertEquals("John", value.get());
    }

    @Test
    public void getValue_nonExistingPrefix_empty() {
        Optional<String> value = argumentMultimap.getValue(prefix2);
        assertFalse(value.isPresent());
    }

    @Test
    public void getAllValues_existingPrefix_success() {
        argumentMultimap.put(prefix1, "John");
        argumentMultimap.put(prefix1, "Mary");
        List<String> values = argumentMultimap.getAllValues(prefix1);
        assertEquals(2, values.size());
        assertEquals("John", values.get(0));
        assertEquals("Mary", values.get(1));
    }

    @Test
    public void getAllValues_nonExistingPrefix_empty() {
        List<String> values = argumentMultimap.getAllValues(prefix2);
        assertTrue(values.isEmpty());
    }

    @Test
    public void getPreamble_noPreamble_empty() {
        String preamble = argumentMultimap.getPreamble();
        assertEquals("", preamble);
    }

    @Test
    public void verifyNoDuplicatePrefixesFor_noDuplicates_success() throws ParseException {
        argumentMultimap.put(prefix1, "John");
        argumentMultimap.verifyNoDuplicatePrefixesFor(prefix1, prefix2);
    }

    @Test
    public void verifyNoDuplicatePrefixesFor_withDuplicates_throwsParseException() {
        argumentMultimap.put(prefix1, "John");
        argumentMultimap.put(prefix1, "Mary");
        assertThrows(ParseException.class, () -> argumentMultimap.verifyNoDuplicatePrefixesFor(prefix1));
    }

    @Test
    public void verifyNoInvalidPrefixesFor_noInvalidPrefixes_success() throws ParseException {
        argumentMultimap.put(new Prefix(""), "");
        argumentMultimap.put(prefix2, "12345678");
        argumentMultimap.put(new Prefix(""), "");
        argumentMultimap.verifyNoInvalidPrefixesFor("p/12345678");
    }

    @Test
    public void verifyNoInvalidPrefixesFor_withInvalidPrefixes_throwsParseException() {
        argumentMultimap.put(prefix1, "John");
        assertThrows(ParseException.class, () -> argumentMultimap.verifyNoInvalidPrefixesFor("n/John p/12345678"));
    }

    private void assertArgumentPresent(ArgumentMultimap argMultimap, Prefix prefix, String... expectedValues) {
        assertEquals(expectedValues[expectedValues.length - 1], argMultimap.getValue(prefix).get());
        assertEquals(expectedValues.length, argMultimap.getAllValues(prefix).size());
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], argMultimap.getAllValues(prefix).get(i));
        }
    }
}

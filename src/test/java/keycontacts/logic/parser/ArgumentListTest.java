package keycontacts.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import keycontacts.logic.parser.exceptions.ParseException;

public class ArgumentListTest {

    private ArgumentList argumentList;
    private Prefix prefix1;
    private Prefix prefix2;
    private ArgumentToken argumentToken1;
    private ArgumentToken argumentToken2;

    @BeforeEach
    public void setUp() {
        argumentList = new ArgumentList();
        prefix1 = new Prefix("n/");
        prefix2 = new Prefix("p/");
        argumentToken1 = new ArgumentToken(prefix1, "value1");
        argumentToken2 = new ArgumentToken(prefix2, "value2");
    }

    @Test
    public void addArgument_argumentAddedSuccessfully() {
        argumentList.addArgument(argumentToken1);
        assertEquals(1, argumentList.size());
        assertEquals(prefix1, argumentList.getPrefix(0));
        assertEquals("value1", argumentList.getValue(0));
    }

    @Test
    public void anyPrefixesPresent_prefixPresent_returnsTrue() {
        argumentList.addArgument(argumentToken1);
        assertTrue(argumentList.anyPrefixesPresent(prefix1));
    }

    @Test
    public void anyPrefixesPresent_prefixNotPresent_returnsFalse() {
        argumentList.addArgument(argumentToken1);
        assertFalse(argumentList.anyPrefixesPresent(prefix2));
    }

    @Test
    public void verifyNoDuplicatePrefixesFor_noDuplicates_doesNotThrow() {
        argumentList.addArgument(argumentToken1);
        argumentList.addArgument(argumentToken2);
        assertDoesNotThrow(() -> argumentList.verifyNoDuplicatePrefixesFor(prefix1, prefix2));
    }

    @Test
    public void getValues_noArguments_returnsEmptyList() {
        assertTrue(argumentList.getValues().isEmpty());
    }

    @Test
    public void getValues_singleArgument_returnsSingleValue() {
        argumentList.addArgument(argumentToken1);
        List<String> values = argumentList.getValues();
        assertEquals(1, values.size());
        assertEquals("value1", values.get(0));
    }

    @Test
    public void getValues_multipleArguments_returnsAllValues() {
        argumentList.addArgument(argumentToken1);
        argumentList.addArgument(argumentToken2);
        List<String> values = argumentList.getValues();
        assertEquals(2, values.size());
        assertEquals("value1", values.get(0));
        assertEquals("value2", values.get(1));
    }

    @Test
    public void getValues_duplicatePrefixes_returnsAllValues() {
        argumentList.addArgument(argumentToken1);
        argumentList.addArgument(new ArgumentToken(prefix1, "value3"));
        List<String> values = argumentList.getValues();
        assertEquals(2, values.size());
        assertEquals("value1", values.get(0));
        assertEquals("value3", values.get(1));
    }

    @Test
    public void isEmpty_noArguments_returnsTrue() {
        assertTrue(argumentList.isEmpty());
    }

    @Test
    public void isEmpty_withArguments_returnsFalse() {
        argumentList.addArgument(argumentToken1);
        assertFalse(argumentList.isEmpty());
    }

    @Test
    public void addArgument_multipleArgumentsAddedSuccessfully() {
        argumentList.addArgument(argumentToken1);
        argumentList.addArgument(argumentToken2);
        assertEquals(2, argumentList.size());
        assertEquals(prefix1, argumentList.getPrefix(0));
        assertEquals("value1", argumentList.getValue(0));
        assertEquals(prefix2, argumentList.getPrefix(1));
        assertEquals("value2", argumentList.getValue(1));
    }

    @Test
    public void getPrefix_invalidIndex_throwsIndexOutOfBoundsException() {
        argumentList.addArgument(argumentToken1);
        assertThrows(IndexOutOfBoundsException.class, () -> argumentList.getPrefix(1));
    }

    @Test
    public void getValue_invalidIndex_throwsIndexOutOfBoundsException() {
        argumentList.addArgument(argumentToken1);
        assertThrows(IndexOutOfBoundsException.class, () -> argumentList.getValue(1));
    }

    @Test
    public void verifyNoDuplicatePrefixesFor_duplicates_throwsParseException() {
        argumentList.addArgument(argumentToken1);
        argumentList.addArgument(new ArgumentToken(prefix1, "value3"));
        ParseException exception = assertThrows(ParseException.class, () -> {
            argumentList.verifyNoDuplicatePrefixesFor(prefix1);
        });
        assertTrue(exception.getMessage()
                .contains("Multiple values specified for the following single-valued field(s): "));
    }
}

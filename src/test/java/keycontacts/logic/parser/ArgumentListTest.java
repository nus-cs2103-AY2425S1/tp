package keycontacts.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

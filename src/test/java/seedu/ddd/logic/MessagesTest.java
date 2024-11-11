package seedu.ddd.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.ddd.logic.parser.CliFlags.FLAG_CLIENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_VENDOR;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.parser.Prefix;

public class MessagesTest {

    @Test
    public void getErrorMessageForDuplicatePrefixes() {
        Prefix[] emptyPrefixes = new Prefix[0];
        assertThrows(AssertionError.class, () -> Messages.getErrorMessageForDuplicatePrefixes(emptyPrefixes));

        Prefix[] duplicatePrefixes = new Prefix[] { PREFIX_NAME, PREFIX_NAME, PREFIX_DATE };
        String expectedMessage = Messages.MESSAGE_DUPLICATE_FIELDS + "n/, d/";
        assertEquals(expectedMessage, Messages.getErrorMessageForDuplicatePrefixes(duplicatePrefixes));
    }

    @Test
    public void getErrorMessageForExclusiveFlags() {
        Prefix[] emptyFlags = new Prefix[0];
        assertThrows(AssertionError.class, () -> Messages.getErrorMessageForExclusiveFlags(emptyFlags));

        Prefix[] duplicateFlags = new Prefix[] { FLAG_CLIENT, FLAG_VENDOR };
        String expectedMessage = Messages.MESSAGE_EXCLUSIVE_FLAGS + "-c, -v";
        assertEquals(expectedMessage, Messages.getErrorMessageForExclusiveFlags(duplicateFlags));
    }

}

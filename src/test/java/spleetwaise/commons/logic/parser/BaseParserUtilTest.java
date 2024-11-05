package spleetwaise.commons.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import spleetwaise.address.testutil.TypicalIndexes;
import spleetwaise.commons.logic.parser.exceptions.ParseException;
import spleetwaise.commons.testutil.Assert;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class BaseParserUtilTest {
    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> BaseParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, BaseParserUtil.MESSAGE_INVALID_INDEX, ()
                -> BaseParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(TypicalIndexes.INDEX_FIRST_PERSON, BaseParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(TypicalIndexes.INDEX_FIRST_PERSON, BaseParserUtil.parseIndex("  1  "));
    }
}

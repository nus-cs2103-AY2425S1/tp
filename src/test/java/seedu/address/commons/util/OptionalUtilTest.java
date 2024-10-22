package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class OptionalUtilTest {

    @Test
    public void optionalToString_optionalEmpty_returnsEmptyString() {
        Optional<?> emptyOptional = Optional.empty();
        assertEquals("", OptionalUtil.optionalToString(emptyOptional));
    }

    @Test
    public void optionalToString_optionalWithValue_returnsValueString() {
        Optional<String> optionalWithValue = Optional.of("testValue");
        assertEquals("testValue", OptionalUtil.optionalToString(optionalWithValue));
    }

    @Test
    public void optionalToString_optionalWithNullValue_returnsEmptyString() {
        Optional<?> optionalWithNullValue = Optional.ofNullable(null);
        assertEquals("", OptionalUtil.optionalToString(optionalWithNullValue));
    }
}

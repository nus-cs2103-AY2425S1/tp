package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import seedu.address.commons.util.ToStringBuilder;

public class SortSettingsTest {
    @Test
    public void toStringMethod() {
        SortSettings sortSettings = new SortSettings();
        String expected = SortSettings.class.getCanonicalName() + "{sortParameter=" + sortSettings.getSortParameter()
                + ", isAscendingOrder=" + sortSettings.isAscendingOrder() + "}";
        assertEquals(expected, sortSettings.toString());
    }
}


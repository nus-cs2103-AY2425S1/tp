package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SortOrderTest {
    @Test
    public void checkValidStringRepresentations() {
        assertEquals(SortOrder.ASC.toString(), "ascending");
        assertEquals(SortOrder.DESC.toString(), "descending");
        assertEquals(SortOrder.OG.toString(), "original");
    }

    @Test
    public void checkValidKeywords() {
        assertEquals(SortOrder.ASC.keyword, "asc");
        assertEquals(SortOrder.DESC.keyword, "desc");
        assertEquals(SortOrder.OG.keyword, "og");
    }
}

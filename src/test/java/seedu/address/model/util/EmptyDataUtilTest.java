package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EmptyDataUtilTest {
    @Test
    public void getSampleCampusConnect_returnsEmptyCampusConnect() {
        assertTrue(EmptyDataUtil.getSampleCampusConnect().getPersonList().isEmpty());
    }
}

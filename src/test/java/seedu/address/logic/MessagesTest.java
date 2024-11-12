package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MessagesTest {

    @Test
    public void getMessageGroupsListedOverviewTest() {
        String expectedSingular = "1 group listed!";
        String expectedPlural = "3 groups listed!";
        assertEquals(expectedSingular, Messages.getMessageGroupsListedOverview(1));
        assertEquals(expectedPlural, Messages.getMessageGroupsListedOverview(3));
    }
}

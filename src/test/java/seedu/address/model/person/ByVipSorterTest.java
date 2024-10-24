package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import org.junit.jupiter.api.Test;


public class ByVipSorterTest {
    @Test
    public void equality() {
        ByVipSorter vipSorter = new ByVipSorter();
        assertEquals(0, vipSorter.compare(CARL, CARL)); // same person
        assertEquals(0, vipSorter.compare(DANIEL, DANIEL)); // same person
        assertEquals(0, vipSorter.compare(CARL, BENSON)); // both VIP
        assertEquals(0, vipSorter.compare(DANIEL, ALICE)); // both non-VIP
    }
}

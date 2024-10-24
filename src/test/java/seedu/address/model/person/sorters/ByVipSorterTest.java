package seedu.address.model.person.sorters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;


public class ByVipSorterTest {
    @Test
    public void equality() {
        ByVipSorter vipSorter = new ByVipSorter();
        assertEquals(0, vipSorter.compare(CARL, CARL)); // same VIP
        assertEquals(0, vipSorter.compare(DANIEL, DANIEL)); // same non-VIP
        assertEquals(0, vipSorter.compare(CARL, BENSON)); // both VIP
        assertEquals(0, vipSorter.compare(DANIEL, ALICE)); // both non-VIP
    }
    @Test
    public void inequality() {
        ByVipSorter vipSorter = new ByVipSorter();
        assertEquals(-1, vipSorter.compare(CARL, DANIEL)); // only first person is a VIP
        assertEquals(1, vipSorter.compare(ALICE, BENSON)); // only second person is a VIP
    }

    @Test
    public void testSorting() {
        ByVipSorter vipSorter = new ByVipSorter();
        ArrayList<Person> toSort = new ArrayList<Person>(TypicalPersons.getTypicalPersons());
        toSort.sort(vipSorter);
        assertTrue(toSort.get(1).isVip()); // last VIP
        assertFalse(toSort.get(2).isVip()); // first non-VIP
    }
}

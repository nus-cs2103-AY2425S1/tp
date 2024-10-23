package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameComparatorTest {

    @Test
    public void test_compare_returnsPositiveInteger() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        assertTrue((new NameComparator().compare(bob, alice)) > 0);
    }
    @Test
    public void test_compare_returnsNegativeInteger() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        assertTrue((new NameComparator().compare(alice, bob)) < 0);
    }

    @Test
    public void equals() {
        NameComparator comparator1 = new NameComparator();
        NameComparator comparator2 = new NameComparator();
        assertEquals(comparator1, comparator2);
    }

    @Test
    public void toStringMethod() {
        NameComparator comparator = new NameComparator();
        String expected = NameComparator.class.getCanonicalName();
        assertEquals(expected, comparator.toString());
    }
}

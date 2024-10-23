package seedu.address.model.person.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonComparatorByNameTest {

    private final ComparatorManager comparatorManager = new ComparatorManager();
    private final PersonComparatorByName comparatorAsc =
            (PersonComparatorByName) comparatorManager.getComparator(SortField.NAME, SortOrder.ASC);

    private final PersonComparatorByName comparatorDesc =
            (PersonComparatorByName) comparatorManager.getComparator(SortField.NAME, SortOrder.DESC);

    @Test
    public void getSortField_success() {
        assertEquals("NAME", comparatorAsc.getSortField());
    }

    @Test
    public void getSortOrder_success() {
        assertEquals("ASC", comparatorAsc.getSortOrder());
        assertEquals("DESC", comparatorDesc.getSortOrder());
    }

    @Test
    public void compare_nullInput_throwsNullPointer() {
        Person firstPerson = new PersonBuilder().withName(VALID_NAME_AMY).build();
        Person secondPerson = new PersonBuilder().withName(VALID_NAME_BOB).build();


        assertThrows(NullPointerException.class, () -> comparatorAsc.compare(firstPerson, null));
        assertThrows(NullPointerException.class, () -> comparatorAsc.compare(null, secondPerson));
    }

    @Test
    public void compare_sameInput_returnsZero() {
        Person firstPerson = new PersonBuilder().withName(VALID_NAME_AMY).build();
        Person secondPerson = new PersonBuilder().withName(VALID_NAME_AMY).build();

        assertEquals(0, comparatorAsc.compare(firstPerson, secondPerson));
    }

    @Test
    public void compare_validInput_success() {
        Person firstPerson = new PersonBuilder().withName(VALID_NAME_AMY).build();
        Person secondPerson = new PersonBuilder().withName(VALID_NAME_BOB).build();

        assertTrue(comparatorAsc.compare(firstPerson, secondPerson) < 0);
        assertTrue(comparatorAsc.compare(secondPerson, firstPerson) > 0);

        assertTrue(comparatorDesc.compare(firstPerson, secondPerson) > 0);
        assertTrue(comparatorDesc.compare(secondPerson, firstPerson) < 0);
    }

    @Test
    public void toStringMethod() {
        String expected = PersonComparatorByName.class.getCanonicalName() + "{sortOrder=ASC}";
        assertEquals(expected, comparatorAsc.toString());
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(comparatorAsc.equals(comparatorAsc));

        // same values -> returns true
        PersonComparatorByName firstComparatorCopy =
                (PersonComparatorByName) comparatorManager.getComparator(SortField.NAME, SortOrder.ASC);
        assertTrue(comparatorAsc.equals(firstComparatorCopy));

        // different types -> returns false
        assertFalse(comparatorAsc.equals(1));

        // null -> returns false
        assertFalse(comparatorAsc.equals(null));

        // different person -> returns false
        assertFalse(comparatorAsc.equals(comparatorDesc));
    }
}

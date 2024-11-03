package seedu.address.model.person.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonComparatorByTelegramTest {

    private final ComparatorManager comparatorManager = new ComparatorManager();
    private final PersonComparatorByTelegram comparatorAsc =
            (PersonComparatorByTelegram) comparatorManager.getComparator(SortField.TELEGRAM, SortOrder.ASC);

    private final PersonComparatorByTelegram comparatorDesc =
            (PersonComparatorByTelegram) comparatorManager.getComparator(SortField.TELEGRAM, SortOrder.DESC);

    @Test
    public void getSortField_success() {
        assertEquals("TELEGRAM", comparatorAsc.getSortField());
    }

    @Test
    public void getSortOrder_success() {
        assertEquals("ASC", comparatorAsc.getSortOrder());
        assertEquals("DESC", comparatorDesc.getSortOrder());
    }

    @Test
    public void compare_nullInput_throwsNullPointer() {
        Person firstPerson = new PersonBuilder().withTelegram(VALID_TELEGRAM_AMY).build();
        Person secondPerson = new PersonBuilder().withTelegram(VALID_TELEGRAM_BOB).build();


        assertThrows(NullPointerException.class, () -> comparatorAsc.compare(firstPerson, null));
        assertThrows(NullPointerException.class, () -> comparatorAsc.compare(null, secondPerson));
    }

    @Test
    public void compare_sameInput_returnsZero() {
        Person firstPerson = new PersonBuilder().withTelegram(VALID_TELEGRAM_AMY).build();
        Person secondPerson = new PersonBuilder().withTelegram(VALID_TELEGRAM_AMY).build();

        assertEquals(0, comparatorAsc.compare(firstPerson, secondPerson));
    }

    @Test
    public void compare_validInput_success() {
        Person firstPerson = new PersonBuilder().withTelegram(VALID_TELEGRAM_AMY).build();
        Person secondPerson = new PersonBuilder().withTelegram(VALID_TELEGRAM_BOB).build();

        assertTrue(comparatorAsc.compare(firstPerson, secondPerson) < 0);
        assertTrue(comparatorAsc.compare(secondPerson, firstPerson) > 0);

        assertTrue(comparatorDesc.compare(firstPerson, secondPerson) > 0);
        assertTrue(comparatorDesc.compare(secondPerson, firstPerson) < 0);
    }

    @Test
    public void toStringMethod() {
        String expected = PersonComparatorByTelegram.class.getCanonicalName() + "{sortOrder=ASC}";
        assertEquals(expected, comparatorAsc.toString());
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(comparatorAsc.equals(comparatorAsc));

        // same values -> returns true
        PersonComparatorByTelegram firstComparatorCopy =
                (PersonComparatorByTelegram) comparatorManager.getComparator(SortField.TELEGRAM, SortOrder.ASC);
        assertTrue(comparatorAsc.equals(firstComparatorCopy));

        // different types -> returns false
        assertFalse(comparatorAsc.equals(1));

        // null -> returns false
        assertFalse(comparatorAsc.equals(null));

        // different person -> returns false
        assertFalse(comparatorAsc.equals(comparatorDesc));
    }
}

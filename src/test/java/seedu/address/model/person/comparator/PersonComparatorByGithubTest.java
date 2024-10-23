package seedu.address.model.person.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonComparatorByGithubTest {

    private final ComparatorManager comparatorManager = new ComparatorManager();
    private final PersonComparatorByGithub comparatorAsc =
            (PersonComparatorByGithub) comparatorManager.getComparator(SortField.GITHUB, SortOrder.ASC);

    private final PersonComparatorByGithub comparatorDesc =
            (PersonComparatorByGithub) comparatorManager.getComparator(SortField.GITHUB, SortOrder.DESC);

    @Test
    public void getSortField_success() {
        assertEquals("GITHUB", comparatorAsc.getSortField());
    }

    @Test
    public void getSortOrder_success() {
        assertEquals("ASC", comparatorAsc.getSortOrder());
        assertEquals("DESC", comparatorDesc.getSortOrder());
    }

    @Test
    public void compare_nullInput_throwsNullPointer() {
        Person firstPerson = new PersonBuilder().withGithub(VALID_GITHUB_AMY).build();
        Person secondPerson = new PersonBuilder().withGithub(VALID_GITHUB_BOB).build();


        assertThrows(NullPointerException.class, () -> comparatorAsc.compare(firstPerson, null));
        assertThrows(NullPointerException.class, () -> comparatorAsc.compare(null, secondPerson));
    }

    @Test
    public void compare_sameInput_returnsZero() {
        Person firstPerson = new PersonBuilder().withGithub(VALID_GITHUB_AMY).build();
        Person secondPerson = new PersonBuilder().withGithub(VALID_GITHUB_AMY).build();

        assertEquals(0, comparatorAsc.compare(firstPerson, secondPerson));
    }

    @Test
    public void compare_validInput_success() {
        Person firstPerson = new PersonBuilder().withGithub(VALID_GITHUB_AMY).build();
        Person secondPerson = new PersonBuilder().withGithub(VALID_GITHUB_BOB).build();

        assertTrue(comparatorAsc.compare(firstPerson, secondPerson) < 0);
        assertTrue(comparatorAsc.compare(secondPerson, firstPerson) > 0);

        assertTrue(comparatorDesc.compare(firstPerson, secondPerson) > 0);
        assertTrue(comparatorDesc.compare(secondPerson, firstPerson) < 0);
    }

    @Test
    public void toStringMethod() {
        String expected = PersonComparatorByGithub.class.getCanonicalName() + "{sortOrder=ASC}";
        assertEquals(expected, comparatorAsc.toString());
    }

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(comparatorAsc.equals(comparatorAsc));

        // same values -> returns true
        PersonComparatorByGithub firstComparatorCopy =
                (PersonComparatorByGithub) comparatorManager.getComparator(SortField.GITHUB, SortOrder.ASC);
        assertTrue(comparatorAsc.equals(firstComparatorCopy));

        // different types -> returns false
        assertFalse(comparatorAsc.equals(1));

        // null -> returns false
        assertFalse(comparatorAsc.equals(null));

        // different person -> returns false
        assertFalse(comparatorAsc.equals(comparatorDesc));
    }
}

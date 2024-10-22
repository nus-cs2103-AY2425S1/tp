package seedu.address.model.person.comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ComparatorManagerTest {
    private ComparatorManager comparatorManager = new ComparatorManager();
    @Test
    public void getComparator_validInput_success() {
        // comparator by github
        assertEquals(
                new PersonComparatorByGithub(SortOrder.ASC),
                comparatorManager.getComparator(SortField.GITHUB, SortOrder.ASC));

        assertEquals(
                new PersonComparatorByGithub(SortOrder.DESC),
                comparatorManager.getComparator(SortField.GITHUB, SortOrder.DESC));

        // comparator by name
        assertEquals(
                new PersonComparatorByName(SortOrder.ASC),
                comparatorManager.getComparator(SortField.NAME, SortOrder.ASC));

        assertEquals(
                new PersonComparatorByName(SortOrder.DESC),
                comparatorManager.getComparator(SortField.NAME, SortOrder.DESC));

        // comparator by telegram
        assertEquals(
                new PersonComparatorByTelegram(SortOrder.ASC),
                comparatorManager.getComparator(SortField.TELEGRAM, SortOrder.ASC));

        assertEquals(
                new PersonComparatorByTelegram(SortOrder.DESC),
                comparatorManager.getComparator(SortField.TELEGRAM, SortOrder.DESC));
    }
}

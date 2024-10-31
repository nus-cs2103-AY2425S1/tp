package seedu.address.model.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class IncomeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Income((String)null));
    }

    @Test
    public void constructor_enum_success() {
        assertEquals(new Income(Income.IncomeGroup.NONE).toString(), "none");
    }

    @Test
    public void constructor_invalidIncome_throwsIllegalArgumentException() {
        String invalidIncome = "";
        assertThrows(IllegalArgumentException.class, () -> new Income(invalidIncome));
    }

    @Test
    public void isValidIncome() {
        // null income
        assertThrows(NullPointerException.class, () -> Income.isValidIncome(null));

        // blank income
        assertFalse(Income.isValidIncome("")); // empty string
        assertFalse(Income.isValidIncome(" ")); // spaces only

        // invalid income
        assertFalse(Income.isValidIncome("abcd"));
        assertFalse(Income.isValidIncome("lOw"));

        // valid income
        assertTrue(Income.isValidIncome("none"));
        assertTrue(Income.isValidIncome("low"));
        assertTrue(Income.isValidIncome("mid"));
        assertTrue(Income.isValidIncome("high"));
    }

    @Test
    public void emptyIncome() {
        Income income = Income.createEmpty();

        assertTrue(income.isEmpty());

        assertEquals(income.toString(), "<REPRESENTATION FOR EMPTY INCOME>");

        assertEquals(income.getValueForUi(), "No income provided");
    }

    @Test
    public void equals() {
        Income income = new Income("low");

        // same values -> returns true
        assertTrue(income.equals(new Income("low")));

        // same object -> returns true
        assertTrue(income.equals(income));

        // null -> returns false
        assertFalse(income.equals(null));

        // different types -> returns false
        assertFalse(income.equals(5.0f));

        // different values -> returns false
        assertFalse(income.equals(new Income("high")));
    }

    @Test
    public void stringForUi() {
        assertEquals(new Income("none").getValueForUi(), "No income");
        assertEquals(new Income("low").getValueForUi(), "Low income");
        assertEquals(new Income("mid").getValueForUi(), "Mid income");
        assertEquals(new Income("high").getValueForUi(), "High income");
    }

    @Test
    public void hashcode() {
        assertEquals(new Income("none").hashCode(), new Income("none").value.hashCode());
    }
}

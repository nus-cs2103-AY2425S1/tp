package seedu.address.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class IncomePredicateTest {

    @Test
    public void equals() {
        List<String> firstIncomeList = Collections.singletonList("1000.00");
        List<String> secondIncomeList = Arrays.asList("1000.00", "2000");

        IncomePredicate firstPredicate = new IncomePredicate(firstIncomeList);
        IncomePredicate secondPredicate = new IncomePredicate(secondIncomeList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        IncomePredicate firstPredicateCopy = new IncomePredicate(firstIncomeList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals("one thousand"));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different list of incomes -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_incomeMatches_returnsTrue() {
        // One value higher than person's income
        IncomePredicate predicate = new IncomePredicate(Collections.singletonList("1500.00"));
        assertTrue(predicate.test(new PersonBuilder().withIncome(1000.00).build()));

        // Multiple values with some higher but some lower than person's income
        predicate = new IncomePredicate(Arrays.asList("500.00", "1000"));
        assertTrue(predicate.test(new PersonBuilder().withIncome(800).build()));

        // Boundary values
        predicate = new IncomePredicate(Arrays.asList("500.01", "500.00"));
        assertTrue(predicate.test(new PersonBuilder().withIncome(500.01).build()));
    }

    @Test
    public void test_incomeDoesNotMatch_returnsFalse() {
        // Value less than person's income
        IncomePredicate predicate = new IncomePredicate(Arrays.asList("1000.00"));
        assertFalse(predicate.test(new PersonBuilder().withIncome(1500).build()));

        // Multiple values all lower than person's income
        predicate = new IncomePredicate(Arrays.asList("1000", "1500", "300.00"));
        assertFalse(predicate.test(new PersonBuilder().withIncome(2000.00).build()));
    }

    @Test
    public void toStringMethod() {
        List<String> incomes = List.of("1000.00", "500");
        IncomePredicate predicate = new IncomePredicate(incomes);

        String expected = IncomePredicate.class.getCanonicalName() + "{incomes=" + incomes + "}";
        assertEquals(expected, predicate.toString());
    }
}

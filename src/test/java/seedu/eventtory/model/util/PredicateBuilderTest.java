package seedu.eventtory.model.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

public class PredicateBuilderTest {
    @Test
    public void combinePredicates() {
        Predicate<Integer> isEven = x -> x % 2 == 0;
        Predicate<Integer> isPositive = x -> x > 0;
        Predicate<Integer> isEvenAndPositive = PredicateBuilder.combinePredicates(isEven, isPositive);
        assertTrue(isEvenAndPositive.test(2));
        assertFalse(isEvenAndPositive.test(1));
        assertFalse(isEvenAndPositive.test(-2));
    }
}

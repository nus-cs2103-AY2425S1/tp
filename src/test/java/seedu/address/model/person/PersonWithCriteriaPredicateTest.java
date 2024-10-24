package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Range;
import seedu.address.testutil.PersonBuilder;

public class PersonWithCriteriaPredicateTest {

    @Test
    public void equals() {
        List<Range<?>> firstCriteria = Collections.singletonList(new Range<>(1, 2));
        List<Range<?>> secondCriteria = Arrays.asList(
                new Range<>(1, 2), new Range<>(3, 4)
        );

        PersonWithCriteriaPredicate firstPredicate = new PersonWithCriteriaPredicate(firstCriteria);
        PersonWithCriteriaPredicate secondPredicate = new PersonWithCriteriaPredicate(secondCriteria);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonWithCriteriaPredicate firstPredicateCopy = new PersonWithCriteriaPredicate(firstCriteria);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different criteria -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void isValidPredicate() {
        // null range
        assertThrows(NullPointerException.class, () -> PersonWithCriteriaPredicate.isValidRange(null));

        // null range list
        assertThrows(NullPointerException.class, () -> new PersonWithCriteriaPredicate(null));

        // invalid range type
        Range<String> range = new Range<>("Alice", "Bob");
        assertFalse(PersonWithCriteriaPredicate.isValidRange(range));

        // valid range type
        Range<Integer> ageRange = new Range<>(20, 25);
        assertTrue(PersonWithCriteriaPredicate.isValidRange(ageRange));

        Range<LocalDate> dateRange = new Range<>(LocalDate.now(), LocalDate.now().plusDays(1));
        assertTrue(PersonWithCriteriaPredicate.isValidRange(dateRange));

        Range<Number> numberRange = new Range<>(1, 2);
        assertTrue(PersonWithCriteriaPredicate.isValidRange(numberRange));

    }

    @Test
    public void test_personWithCriteria_returnsTrue() {
        // no criterion
        List<Range<?>> criteria = new ArrayList<>();
        PersonWithCriteriaPredicate predicate = new PersonWithCriteriaPredicate(criteria);
        assertTrue(predicate.test(new PersonBuilder().build()));

        // one criterion
        criteria.add(new Range<Integer>(20, 25));
        predicate = new PersonWithCriteriaPredicate(criteria);
        assertTrue(predicate.test(new PersonBuilder().withAge("21").build()));

        // multiple criteria
        criteria.add(new Range<LocalDate>(
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31)
        ));
        predicate = new PersonWithCriteriaPredicate(criteria);
        assertTrue(predicate.test(new PersonBuilder().withAge("21").addAppointment("01/01/2024 1200").build()));
    }

    @Test
    public void test_personNotWithCriteria_returnsFalse() {
        // Non-matching criterion
        List<Range<?>> criteria = new ArrayList<>();
        criteria.add(new Range<Integer>(20, 25));
        PersonWithCriteriaPredicate predicate = new PersonWithCriteriaPredicate(criteria);
        assertFalse(predicate.test(new PersonBuilder().withAge("19").build()));

        // only one matching criterion in multiple criteria
        criteria.add(new Range<LocalDate>(
                LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31)
        ));
        assertFalse(predicate.test(new PersonBuilder().withAge("19").addAppointment("01/01/2024 1200").build()));
    }

    @Test
    public void toStringMethod() {
        List<Range<?>> criteria = new ArrayList<>();
        criteria.add(new Range<Integer>(20, 21));
        PersonWithCriteriaPredicate predicate = new PersonWithCriteriaPredicate(criteria);
        String expected = PersonWithCriteriaPredicate.class.getCanonicalName() + "{criteria=" + criteria + "}";;
        assertEquals(expected, predicate.toString());
    }
}

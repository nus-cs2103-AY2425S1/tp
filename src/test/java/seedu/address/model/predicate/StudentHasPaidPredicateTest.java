package seedu.address.model.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class StudentHasPaidPredicateTest {

    private Person paidPerson;
    private Person unpaidPerson;
    private StudentHasPaidPredicate predicatePaid;
    private StudentHasPaidPredicate predicateUnpaid;

    @BeforeEach
    public void setUp() {
        paidPerson = new PersonBuilder().withPayment("0").build(); // No overdue amount
        unpaidPerson = new PersonBuilder().withPayment("100").build(); // Overdue amount
    }

    @Test
    public void test_paymentUpToDateTrue_withPaidPerson() {
        predicatePaid = new StudentHasPaidPredicate(true);
        assertTrue(predicatePaid.test(paidPerson)); // Should return true for a paid person
    }

    @Test
    public void test_paymentUpToDateTrue_withUnpaidPerson() {
        predicatePaid = new StudentHasPaidPredicate(true);
        assertFalse(predicatePaid.test(unpaidPerson)); // Should return false for an unpaid person
    }

    @Test
    public void test_paymentUpToDateFalse_withPaidPerson() {
        predicateUnpaid = new StudentHasPaidPredicate(false);
        assertFalse(predicateUnpaid.test(paidPerson)); // Should return false for a paid person
    }

    @Test
    public void test_paymentUpToDateFalse_withUnpaidPerson() {
        predicateUnpaid = new StudentHasPaidPredicate(false);
        assertTrue(predicateUnpaid.test(unpaidPerson)); // Should return true for an unpaid person
    }

    @Test
    public void test_equalsSameObject_returnsTrue() {
        predicatePaid = new StudentHasPaidPredicate(true);
        assertEquals(predicatePaid, predicatePaid); // Same object should be equal
    }

    @Test
    public void test_equalsDifferentTypes_returnsFalse() {
        predicatePaid = new StudentHasPaidPredicate(true);
        assertNotEquals("some string", predicatePaid); // Different types should not be equal
    }

    @Test
    public void test_equalsDifferentPaymentStatus_returnsFalse() {
        StudentHasPaidPredicate predicate1 = new StudentHasPaidPredicate(true);
        StudentHasPaidPredicate predicate2 = new StudentHasPaidPredicate(false);
        assertNotEquals(predicate1, predicate2); // Different payment statuses should not be equal
    }

    @Test
    public void test_equalsSamePaymentStatus_returnsTrue() {
        StudentHasPaidPredicate predicate1 = new StudentHasPaidPredicate(true);
        StudentHasPaidPredicate predicate2 = new StudentHasPaidPredicate(true);
        assertEquals(predicate1, predicate2); // Same payment status should be equal
    }

    @Test
    public void test_toString_correctFormat() {
        boolean hasPaid = true;
        predicatePaid = new StudentHasPaidPredicate(hasPaid);
        String expected = StudentHasPaidPredicate.class.getCanonicalName() + "{payment up to date=" + hasPaid + "}";
        assertEquals(expected, predicatePaid.toString()); // Verify that toString returns the expected format
    }

    @Test
    public void test_paymentStatusWithZeroOverdue() {
        Person zeroOverduePerson = new PersonBuilder().withPayment("0").build();
        predicatePaid = new StudentHasPaidPredicate(true);
        assertTrue(predicatePaid.test(zeroOverduePerson)); // Should return true for zero overdue amount
    }

    @Test
    public void test_paymentStatusWithNegativeOverdue() {
        Person negativeOverduePerson = new PersonBuilder().withPayment("-50").build(); // Negative overdue amount
        predicatePaid = new StudentHasPaidPredicate(true);
        assertTrue(predicatePaid.test(negativeOverduePerson)); // Should return true for negative overdue amount
    }

    @Test
    public void test_paymentStatusWithLargeOverdue() {
        Person largeOverduePerson = new PersonBuilder().withPayment("1000").build(); // Large overdue amount
        predicateUnpaid = new StudentHasPaidPredicate(false);
        assertTrue(predicateUnpaid.test(largeOverduePerson)); // Should return true for large overdue amount
    }

    @Test
    public void test_paymentStatusWithRandomValues() {
        Person randomOverduePerson = new PersonBuilder().withPayment("250").build(); // Random overdue amount
        predicatePaid = new StudentHasPaidPredicate(false);
        assertTrue(predicatePaid.test(randomOverduePerson)); // Should return true for a random unpaid person
    }
}


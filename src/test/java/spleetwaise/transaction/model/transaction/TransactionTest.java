package spleetwaise.transaction.model.transaction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static spleetwaise.transaction.model.transaction.Status.DONE_STATUS;
import static spleetwaise.transaction.model.transaction.Status.NOT_DONE_STATUS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import spleetwaise.address.model.person.Person;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.transaction.testutil.TransactionBuilder;

public class TransactionTest {

    private static final Person testPerson = TypicalPersons.ALICE;
    private static final Amount testAmount = new Amount("1.23");
    private static final Description testDescription = new Description("description");
    private static final Date testDate = new Date("01012024");
    private static final Set<Category> testCategories = new HashSet<>(TransactionBuilder.DEFAULT_CATEGORY_SET);
    private static final Status testStatus = new Status(NOT_DONE_STATUS);

    @Test
    public void constructor_nullParams_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new Transaction(null, testAmount,
                testDescription, testDate, testCategories, testStatus
        ));
        assertThrows(NullPointerException.class, () -> new Transaction(testPerson, null,
                testDescription, testDate, testCategories, testStatus
        ));
        assertThrows(NullPointerException.class, () -> new Transaction(testPerson, testAmount,
                null, testDate, testCategories, testStatus
        ));
        assertThrows(NullPointerException.class, () -> new Transaction(testPerson, testAmount,
                testDescription, null, testCategories, testStatus
        ));
        assertThrows(NullPointerException.class, () -> new Transaction(testPerson, testAmount,
                testDescription, testDate, null, testStatus
        ));
        assertThrows(NullPointerException.class, () -> new Transaction(testPerson, testAmount,
                testDescription, testDate, testCategories, null
        ));
    }

    @Test
    public void constructor_validParams_success() {
        assertDoesNotThrow(() -> new Transaction(testPerson, testAmount, testDescription, testDate,
                testCategories, testStatus
        ));
    }

    @Test
    public void equals_validArgument_returnsTrue() {
        Transaction txn1 = new Transaction(
                testPerson, testAmount, testDescription, testDate, testCategories, testStatus);
        Transaction txn2 = new Transaction(
                testPerson, testAmount, testDescription, testDate, testCategories, testStatus);

        assertEquals(txn1, txn2);
        assertEquals(txn1, txn1);

        Set<Category> testCategories2 = new HashSet<>(List.of(new Category("EXTRA")));
        txn2 = new Transaction(testPerson, testAmount, testDescription, testDate, testCategories2, testStatus);
        assertEquals(txn1, txn2);

        Status oppositeTestStatus = new Status(DONE_STATUS);
        txn2 = new Transaction(testPerson, testAmount, testDescription, testDate, testCategories, oppositeTestStatus);
        assertEquals(txn1, txn2);
    }

    @Test
    public void equals_invalidArgument_returnsFalse() {
        Transaction txn1 = new Transaction(
                testPerson, testAmount, testDescription, testDate, testCategories, testStatus);

        Person testPerson2 = TypicalPersons.BOB;
        Transaction txn2 = new Transaction(
                testPerson2, testAmount, testDescription, testDate, testCategories, testStatus);
        assertNotEquals(txn1, txn2);

        Amount testAmount2 = new Amount("-1.23");
        txn2 = new Transaction(testPerson, testAmount2, testDescription, testDate, testCategories, testStatus);
        assertNotEquals(txn1, txn2);

        Description testDescription2 = new Description("description2");
        txn2 = new Transaction(testPerson, testAmount, testDescription2, testDate, testCategories, testStatus);
        assertNotEquals(txn1, txn2);

        Date testDate2 = new Date("02012024");
        txn2 = new Transaction(testPerson, testAmount, testDescription, testDate2, testCategories, testStatus);
        assertNotEquals(txn1, txn2);

        assertNotEquals(null, txn1);
    }

    @Test
    public void hasSameId_validArgument() {
        TransactionBuilder txnBuilder = new TransactionBuilder();
        Transaction t1 = txnBuilder.build();
        Transaction t2 = txnBuilder.withId("420yolo").build();
        assertFalse(t1.hasSameId(t2));
    }

    @Test
    public void containsValidCategory() {
        Transaction txn = new Transaction(
                testPerson, testAmount, testDescription, testDate, testCategories, testStatus);
        assertTrue(txn.containsCategory(new Category("FOOD")));
    }

    @Test
    public void hasSameId_null_throwsNullPointerException() {
        TransactionBuilder txnBuilder = new TransactionBuilder();
        Transaction t1 = txnBuilder.build();
        assertThrows(NullPointerException.class, () -> t1.hasSameId(null));
    }

    @Test
    public void toString_success() {
        Transaction txn1 = new Transaction(testPerson, testAmount, testDescription, testDate,
                TransactionBuilder.DEFAULT_CATEGORY_SET, testStatus
        );

        assertEquals(
                "Alice Pauline [Not Done] (94351253): description on 01/01/2024 for $1.23 with categories: [FOOD]",
                txn1.toString()
        );
    }
}

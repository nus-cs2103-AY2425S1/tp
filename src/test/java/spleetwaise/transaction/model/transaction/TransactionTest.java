package spleetwaise.transaction.model.transaction;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void constructor_nullParams_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new Transaction(null, testAmount,
                testDescription, testDate, testCategories
        ));
        assertThrows(NullPointerException.class, () -> new Transaction(testPerson, null,
                testDescription, testDate, testCategories
        ));
        assertThrows(NullPointerException.class, () -> new Transaction(testPerson, testAmount,
                null, testDate, testCategories
        ));
        assertThrows(NullPointerException.class, () -> new Transaction(testPerson, testAmount,
                testDescription, null, testCategories
        ));
        assertThrows(NullPointerException.class, () -> new Transaction(testPerson, testAmount,
                testDescription, testDate, null
        ));
    }

    @Test
    public void constructor_validParams_success() {
        assertDoesNotThrow(() -> new Transaction(testPerson, testAmount, testDescription, testDate, testCategories));
    }

    @Test
    public void equals_validArgument_returnsTrue() {
        Transaction txn1 = new Transaction(testPerson, testAmount, testDescription, testDate, testCategories);
        Transaction txn2 = new Transaction(testPerson, testAmount, testDescription, testDate, testCategories);

        assertEquals(txn1, txn2);
        assertEquals(txn1, txn1);
    }

    @Test
    public void equals_invalidArgument_returnsFalse() {
        Transaction txn1 = new Transaction(testPerson, testAmount, testDescription, testDate, testCategories);

        Person testPerson2 = TypicalPersons.BOB;
        Transaction txn2 = new Transaction(testPerson2, testAmount, testDescription, testDate, testCategories);
        assertNotEquals(txn1, txn2);

        Amount testAmount2 = new Amount("-1.23");
        txn2 = new Transaction(testPerson, testAmount2, testDescription, testDate, testCategories);
        assertNotEquals(txn1, txn2);

        Description testDescription2 = new Description("description2");
        txn2 = new Transaction(testPerson, testAmount, testDescription2, testDate, testCategories);
        assertNotEquals(txn1, txn2);

        Date testDate2 = new Date("02012024");
        txn2 = new Transaction(testPerson, testAmount, testDescription, testDate2, testCategories);
        assertNotEquals(txn1, txn2);

        Set<Category> testCategories2 = new HashSet<>(List.of(new Category("EXTRA")));
        txn2 = new Transaction(testPerson, testAmount, testDescription, testDate, testCategories2);
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
        Transaction txn = new Transaction(testPerson, testAmount, testDescription, testDate, testCategories);
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
                TransactionBuilder.DEFAULT_CATEGORY_SET
        );

        assertEquals(
                String.format("[%s] Alice Pauline(94351253): description on 01/01/2024 for $1.23 with categories: "
                        + "[FOOD]", txn1.getId()),
                txn1.toString()
        );
    }
}

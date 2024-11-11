package spleetwaise.transaction.model.filterpredicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import spleetwaise.address.model.person.Person;
import spleetwaise.address.testutil.TypicalPersons;
import spleetwaise.transaction.testutil.TypicalTransactions;

public class PersonFilterPredicateTest {

    private static final Person testPerson = TypicalTransactions.SEANOWESME.getPerson();

    @Test
    public void constructor_null_exceptionThrown() {
        assertThrows(NullPointerException.class, () -> new PersonFilterPredicate(null));
    }

    @Test
    public void execute_person_success() {
        PersonFilterPredicate pred = new PersonFilterPredicate(testPerson);

        assertTrue(pred.test(TypicalTransactions.SEANOWESME));
        assertFalse(pred.test(TypicalTransactions.BOBOWES));
    }

    @Test
    public void equals_samePredicate_returnsTrue() {
        PersonFilterPredicate pred = new PersonFilterPredicate(testPerson);
        PersonFilterPredicate pred2 = new PersonFilterPredicate(testPerson);

        assertEquals(pred, pred);
        assertEquals(pred, pred2);
    }

    @Test
    public void equals_diffPredicate_returnsFalse() {
        PersonFilterPredicate pred = new PersonFilterPredicate(testPerson);
        PersonFilterPredicate pred2 = new PersonFilterPredicate(TypicalPersons.BOB);

        assertNotEquals(pred, pred2);
        assertNotEquals(pred, null);
        assertNotEquals(pred, new Object());
    }

    @Test
    public void toString_success() {
        PersonFilterPredicate pred = new PersonFilterPredicate(testPerson);
        assertEquals("spleetwaise.transaction.model.filterpredicate.PersonFilterPredicate{person="
                        + testPerson.toString() + "}",
                pred.toString()
        );
    }
}

package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalWeddings.WEDDING_ONE;

import org.junit.jupiter.api.Test;

public class PersonInWeddingPredicateTest {
    @Test
    public void test_personInWedding_returnsTrue() {
        PersonInWeddingPredicate predicate = new PersonInWeddingPredicate(WEDDING_ONE);
        assertTrue(predicate.test(ALICE));
    }

    @Test
    public void test_personInWedding_returnsFalse() {
        PersonInWeddingPredicate predicate = new PersonInWeddingPredicate(WEDDING_ONE);
        assertTrue(predicate.test(BENSON));
    }
}

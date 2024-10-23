package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class IsSelectedPredicateTest {
    @Test
    public void test_samePerson_returnsTrue() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        IsSelectedPredicate predicate = new IsSelectedPredicate(model, INDEX_FIRST_PERSON);
        assertTrue(predicate.test(model.getFilteredPersonList().get(0)));
    }

    @Test
    public void test_differentPerson_returnsFalse() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        IsSelectedPredicate predicate = new IsSelectedPredicate(model, INDEX_FIRST_PERSON);
        assertFalse(predicate.test(model.getFilteredPersonList().get(2)));
    }
}

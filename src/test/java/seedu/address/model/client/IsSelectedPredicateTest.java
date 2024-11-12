package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class IsSelectedPredicateTest {
    @Test
    public void test_sameClient_returnsTrue() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        IsSelectedPredicate predicate = new IsSelectedPredicate(model, INDEX_FIRST_CLIENT);
        assertTrue(predicate.test(model.getFilteredClientList().get(0)));
    }

    @Test
    public void test_differentClient_returnsFalse() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        IsSelectedPredicate predicate = new IsSelectedPredicate(model, INDEX_FIRST_CLIENT);
        assertFalse(predicate.test(model.getFilteredClientList().get(2)));
    }
}

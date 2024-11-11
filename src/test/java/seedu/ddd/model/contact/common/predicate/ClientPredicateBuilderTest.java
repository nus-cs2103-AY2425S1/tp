package seedu.ddd.model.contact.common.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_CLIENT;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_VENDOR;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.parser.ArgumentMultimap;
import seedu.ddd.logic.parser.exceptions.ParseException;

public class ClientPredicateBuilderTest {

    @Test
    public void test_client_returnsTrue() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        ContactPredicateBuilder predicateBuilder = new ClientPredicateBuilder(argMultimap);
        assertTrue(predicateBuilder.build().test(VALID_CLIENT));
    }

    @Test
    public void test_vendor_returnsFalse() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        ContactPredicateBuilder predicateBuilder = new ClientPredicateBuilder(argMultimap);
        assertFalse(predicateBuilder.build().test(VALID_VENDOR));
    }

}

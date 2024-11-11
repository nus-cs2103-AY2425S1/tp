package seedu.ddd.model.contact.common.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_SERVICE;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_CLIENT;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_VENDOR;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.parser.ArgumentMultimap;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.testutil.contact.VendorBuilder;

public class VendorPredicateBuilderTest {

    @Test
    public void test_vendor_returnsTrue() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        ContactPredicateBuilder predicateBuilder = new VendorPredicateBuilder(argMultimap);
        assertTrue(predicateBuilder.build().test(VALID_VENDOR));
    }

    @Test
    public void test_client_returnsFalse() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        ContactPredicateBuilder predicateBuilder = new VendorPredicateBuilder(argMultimap);
        assertFalse(predicateBuilder.build().test(VALID_CLIENT));
    }

    @Test
    public void testArgumentMultiMap_containsService_returnsTrue() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_SERVICE, "Alice Millow");
        ContactPredicateBuilder predicateBuilder = new VendorPredicateBuilder(argMultimap);

        assertTrue(predicateBuilder.build().test(new VendorBuilder().withService("Alice Millow").build()));
        assertTrue(predicateBuilder.build().test(new VendorBuilder().withService("Alice").build()));
        assertTrue(predicateBuilder.build().test(new VendorBuilder().withService("milLow").build()));
    }

    @Test
    public void testArgumentMultimap_doesNotContainService_returnsFalse() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_SERVICE, "Alice Millow");
        ContactPredicateBuilder predicateBuilder = new VendorPredicateBuilder(argMultimap);

        assertFalse(predicateBuilder.build().test(new VendorBuilder().withService("Benson Nguyen").build()));
        assertFalse(predicateBuilder.build().test(new VendorBuilder().withService("Harry Potter").build()));
        assertFalse(predicateBuilder.build().test(new VendorBuilder().withService("benSOn Nguyen").build()));
    }
    @Test
    public void testArgumentMultimap_doesNotContainValidService_throwsParseExceptionError() {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        // Not alphanumeric input
        argMultimap.put(PREFIX_SERVICE, "catering n/Jane");
        ContactPredicateBuilder predicateBuilder = new VendorPredicateBuilder(argMultimap);
        assertThrows(ParseException.class, predicateBuilder::build);
    }
    @Test
    public void testArgumentMultimap_doesNotContainService_throwsParseExceptionError() {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_SERVICE, "");
        ContactPredicateBuilder predicateBuilder = new VendorPredicateBuilder(argMultimap);
        assertThrows(ParseException.class, predicateBuilder::build);
    }

}

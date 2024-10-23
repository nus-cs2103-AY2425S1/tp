package seedu.ddd.model.contact.common.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.parser.CliFlags.FLAG_CLIENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_VENDOR;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ddd.testutil.TypicalContacts.ALICE;
import static seedu.ddd.testutil.TypicalContacts.BENSON;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.parser.ArgumentMultimap;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.testutil.ClientBuilder;

public class ContactPredicateBuilderTest {

    @Test
    public void equals() {
        ArgumentMultimap argMultimap1 = new ArgumentMultimap();
        argMultimap1.put(PREFIX_NAME, "Alice Millow");
        ContactPredicateBuilder firstPredicate = new ContactPredicateBuilder(argMultimap1);

        ArgumentMultimap argMultimap2 = new ArgumentMultimap();
        argMultimap2.put(PREFIX_ID, "1");
        ContactPredicateBuilder secondPredicate = new ContactPredicateBuilder(argMultimap2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContactPredicateBuilder firstPredicateCopy =
                new ContactPredicateBuilder(argMultimap1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different name list -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testArgumentMultimapContainsNameReturnsTrue() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_NAME, "Alice Millow");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);

        assertTrue(predicateBuilder.build().test(new ClientBuilder().withName("Alice Millow").build()));
        assertTrue(predicateBuilder.build().test(new ClientBuilder().withName("Alice").build()));
        assertTrue(predicateBuilder.build().test(new ClientBuilder().withName("milLow").build()));
    }

    @Test
    public void testArgumentMultimapDoesNotContainNameReturnsFalse() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_NAME, "Alice Millow");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);

        assertFalse(predicateBuilder.build().test(new ClientBuilder().withName("Benson Nguyen").build()));
        assertFalse(predicateBuilder.build().test(new ClientBuilder().withName("Harry Potter").build()));
        assertFalse(predicateBuilder.build().test(new ClientBuilder().withName("benSOn Nguyen").build()));
    }

    @Test
    public void testArgumentMultimapContainsClientReturnsTrue() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(FLAG_CLIENT, "");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);
        assertTrue(predicateBuilder.build().test(ALICE));
    }

    @Test
    public void testArgumentMultimapDoesNotContainClientReturnsFalse() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(FLAG_CLIENT, "");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);
        assertFalse(predicateBuilder.build().test(BENSON));
    }

    @Test
    public void testArgumentMultimapContainsVendorReturnsTrue() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(FLAG_VENDOR, "");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);
        assertTrue(predicateBuilder.build().test(BENSON));
    }

    @Test
    public void testArgumentMultimapDoesNotContainVendorReturnsFalse() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(FLAG_VENDOR, "");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);
        assertFalse(predicateBuilder.build().test(ALICE));
    }

    @Test
    public void testArgumentMultimapContainPhoneReturnsTrue() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_PHONE, "8123");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);

        assertTrue(predicateBuilder.build().test(new ClientBuilder().withPhone("81234567").build()));
        assertTrue(predicateBuilder.build().test(new ClientBuilder().withPhone("8123").build()));
        assertTrue(predicateBuilder.build().test(new ClientBuilder().withPhone("45678123").build()));
    }

    @Test
    public void testArgumentMultimapDoesNotContainPhoneReturnsFalse() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_PHONE, "1092");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);

        assertFalse(predicateBuilder.build().test(new ClientBuilder().withPhone("81234567").build()));
        assertFalse(predicateBuilder.build().test(new ClientBuilder().withPhone("10931234").build()));
        assertFalse(predicateBuilder.build().test(new ClientBuilder().withPhone("90120312").build()));
    }

    @Test
    public void testArgumentMultimapContainAddressReturnsTrue() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_ADDRESS, "Main Street");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);

        assertTrue(predicateBuilder.build().test(new ClientBuilder().withAddress("Main Street").build()));
        assertTrue(predicateBuilder.build().test(new ClientBuilder().withAddress("Main").build()));
        assertTrue(predicateBuilder.build().test(new ClientBuilder().withAddress("Street").build()));
    }

    @Test
    public void testArgumentMultimapDoesNotContainAddressReturnsFalse() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_ADDRESS, "Main Street");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);

        assertFalse(predicateBuilder.build().test(new ClientBuilder().withAddress("Benson Nguyen").build()));
        assertFalse(predicateBuilder.build().test(new ClientBuilder().withAddress("Block 123 Jane Avenue 6").build()));
        assertFalse(predicateBuilder.build().test(new ClientBuilder().withAddress("Bukit Merah").build()));
    }

    @Test
    public void testArgumentMultimapContainIdReturnsTrue() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_ID, "1");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);

        assertTrue(predicateBuilder.build().test(new ClientBuilder().withId(1).build()));

        argMultimap.put(PREFIX_ID, "2");
        assertTrue(predicateBuilder.build().test(new ClientBuilder().withId(2).build()));
    }

    @Test
    public void testArgumentMultimapDoesNotContainIdReturnsFalse() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_ID, "2");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);

        assertFalse(predicateBuilder.build().test(new ClientBuilder().withId(1).build()));

        argMultimap.put(PREFIX_ID, "1");
        assertFalse(predicateBuilder.build().test(new ClientBuilder().withId(2).build()));
    }

    @Test
    public void testArgumentMultimapContainTagsReturnsTrue() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);
        // empty tags
        assertTrue(predicateBuilder.build().test(new ClientBuilder().build()));

        argMultimap.put(PREFIX_TAG, "expert");
        assertTrue(predicateBuilder.build().test(new ClientBuilder().withTags("expert", "tall").build()));

        argMultimap.put(PREFIX_TAG, "tall");
        assertTrue(predicateBuilder.build().test(new ClientBuilder().withTags("expert", "tall").build()));
    }

    @Test
    public void testArgumentMultimapDoesNotContainTagsReturnsFalse() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_TAG, "friend");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);

        assertFalse(predicateBuilder.build().test(ALICE));

        argMultimap.put(PREFIX_TAG, "oweMoneies");
        assertFalse(predicateBuilder.build().test(BENSON));
    }
    @Test
    public void testArgumentMultimapContainEmailReturnsTrue() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_EMAIL, "alice@example.com");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);

        assertTrue(predicateBuilder.build().test(ALICE));

        argMultimap.put(PREFIX_EMAIL, "johnd@example.com");
        assertTrue(predicateBuilder.build().test(BENSON));
    }

    @Test
    public void testArgumentMultimapDoesNotContainEmailReturnsFalse() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_EMAIL, "alice@example.com");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);

        assertFalse(predicateBuilder.build().test(BENSON));

        argMultimap.put(PREFIX_EMAIL, "johnd@example.com");
        assertFalse(predicateBuilder.build().test(ALICE));
    }


    @Test
    public void testArgumentMultimapDoesNotContainNameThrowsParseException() {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_NAME, "");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);
        assertThrows(ParseException.class, predicateBuilder::build);
    }

    @Test
    public void testArgumentMultimapDoesNotContainIdThrowsParseException() {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_ID, "");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);
        assertThrows(ParseException.class, predicateBuilder::build);
    }

    @Test
    public void testArgumentMultimapDoesNotContainAddressThrowsParseException() {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_ADDRESS, "");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);
        assertThrows(ParseException.class, predicateBuilder::build);
    }

    @Test
    public void testArgumentMultimapDoesNotContainPhoneThrowsParseException() {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_PHONE, "");
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);
        assertThrows(ParseException.class, predicateBuilder::build);
    }

    @Test
    public void toStringMethod() {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        ContactPredicateBuilder predicateBuilder = new ContactPredicateBuilder(argMultimap);

        String expected = ContactPredicateBuilder.class.getCanonicalName() + "{}";
        assertEquals(expected, predicateBuilder.toString());
    }
}

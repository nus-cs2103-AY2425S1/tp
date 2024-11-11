package seedu.ddd.model.event.common.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.parser.ArgumentMultimap;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.testutil.event.EventBuilder;

public class EventPredicateBuilderTest {
    private String validDate1 = "2000-01-03";
    private String validDate2 = "3 Jan 2000";
    private String validDate3 = "01/03/2000";
    private String differentValidDate = "2000-03-01";

    @Test
    public void equals() {
        ArgumentMultimap argMultimap1 = new ArgumentMultimap();
        argMultimap1.put(PREFIX_DESC, "Alice Millow");
        EventPredicateBuilder firstPredicate = new EventPredicateBuilder(argMultimap1);

        ArgumentMultimap argMultimap2 = new ArgumentMultimap();
        argMultimap2.put(PREFIX_ID, "1");
        EventPredicateBuilder secondPredicate = new EventPredicateBuilder(argMultimap2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventPredicateBuilder firstPredicateCopy =
                new EventPredicateBuilder(argMultimap1);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different name list -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testArgumentMultimap_containsDescription_returnsTrue() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_DESC, "Alice Millow");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);

        assertTrue(predicateBuilder.build().test(new EventBuilder().withDescription("Alice Millow").build()));
        assertTrue(predicateBuilder.build().test(new EventBuilder().withDescription("Alice").build()));
        assertTrue(predicateBuilder.build().test(new EventBuilder().withDescription("milLow").build()));
    }

    @Test
    public void testArgumentMultimap_doesNotContainDescription_returnsFalse() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_DESC, "Alice Millow");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);

        assertFalse(predicateBuilder.build().test(new EventBuilder().withDescription("Benson Nguyen").build()));
        assertFalse(predicateBuilder.build().test(new EventBuilder().withDescription("Harry Potter").build()));
        assertFalse(predicateBuilder.build().test(new EventBuilder().withDescription("benSOn Nguyen").build()));
    }
    @Test
    public void testArgumentMultimap_containsName_returnsTrue() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_NAME, "Alice Millow");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);

        assertTrue(predicateBuilder.build().test(new EventBuilder().withName("Alice Millow").build()));
        assertTrue(predicateBuilder.build().test(new EventBuilder().withName("Alice").build()));
        assertTrue(predicateBuilder.build().test(new EventBuilder().withName("milLow").build()));
    }
    @Test
    public void testArgumentMultimap_doesNotContainName_returnsFalse() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_NAME, "Alice Millow");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);

        assertFalse(predicateBuilder.build().test(new EventBuilder().withName("Benson Nguyen").build()));
        assertFalse(predicateBuilder.build().test(new EventBuilder().withName("Harry Potter").build()));
        assertFalse(predicateBuilder.build().test(new EventBuilder().withName("benSOn Nguyen").build()));
    }

    @Test
    public void testArgumentMultimap_containsId_returnsTrue() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_ID, "1");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);

        assertTrue(predicateBuilder.build().test(new EventBuilder().withEventId(1).build()));

        argMultimap.put(PREFIX_ID, "2");
        assertTrue(predicateBuilder.build().test(new EventBuilder().withEventId(2).build()));
    }

    @Test
    public void testArgumentMultimap_doesNotContainId_returnsFalse() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_ID, "2");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);

        assertFalse(predicateBuilder.build().test(new EventBuilder().withEventId(1).build()));

        argMultimap.put(PREFIX_ID, "1");
        assertFalse(predicateBuilder.build().test(new EventBuilder().withEventId(2).build()));
    }
    @Test
    public void testArgumentMultimap_doesNotContainValidId_throwsParseException() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_ID, "two");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);

        assertThrows(ParseException.class, predicateBuilder::build);

        argMultimap.put(PREFIX_ID, "one");
        assertThrows(ParseException.class, predicateBuilder::build);
    }
    @Test
    public void testArgumentMultimap_containsDate_returnsTrue() throws ParseException {
        // Three same date inputs with different formats


        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_DATE, validDate1);
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);

        assertTrue(predicateBuilder.build().test(new EventBuilder().withDate(validDate1).build()));

        argMultimap.put(PREFIX_DATE, validDate2);
        assertTrue(predicateBuilder.build().test(new EventBuilder().withDate(validDate1).build()));

        argMultimap.put(PREFIX_DATE, validDate3);
        assertTrue(predicateBuilder.build().test(new EventBuilder().withDate(validDate1).build()));
    }

    @Test
    public void testArgumentMultimap_doesNotContainDate_returnsFalse() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_DATE, differentValidDate);
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);

        // Test different date against all three valid inputs.
        assertFalse(predicateBuilder.build().test(new EventBuilder().withDate(validDate1).build()));

        argMultimap.put(PREFIX_DATE, differentValidDate);
        assertFalse(predicateBuilder.build().test(new EventBuilder().withDate(validDate2).build()));

        argMultimap.put(PREFIX_DATE, differentValidDate);
        assertFalse(predicateBuilder.build().test(new EventBuilder().withDate(validDate3).build()));
    }

    @Test
    public void testArgumentMultimap_doesNotContainDescription_throwsParseExceptionError() {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_DESC, "");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);
        assertThrows(ParseException.class, predicateBuilder::build);
    }
    @Test
    public void testArgumentMultimap_doesNotContainName_throwsParseExceptionError() {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_NAME, "");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);
        assertThrows(ParseException.class, predicateBuilder::build);
    }

    @Test
    public void testArgumentMultimap_doesNotContainId_throwsParseExceptionError() {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_ID, "");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);
        assertThrows(ParseException.class, predicateBuilder::build);
    }

    @Test
    public void testArgumentMultimap_doesNotContainDate_throwsParseExceptionError() {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_DATE, "");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);
        assertThrows(ParseException.class, predicateBuilder::build);
    }

    @Test
    public void toStringMethod() {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);

        String expected = EventPredicateBuilder.class.getCanonicalName() + "{}";
        assertEquals(expected, predicateBuilder.toString());
    }
}

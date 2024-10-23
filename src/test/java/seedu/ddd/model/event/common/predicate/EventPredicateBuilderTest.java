package seedu.ddd.model.event.common.predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ID;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.parser.ArgumentMultimap;
import seedu.ddd.logic.parser.exceptions.ParseException;
import seedu.ddd.testutil.EventBuilder;

public class EventPredicateBuilderTest {

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
    public void testArgumentMultimapContainDescriptionReturnsTrue() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_DESC, "Alice Millow");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);

        assertTrue(predicateBuilder.build().test(new EventBuilder().withDescription("Alice Millow").build()));
        assertTrue(predicateBuilder.build().test(new EventBuilder().withDescription("Alice").build()));
        assertTrue(predicateBuilder.build().test(new EventBuilder().withDescription("milLow").build()));
    }

    @Test
    public void testArgumentMultimapDoesNotContainDescriptionReturnsFalse() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_DESC, "Alice Millow");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);

        assertFalse(predicateBuilder.build().test(new EventBuilder().withDescription("Benson Nguyen").build()));
        assertFalse(predicateBuilder.build().test(new EventBuilder().withDescription("Harry Potter").build()));
        assertFalse(predicateBuilder.build().test(new EventBuilder().withDescription("benSOn Nguyen").build()));
    }

    @Test
    public void testArgumentMultimapContainIdReturnsTrue() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_ID, "1");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);

        assertTrue(predicateBuilder.build().test(new EventBuilder().withEventId(1).build()));

        argMultimap.put(PREFIX_ID, "2");
        assertTrue(predicateBuilder.build().test(new EventBuilder().withEventId(2).build()));
    }

    @Test
    public void testArgumentMultimapDoesNotContainIdReturnsFalse() throws ParseException {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_ID, "2");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);

        assertFalse(predicateBuilder.build().test(new EventBuilder().withEventId(1).build()));

        argMultimap.put(PREFIX_ID, "1");
        assertFalse(predicateBuilder.build().test(new EventBuilder().withEventId(2).build()));
    }

    @Test
    public void testArgumentMultimapDoesNotContainDescriptionThrowsParseException() {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_DESC, "");
        EventPredicateBuilder predicateBuilder = new EventPredicateBuilder(argMultimap);
        assertThrows(ParseException.class, predicateBuilder::build);
    }

    @Test
    public void testArgumentMultimapDoesNotContainIdThrowsParseException() {
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        argMultimap.put(PREFIX_ID, "");
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

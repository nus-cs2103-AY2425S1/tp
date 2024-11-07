package seedu.eventfulnus.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.eventfulnus.testutil.TypicalEvents;

public class EventContainsKeywordsPredicateTest {
    @Test
    void keywordsMatchSport_returnsTrue() {
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(List.of("Chess"));
        assertTrue(predicate.test(TypicalEvents.CHESS_COM_NUSC));
    }

    @Test
    void keywordsDoNotMatchSport_returnsFalse() {
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(List.of("Swimming"));
        assertFalse(predicate.test(TypicalEvents.CHESS_COM_NUSC));
    }

    @Test
    void multipleKeywordsMatchEventName_returnsTrue() {
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(
                List.of("CHESS", "NUSC", "COM"));
        assertTrue(predicate.test(TypicalEvents.CHESS_COM_NUSC));
    }

    @Test
    void noKeywords_returnsFalse() {
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(List.of());
        assertFalse(predicate.test(TypicalEvents.CHESS_COM_NUSC));
    }

    @Test
    void keywordsMatchPartOfSport_returnsTrue() {
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(List.of("che"));
        assertTrue(predicate.test(TypicalEvents.CHESS_COM_NUSC));
    }

    @Test
    void keywordsMatchEventNameCaseInsensitive_returnsTrue() {
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(List.of("chess"));
        assertTrue(predicate.test(TypicalEvents.CHESS_COM_NUSC));
    }

    @Test
    void equals_sameObject_returnsTrue() {
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(List.of("CHESS_COM_NUSC"));
        assertEquals(predicate, predicate);
    }

    @Test
    void equals_differentObjectSameKeywords_returnsTrue() {
        EventContainsKeywordsPredicate predicate1 = new EventContainsKeywordsPredicate(List.of("CHESS_COM_NUSC"));
        EventContainsKeywordsPredicate predicate2 = new EventContainsKeywordsPredicate(List.of("CHESS_COM_NUSC"));
        assertEquals(predicate1, predicate2);
    }

    @Test
    void equals_differentObjectDifferentKeywords_returnsFalse() {
        EventContainsKeywordsPredicate predicate1 = new EventContainsKeywordsPredicate(List.of("CHESS_COM_NUSC"));
        EventContainsKeywordsPredicate predicate2 = new EventContainsKeywordsPredicate(List.of("SWIMMING_M_MED_DEN"));
        assertNotEquals(predicate1, predicate2);
    }

    @Test
    void equals_null_returnsFalse() {
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(List.of("CHESS_COM_NUSC"));
        assertNotEquals(null, predicate);
    }

    @Test
    void equals_differentType_returnsFalse() {
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(List.of("CHESS_COM_NUSC"));
        assertNotEquals(1, predicate);
    }
}

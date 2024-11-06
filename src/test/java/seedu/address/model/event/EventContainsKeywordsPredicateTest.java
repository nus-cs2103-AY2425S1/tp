//package seedu.address.model.event;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//
//public class EventContainsKeywordsPredicateTest {
//    @Test
//    void keywordsMatchEventName_returnsTrue() {
//        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(List.of("IFG"));
//        assertTrue(predicate.test(new Event(new EventName("IFG"))));
//    }
//
//    @Test
//    void keywordsDoNotMatchEventName_returnsFalse() {
//        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(List.of("SUNIG"));
//        assertFalse(predicate.test(new Event(new EventName("IFG"))));
//    }
//
//    @Test
//    void multipleKeywordsMatchEventName_returnsTrue() {
//        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(List.of("IFG", "SUNIG"));
//        assertTrue(predicate.test(new Event(new EventName("IFG"))));
//    }
//
//    @Test
//    void noKeywords_returnsFalse() {
//        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(List.of());
//        assertFalse(predicate.test(new Event(new EventName("IFG"))));
//    }
//
//    @Test
//    void keywordsMatchPartOfEventName_returnsTrue() {
//        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(List.of("IF"));
//        assertTrue(predicate.test(new Event(new EventName("IFG"))));
//    }
//
//    @Test
//    void keywordsMatchEventNameCaseInsensitive_returnsTrue() {
//        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(List.of("ifg"));
//        assertTrue(predicate.test(new Event(new EventName("IFG"))));
//    }
//
//    @Test
//    void equals_sameObject_returnsTrue() {
//        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(List.of("IFG"));
//        assertEquals(predicate, predicate);
//    }
//
//    @Test
//    void equals_differentObjectSameKeywords_returnsTrue() {
//        EventContainsKeywordsPredicate predicate1 = new EventContainsKeywordsPredicate(List.of("IFG"));
//        EventContainsKeywordsPredicate predicate2 = new EventContainsKeywordsPredicate(List.of("IFG"));
//        assertEquals(predicate1, predicate2);
//    }
//
//    @Test
//    void equals_differentObjectDifferentKeywords_returnsFalse() {
//        EventContainsKeywordsPredicate predicate1 = new EventContainsKeywordsPredicate(List.of("IFG"));
//        EventContainsKeywordsPredicate predicate2 = new EventContainsKeywordsPredicate(List.of("SUNIG"));
//        assertNotEquals(predicate1, predicate2);
//    }
//
//    @Test
//    void equals_null_returnsFalse() {
//        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(List.of("IFG"));
//        assertNotEquals(null, predicate);
//    }
//
//    @Test
//    void equals_differentType_returnsFalse() {
//        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(List.of("IFG"));
//        assertNotEquals(1, predicate);
//    }
//}

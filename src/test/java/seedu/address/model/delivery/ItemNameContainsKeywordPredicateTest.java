package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.DeliveryBuilder;

public class ItemNameContainsKeywordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ItemNameContainsKeywordPredicate firstPredicate =
                new ItemNameContainsKeywordPredicate(firstPredicateKeywordList);
        ItemNameContainsKeywordPredicate secondPredicate =
                new ItemNameContainsKeywordPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        ItemNameContainsKeywordPredicate firstPredicateCopy =
                new ItemNameContainsKeywordPredicate(firstPredicateKeywordList);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertNotEquals(1, firstPredicate);

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different person -> returns false
        assertNotEquals(firstPredicate, secondPredicate);
    }

    @Test
    public void test_itemNameContainsKeywords_returnsTrue() {
        // One keyword
        ItemNameContainsKeywordPredicate predicate =
                new ItemNameContainsKeywordPredicate(Collections.singletonList("monitor"));
        assertTrue(predicate.test(new DeliveryBuilder().withItems(Collections.singletonList(new ItemName("monitor")))
                .build()));

        // Multiple keywords
        predicate = new ItemNameContainsKeywordPredicate(Arrays.asList("monitor", "mouse"));
        assertTrue(predicate.test(new DeliveryBuilder().withItems(Arrays.asList(new ItemName("monitor"),
                new ItemName("mouse"))).build()));

        // Only one matching keyword
        predicate = new ItemNameContainsKeywordPredicate(Arrays.asList("monitor", "mouse"));
        assertTrue(predicate.test(new DeliveryBuilder().withItems(Arrays.asList(new ItemName("monitor"),
                new ItemName("keyboard"))).build()));

        // Mixed-case keywords
        predicate = new ItemNameContainsKeywordPredicate(Arrays.asList("MoNiTor", "mOusE"));
        assertTrue(predicate.test(new DeliveryBuilder().withItems(Arrays.asList(new ItemName("monitor"),
                new ItemName("mouse"))).build()));
    }

    @Test
    public void test_itemNameContainsKeywords_returnsFalse() {
        // Zero keywords
        ItemNameContainsKeywordPredicate predicate = new ItemNameContainsKeywordPredicate(Collections.emptyList());
        assertFalse(predicate.test(new DeliveryBuilder().build()));

        // Non-matching keyword
        predicate = new ItemNameContainsKeywordPredicate(Arrays.asList("monitor"));
        assertFalse(predicate.test(new DeliveryBuilder().withItems(Collections.singletonList(new ItemName("mouse")))
                .build()));

        // Keywords match everything else but name
        predicate = new ItemNameContainsKeywordPredicate(Arrays.asList("$10", "2024-10-10", "monitor", "delivered",
                "00:00:01", "S123456", "Dummy"));
        assertFalse(predicate.test(new DeliveryBuilder().withCost("$10").withDate("2024-10-10").withEta("2024-10-10")
                .withItems(Collections.singletonList(new ItemName("mouse"))).withStatus("delivered")
                .withTime("00:00:01").withAddress("456, Test Address, #12-34, S123456")
                .withTags(Collections.singletonList(new Tag("Dummy Tag"))).build()
        ));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        ItemNameContainsKeywordPredicate predicate = new ItemNameContainsKeywordPredicate(keywords);

        String expected = ItemNameContainsKeywordPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }

}

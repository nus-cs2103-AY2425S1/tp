package seedu.address.model.student.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.Student;

class AttributeContainsKeywordsPredicateTest {

    @Test
    public void getKeywords() {
        List<String> keywords = List.of("keyword1", "keyword2");
        AttributeContainsKeywordsPredicate<String> predicate = generatePredicate(keywords);

        assertEquals(keywords, predicate.getKeywords());
    }

    @Test
    public void equals() {
        List<String> keywords = List.of("keyword1", "keyword2");
        AttributeContainsKeywordsPredicate<String> predicate = generatePredicate(keywords);

        // same object -> returns true
        assertEquals(predicate, predicate);

        // same values -> returns true
        AttributeContainsKeywordsPredicate<String> predicateCopy = generatePredicate(keywords);
        assertEquals(predicate, predicateCopy);

        // different types -> returns false
        assertNotEquals(predicate, 1);

        // null -> returns false
        assertNotEquals(predicate, null);
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        AttributeContainsKeywordsPredicate<String> predicate = generatePredicate(keywords);

        // anonymous class do not have canonical name
        String expected = "null{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }

    public AttributeContainsKeywordsPredicate<String> generatePredicate(List<String> keywords) {
        return new AttributeContainsKeywordsPredicate<String>(keywords) {
            @Override
            public boolean test(Student student) {
                return false;
            }

        };
    }
}

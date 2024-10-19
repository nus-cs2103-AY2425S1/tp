package seedu.address.model.student.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        AttributeContainsKeywordsPredicate<String> predicate = generatePredicate(keywords);

        String expected = AttributeContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
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

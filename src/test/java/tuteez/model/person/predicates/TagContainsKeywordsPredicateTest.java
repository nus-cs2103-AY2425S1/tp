package tuteez.model.person.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import tuteez.model.person.Person;
import tuteez.testutil.PersonBuilder;

public class TagContainsKeywordsPredicateTest {

    private static final Person personWithTag =
            new PersonBuilder().withName("bob").withTags("friend", "student").build();

    @Test
    public void test_tagMatchesKeyword_returnsTrue() {
        List<String> keywords = List.of("friend");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(personWithTag));
    }

    @Test
    public void test_tagMatchesKeywordCaseInsensitive_returnsTrue() {
        List<String> keywords = List.of("FRIEND");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(personWithTag));
    }

    @Test
    public void test_tagDoesNotMatchAnyKeyword_returnsFalse() {
        List<String> keywords = List.of("teacher");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keywords);
        assertFalse(predicate.test(personWithTag));
    }

    @Test
    public void test_tagMatchesMultipleKeywords_returnsTrue() {
        List<String> keywords = List.of("student", "teacher");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keywords);
        assertTrue(predicate.test(personWithTag));
    }

    @Test
    public void equals_sameKeywords_returnsTrue() {
        List<String> keywords1 = List.of("friend", "student");
        List<String> keywords2 = List.of("friend", "student");

        TagContainsKeywordsPredicate predicate1 = new TagContainsKeywordsPredicate(keywords1);
        TagContainsKeywordsPredicate predicate2 = new TagContainsKeywordsPredicate(keywords2);

        assertTrue(predicate1.equals(predicate2));
    }

    @Test
    public void equals_differentKeywords_returnsFalse() {
        List<String> keywords1 = List.of("friend", "student");
        List<String> keywords2 = List.of("classmate", "teacher");

        TagContainsKeywordsPredicate predicate1 = new TagContainsKeywordsPredicate(keywords1);
        TagContainsKeywordsPredicate predicate2 = new TagContainsKeywordsPredicate(keywords2);

        assertFalse(predicate1.equals(predicate2));
    }
}

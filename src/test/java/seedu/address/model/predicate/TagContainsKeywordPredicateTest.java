package seedu.address.model.predicate;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordPredicateTest {

    @Test
    public void testTagContainsExactKeyword_success() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("Scholar");
        Person person = new PersonBuilder().withTags("Scholar").build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void testTagContainsCaseInsensitiveKeyword_success() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("SchOLaR");
        Person person = new PersonBuilder().withTags("scholar").build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void testTagContainsKeywordWithMultipleTags_success() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("ADHD");
        Person person = new PersonBuilder().withTags("Scholar", "ADHD", "SessionB").build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void testTagDoesNotContainKeyword_failure() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("Scholar");
        Person person = new PersonBuilder().withTags("ADHD", "sessionA").build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void testTagContainsKeywordInDifferentCase_failure() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("scholar");
        Person person = new PersonBuilder().withTags("SCHOLAR", "school").build();
        assertTrue(predicate.test(person)); // Should return true because of case-insensitive match
    }

    @Test
    public void testMultipleTagsWithOnlyOneMatching_success() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("scholar");
        Person person = new PersonBuilder().withTags("ADHD", "scholar", "SessionA").build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void testPersonWithNoTags_noMatch() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("SessionA");
        Person person = new PersonBuilder().build(); // No tags added
        assertFalse(predicate.test(person));
    }

    @Test
    public void testPersonWithMultipleSameTags_containsKeyword_success() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("SessionA");
        Person person = new PersonBuilder().withTags("SessionA", "SessionA", "SessionA").build();
        assertTrue(predicate.test(person)); // Multiple same tags should still return true
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("scholar");
        assertEquals(predicate, predicate); // Same object should be equal
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("scholar");
        assertNotEquals("some string", predicate); // Different types should not be equal
    }

    @Test
    public void equals_differentKeyword_returnsFalse() {
        TagContainsKeywordPredicate predicate1 = new TagContainsKeywordPredicate("scholar");
        TagContainsKeywordPredicate predicate2 = new TagContainsKeywordPredicate("ADHD");
        assertNotEquals(predicate1, predicate2); // Different keywords should not be equal
    }

    @Test
    public void equals_sameKeyword_returnsTrue() {
        TagContainsKeywordPredicate predicate1 = new TagContainsKeywordPredicate("scholar");
        TagContainsKeywordPredicate predicate2 = new TagContainsKeywordPredicate("scholar");
        assertEquals(predicate1, predicate2); // Same keyword should be equal
    }

    @Test
    public void toString_correctFormat() {
        String keyword = "scholar";
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(keyword);
        String expected = TagContainsKeywordPredicate.class.getCanonicalName() + "{keyword=" + keyword + "}";
        assertEquals(expected, predicate.toString()); // Verify that toString returns the expected format
    }
}


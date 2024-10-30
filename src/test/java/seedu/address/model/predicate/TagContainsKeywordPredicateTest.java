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
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("friend");
        Person person = new PersonBuilder().withTags("friend").build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void testTagContainsCaseInsensitiveKeyword_success() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("FrIeNd");
        Person person = new PersonBuilder().withTags("friend").build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void testTagContainsKeywordWithMultipleTags_success() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("student");
        Person person = new PersonBuilder().withTags("friend", "student", "colleague").build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void testTagDoesNotContainKeyword_failure() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("hobby");
        Person person = new PersonBuilder().withTags("friend", "family").build();
        assertFalse(predicate.test(person));
    }

    @Test
    public void testTagContainsKeywordInDifferentCase_failure() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("Work");
        Person person = new PersonBuilder().withTags("WORK", "school").build();
        assertTrue(predicate.test(person)); // Should return true because of case-insensitive match
    }

    @Test
    public void testMultipleTagsWithOnlyOneMatching_success() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("hobby");
        Person person = new PersonBuilder().withTags("friend", "hobby", "colleague").build();
        assertTrue(predicate.test(person));
    }

    @Test
    public void testPersonWithNoTags_noMatch() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("friend");
        Person person = new PersonBuilder().build(); // No tags added
        assertFalse(predicate.test(person));
    }

    @Test
    public void testPersonWithMultipleSameTags_containsKeyword_success() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("study");
        Person person = new PersonBuilder().withTags("study", "study", "study").build();
        assertTrue(predicate.test(person)); // Multiple same tags should still return true
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("friend");
        assertEquals(predicate, predicate); // Same object should be equal
    }

    @Test
    public void equals_differentTypes_returnsFalse() {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate("friend");
        assertNotEquals("some string", predicate); // Different types should not be equal
    }

    @Test
    public void equals_differentKeyword_returnsFalse() {
        TagContainsKeywordPredicate predicate1 = new TagContainsKeywordPredicate("friend");
        TagContainsKeywordPredicate predicate2 = new TagContainsKeywordPredicate("family");
        assertNotEquals(predicate1, predicate2); // Different keywords should not be equal
    }

    @Test
    public void equals_sameKeyword_returnsTrue() {
        TagContainsKeywordPredicate predicate1 = new TagContainsKeywordPredicate("friend");
        TagContainsKeywordPredicate predicate2 = new TagContainsKeywordPredicate("friend");
        assertEquals(predicate1, predicate2); // Same keyword should be equal
    }

    @Test
    public void toString_correctFormat() {
        String keyword = "friend";
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(keyword);
        String expected = TagContainsKeywordPredicate.class.getCanonicalName() + "{keyword=" + keyword + "}";
        assertEquals(expected, predicate.toString()); // Verify that toString returns the expected format
    }
}


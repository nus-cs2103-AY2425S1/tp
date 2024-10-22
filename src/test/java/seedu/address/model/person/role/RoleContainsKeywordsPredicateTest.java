package seedu.address.model.person.role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class RoleContainsKeywordsPredicateTest {
    @Test
    void keywordsMatchRole_returnsTrue() {
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(List.of("Manager"));
        Person person = new PersonBuilder().withRoles("Manager").build();
        assertTrue(predicate.test(person));
    }

    @Test
    void keywordsDoNotMatchRole_returnsFalse() {
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(List.of("Developer"));
        Person person = new PersonBuilder().withRoles("Manager").build();
        assertFalse(predicate.test(person));
    }

    @Test
    void multipleKeywordsMatchRole_returnsTrue() {
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(List.of("Manager", "Developer"));
        Person person = new PersonBuilder().withRoles("Manager").build();
        assertTrue(predicate.test(person));
    }

    @Test
    void noKeywords_returnsFalse() {
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(List.of());
        Person person = new PersonBuilder().withRoles("Manager").build();
        assertFalse(predicate.test(person));
    }

    @Test
    void keywordsMatchPartOfRole_returnsFalse() {
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(List.of("Man"));
        Person person = new PersonBuilder().withRoles("Manager").build();
        assertFalse(predicate.test(person));
    }

    @Test
    void keywordsMatchRoleCaseInsensitive_returnsTrue() {
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(List.of("manager"));
        Person person = new PersonBuilder().withRoles("Manager").build();
        assertTrue(predicate.test(person));
    }

    @Test
    void equals_sameObject_returnsTrue() {
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(List.of("Manager"));
        assertEquals(predicate, predicate);
    }

    @Test
    void equals_differentObjectSameKeywords_returnsTrue() {
        RoleContainsKeywordsPredicate predicate1 = new RoleContainsKeywordsPredicate(List.of("Manager"));
        RoleContainsKeywordsPredicate predicate2 = new RoleContainsKeywordsPredicate(List.of("Manager"));
        assertEquals(predicate1, predicate2);
    }

    @Test
    void equals_differentObjectDifferentKeywords_returnsFalse() {
        RoleContainsKeywordsPredicate predicate1 = new RoleContainsKeywordsPredicate(List.of("Manager"));
        RoleContainsKeywordsPredicate predicate2 = new RoleContainsKeywordsPredicate(List.of("Developer"));
        assertNotEquals(predicate1, predicate2);
    }

    @Test
    void equals_null_returnsFalse() {
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(List.of("Manager"));
        assertNotEquals(null, predicate);
    }

    @Test
    void equals_differentType_returnsFalse() {
        RoleContainsKeywordsPredicate predicate = new RoleContainsKeywordsPredicate(List.of("Manager"));
        assertNotEquals(1, predicate);
    }
}

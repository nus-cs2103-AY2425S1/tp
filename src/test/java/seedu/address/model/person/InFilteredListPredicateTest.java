package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class InFilteredListPredicateTest {

    @Test
    public void equals() {
        List<Person> firstPersonList = Collections.singletonList(new PersonBuilder().withName("first").build());
        List<Person> secondPersonList = Arrays.asList(new PersonBuilder().withName("first").build(),
                new PersonBuilder().withName("second").build());

        InFilteredListPredicate firstPredicate = new InFilteredListPredicate(firstPersonList);
        InFilteredListPredicate secondPredicate = new InFilteredListPredicate(secondPersonList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        InFilteredListPredicate firstPredicateCopy = new InFilteredListPredicate(firstPersonList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_isInFilteredList_returnsTrue() {
        // One person
        InFilteredListPredicate predicate = new InFilteredListPredicate(Collections.singletonList(
                new PersonBuilder().withName("Alice").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Multiple people
        predicate = new InFilteredListPredicate(Arrays.asList(
                new PersonBuilder().withName("Alice").build(),
                new PersonBuilder().withName("Bob").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Bob").build()));
    }

    @Test
    public void test_isNotInFilteredList_returnsFalse() {
        // Zero people
        InFilteredListPredicate predicate = new InFilteredListPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // No matching person, different name
        predicate = new InFilteredListPredicate(Collections.singletonList(
                new PersonBuilder().withName("Alice").build()));
        assertFalse(predicate.test(new PersonBuilder().withName("Bob").build()));

        // No matching person, different module role
        predicate = new InFilteredListPredicate(Collections.singletonList(
                new PersonBuilder().withName("Alice").withModuleRoleMap(
                        new ModuleCode("CS2103T"), RoleType.TUTOR).build()));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withModuleRoleMap(
                new ModuleCode("CS2101"), RoleType.TUTOR).build()));
    }

    @Test
    public void toStringMethod() {
        List<Person> personList = Collections.singletonList(new PersonBuilder().withName("Alice").build());
        InFilteredListPredicate predicate = new InFilteredListPredicate(personList);

        String expected = InFilteredListPredicate.class.getCanonicalName()
                + "{currentFilteredList=" + personList + "}";
        assertEquals(expected, predicate.toString());
    }
}


package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

public class PersonMeetsCriteriaPredicateTest {

    private final Person person = new Person(
            new Name("John Doe"),
            new Phone("12345678"),
            new Email("john@example.com"),
            new Address("123 Street"),
            Notes.createEmpty(),
            new HashSet<>(Collections.singleton(new Tag("friend"))),
            new Income("low"),
            new Age("23")
    );

    @Test
    public void test_phoneCriteriaMatches_returnsTrue() {
        List<String> phoneCriteria = Arrays.asList("123");
        List<String> emailCriteria = Collections.emptyList();
        List<String> addressCriteria = Collections.emptyList();
        List<String> incomeCriteria = Collections.emptyList();
        List<String> ageCriteria = Collections.emptyList();
        List<String> tags = Collections.emptyList();

        PersonMeetsCriteriaPredicate predicate = new PersonMeetsCriteriaPredicate(
            phoneCriteria,
            emailCriteria,
            addressCriteria,
            incomeCriteria,
            ageCriteria,
            tags);

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_phoneCriteriaDoesNotMatch_returnsFalse() {
        List<String> phoneCriteria = Arrays.asList("999");
        List<String> emailCriteria = Collections.emptyList();
        List<String> addressCriteria = Collections.emptyList();
        List<String> incomeCriteria = Collections.emptyList();
        List<String> ageCriteria = Collections.emptyList();
        List<String> tags = Collections.emptyList();

        PersonMeetsCriteriaPredicate predicate = new PersonMeetsCriteriaPredicate(
            phoneCriteria,
            emailCriteria,
            addressCriteria,
            incomeCriteria,
            ageCriteria,
            tags);

        assertFalse(predicate.test(person));
    }

    @Test
    public void test_emailCriteriaMatches_returnsTrue() {
        List<String> phoneCriteria = Collections.emptyList();
        List<String> emailCriteria = Arrays.asList("john@example.com");
        List<String> addressCriteria = Collections.emptyList();
        List<String> incomeCriteria = Collections.emptyList();
        List<String> ageCriteria = Collections.emptyList();
        List<String> tags = Collections.emptyList();

        PersonMeetsCriteriaPredicate predicate = new PersonMeetsCriteriaPredicate(
            phoneCriteria,
            emailCriteria,
            addressCriteria,
            incomeCriteria,
            ageCriteria,
            tags);

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_emailCriteriaDoesNotMatch_returnsFalse() {
        List<String> phoneCriteria = Collections.emptyList();
        List<String> emailCriteria = Arrays.asList("jane@example.com");
        List<String> addressCriteria = Collections.emptyList();
        List<String> incomeCriteria = Collections.emptyList();
        List<String> ageCriteria = Collections.emptyList();
        List<String> tags = Collections.emptyList();

        PersonMeetsCriteriaPredicate predicate = new PersonMeetsCriteriaPredicate(
            phoneCriteria,
            emailCriteria,
            addressCriteria,
            incomeCriteria,
            ageCriteria,
            tags);

        assertFalse(predicate.test(person));
    }

    @Test
    public void test_addressCriteriaMatches_returnsTrue() {
        List<String> phoneCriteria = Collections.emptyList();
        List<String> emailCriteria = Collections.emptyList();
        List<String> addressCriteria = Arrays.asList("Street");
        List<String> incomeCriteria = Collections.emptyList();
        List<String> ageCriteria = Collections.emptyList();
        List<String> tags = Collections.emptyList();

        PersonMeetsCriteriaPredicate predicate = new PersonMeetsCriteriaPredicate(
            phoneCriteria,
            emailCriteria,
            addressCriteria,
            incomeCriteria,
            ageCriteria,
            tags);

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_addressCriteriaDoesNotMatch_returnsFalse() {
        List<String> phoneCriteria = Collections.emptyList();
        List<String> emailCriteria = Collections.emptyList();
        List<String> addressCriteria = Arrays.asList("Avenue");
        List<String> incomeCriteria = Collections.emptyList();
        List<String> ageCriteria = Collections.emptyList();
        List<String> tags = Collections.emptyList();

        PersonMeetsCriteriaPredicate predicate = new PersonMeetsCriteriaPredicate(
            phoneCriteria,
            emailCriteria,
            addressCriteria,
            incomeCriteria,
            ageCriteria,
            tags);

        assertFalse(predicate.test(person));
    }

    @Test
    public void test_incomeCriteriaMatches_returnsTrue() {
        List<String> phoneCriteria = Collections.emptyList();
        List<String> emailCriteria = Collections.emptyList();
        List<String> addressCriteria = Collections.emptyList();
        List<String> incomeCriteria = Arrays.asList("low");
        List<String> ageCriteria = Collections.emptyList();
        List<String> tags = Collections.emptyList();

        PersonMeetsCriteriaPredicate predicate = new PersonMeetsCriteriaPredicate(
            phoneCriteria,
            emailCriteria,
            addressCriteria,
            incomeCriteria,
            ageCriteria,
            tags);

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_incomeCriteriaDoesNotMatch_returnsFalse() {
        List<String> phoneCriteria = Collections.emptyList();
        List<String> emailCriteria = Collections.emptyList();
        List<String> addressCriteria = Collections.emptyList();
        List<String> incomeCriteria = Arrays.asList("high");
        List<String> ageCriteria = Collections.emptyList();
        List<String> tags = Collections.emptyList();

        PersonMeetsCriteriaPredicate predicate = new PersonMeetsCriteriaPredicate(
            phoneCriteria,
            emailCriteria,
            addressCriteria,
            incomeCriteria,
            ageCriteria,
            tags);

        assertFalse(predicate.test(person));
    }

    @Test
    public void test_ageCriteriaMatches_returnsTrue() {
        List<String> phoneCriteria = Collections.emptyList();
        List<String> emailCriteria = Collections.emptyList();
        List<String> addressCriteria = Collections.emptyList();
        List<String> incomeCriteria = Collections.emptyList();
        List<String> ageCriteria = Arrays.asList(">20");
        List<String> tags = Collections.emptyList();

        PersonMeetsCriteriaPredicate predicate = new PersonMeetsCriteriaPredicate(
            phoneCriteria,
            emailCriteria,
            addressCriteria,
            incomeCriteria,
            ageCriteria,
            tags);

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_ageCriteriaDoesNotMatch_returnsFalse() {
        List<String> phoneCriteria = Collections.emptyList();
        List<String> emailCriteria = Collections.emptyList();
        List<String> addressCriteria = Collections.emptyList();
        List<String> incomeCriteria = Collections.emptyList();
        List<String> ageCriteria = Arrays.asList("<20");
        List<String> tags = Collections.emptyList();

        PersonMeetsCriteriaPredicate predicate = new PersonMeetsCriteriaPredicate(
            phoneCriteria,
            emailCriteria,
            addressCriteria,
            incomeCriteria,
            ageCriteria,
            tags);

        assertFalse(predicate.test(person));
    }

    @Test
    public void test_tagsMatch_returnsTrue() {
        List<String> phoneCriteria = Collections.emptyList();
        List<String> emailCriteria = Collections.emptyList();
        List<String> addressCriteria = Collections.emptyList();
        List<String> incomeCriteria = Collections.emptyList();
        List<String> ageCriteria = Collections.emptyList();
        List<String> tags = Arrays.asList("friend");

        PersonMeetsCriteriaPredicate predicate = new PersonMeetsCriteriaPredicate(
            phoneCriteria,
            emailCriteria,
            addressCriteria,
            incomeCriteria,
            ageCriteria,
            tags);

        assertTrue(predicate.test(person));
    }

    @Test
    public void test_tagsDoNotMatch_returnsFalse() {
        List<String> phoneCriteria = Collections.emptyList();
        List<String> emailCriteria = Collections.emptyList();
        List<String> addressCriteria = Collections.emptyList();
        List<String> incomeCriteria = Collections.emptyList();
        List<String> ageCriteria = Collections.emptyList();
        List<String> tags = Arrays.asList("colleague");

        PersonMeetsCriteriaPredicate predicate = new PersonMeetsCriteriaPredicate(
            phoneCriteria,
            emailCriteria,
            addressCriteria,
            incomeCriteria,
            ageCriteria,
            tags);

        assertFalse(predicate.test(person));
    }

    @Test
    public void equals() {
        List<String> phoneCriteria = Arrays.asList("123");
        List<String> emailCriteria = Arrays.asList("john@example.com");
        List<String> addressCriteria = Arrays.asList("123 Street");
        List<String> incomeCriteria = Arrays.asList("low");
        List<String> ageCriteria = Arrays.asList(">20");
        List<String> tags = Arrays.asList("friend");

        PersonMeetsCriteriaPredicate predicate1 = new PersonMeetsCriteriaPredicate(
            phoneCriteria,
            emailCriteria,
            addressCriteria,
            incomeCriteria,
            ageCriteria,
            tags);

        PersonMeetsCriteriaPredicate predicate2 = new PersonMeetsCriteriaPredicate(
            phoneCriteria,
            emailCriteria,
            addressCriteria,
            incomeCriteria,
            ageCriteria,
            tags);

        NameContainsKeywordsPredicate predicate3 = new NameContainsKeywordsPredicate(Arrays.asList("Alice"));

        assertFalse(predicate1.equals(predicate3));

        assertEquals(predicate1, predicate2);
    }

    @Test
    public void toStringTest() {
        List<String> phoneCriteria = Arrays.asList("123");
        List<String> emailCriteria = Arrays.asList("example.com");
        List<String> addressCriteria = Arrays.asList("Street");
        List<String> incomeCriteria = Arrays.asList("low");
        List<String> ageCriteria = Arrays.asList(">20");
        List<String> tags = Arrays.asList("friend");

        PersonMeetsCriteriaPredicate predicate = new PersonMeetsCriteriaPredicate(
            phoneCriteria,
            emailCriteria,
            addressCriteria,
            incomeCriteria,
            ageCriteria,
            tags);
        String expectedString = PersonMeetsCriteriaPredicate.class.getCanonicalName()
            + "{phone=[123], email=[example.com], address=[Street], income=[low], age=[>20], tags=[friend]}";

        assertEquals(expectedString, predicate.toString());
    }
}

package seedu.address.logic.parser.criteria;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
public class TagSearchCriteriaTest {

    @Test
    public void test_matchesTag() {
        TagSearchCriteria criteria = new TagSearchCriteria(List.of("friend"));

        Person alice = new PersonBuilder().withName("Alice").withTags("friend").build();
        Person bob = new PersonBuilder().withName("Bob").withTags("colleague").build();

        assertTrue(criteria.test(alice));
        assertFalse(criteria.test(bob));
    }

    @Test
    public void test_noMatchForNonMatchingTag() {
        TagSearchCriteria criteria = new TagSearchCriteria(List.of("family"));
        Person alice = new PersonBuilder().withName("Alice").withTags("friend").build();
        assertFalse(criteria.test(alice));
    }
}


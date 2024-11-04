package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class ArgumentPredicateToFailTest {
    @Test
    public void equals() {
        ArgumentPredicateToFail firstArgumentPredicateToFail = new ArgumentPredicateToFail(mapPerson(ALICE));
        ArgumentPredicateToFail secondArgumentPredicateToFail = new ArgumentPredicateToFail(mapPerson(BOB));

        // same object -> returns true
        assertTrue(firstArgumentPredicateToFail.equals(firstArgumentPredicateToFail));

        // same values -> returns true
        ArgumentPredicateToFail firstPredicateCopy = new ArgumentPredicateToFail(mapPerson(ALICE));
        assertTrue(firstArgumentPredicateToFail.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstArgumentPredicateToFail.equals(1));

        // null -> returns false
        assertFalse(firstArgumentPredicateToFail.equals(null));

        // different person -> returns false
        assertFalse(firstArgumentPredicateToFail.equals(secondArgumentPredicateToFail));
    }

    @Test
    public void test_personContainsKeywords_returnsFalse() {
        // Only name parameter
        Map<String, Object> testNameParameter = Map.of(
                "name", new Name("Bob"),
                "tags", new HashSet<>()
        );
        ArgumentPredicateToFail namePredicate = new ArgumentPredicateToFail(testNameParameter);
        assertFalse(namePredicate.test(new PersonBuilder().withName(VALID_NAME_BOB).build()));

        // Only phone parameter
        Map<String, Object> testPhoneParameter = Map.of(
                "phone", new Phone("9876"),
                "tags", new HashSet<>()
        );
        ArgumentPredicateToFail phonePredicate = new ArgumentPredicateToFail(testPhoneParameter);
        assertFalse(phonePredicate.test(new PersonBuilder().withPhone("9876").build()));

        // Only email parameter
        Map<String, Object> testEmailParameter = Map.of(
                "email", new Email("test@gmail.com"),
                "tags", new HashSet<>()
        );
        ArgumentPredicateToFail emailPredicate = new ArgumentPredicateToFail(testEmailParameter);
        assertFalse(emailPredicate.test(new PersonBuilder().withEmail("test@gmail.com").build()));

        // Only address parameter
        Map<String, Object> testAddressParameter = Map.of(
                "address", new Address("Blk 17, Clown street"),
                "tags", new HashSet<>()
        );
        ArgumentPredicateToFail addressPredicate = new ArgumentPredicateToFail(testAddressParameter);
        assertFalse(addressPredicate.test(new PersonBuilder().withAddress("Blk 17, Clown street").build()));

        // Only project status parameter
        Map<String, Object> testProjectStatusParameter = Map.of(
                "project status", new ProjectStatus("completed"),
                "tags", new HashSet<>()
        );
        ArgumentPredicateToFail projectStatusPredicate = new ArgumentPredicateToFail(testProjectStatusParameter);
        assertFalse(projectStatusPredicate.test(new PersonBuilder().withProjectStatus("completed").build()));

        // Only payment status parameter
        Map<String, Object> testPaymentStatusParameter = Map.of(
                "payment status", new PaymentStatus("paid"),
                "tags", new HashSet<>());
        ArgumentPredicateToFail paymentStatusPredicate = new ArgumentPredicateToFail(testPaymentStatusParameter);
        assertFalse(paymentStatusPredicate.test(new PersonBuilder().withPaymentStatus("paid").build()));

        // Only client status parameter
        Map<String, Object> testClientStatusParameter = Map.of(
                "client status", new ClientStatus("active"),
                "tags", new HashSet<>()
        );
        ArgumentPredicateToFail clientStatusPredicate = new ArgumentPredicateToFail(testClientStatusParameter);
        assertFalse(clientStatusPredicate.test(new PersonBuilder().withClientStatus("active").build()));

        // Only deadline parameter
        Map<String, Object> testDeadlineParameter = Map.of(
                "deadline", new Deadline("20-10-2002"),
                "tags", new HashSet<>()
        );
        ArgumentPredicateToFail deadlinePredicate = new ArgumentPredicateToFail(testDeadlineParameter);
        assertFalse(deadlinePredicate.test(new PersonBuilder().withDeadline("20-10-2002").build()));

        // Only tag parameter
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friend"));
        Map<String, Object> testTagParameter = Map.of("tags", tags);
        ArgumentPredicateToFail tagPredicate = new ArgumentPredicateToFail(testTagParameter);
        assertFalse(tagPredicate.test(new PersonBuilder().withTags(new String[] {"friend", "family"}).build()));

        // Multiple parameters
        Map<String, Object> testMultipleParameters = Map.of(
                "name", new Name("Luk"),
                "tags", tags
        );
        ArgumentPredicateToFail multipleParametersPredicate = new ArgumentPredicateToFail(testMultipleParameters);
        assertFalse(multipleParametersPredicate.test(new PersonBuilder().withName("Luke Chambers")
                .withTags(new String[] {"friend", "family"}).build()));
    }

    @Test
    public void test_personContainWrongKeywords_returnsTrue() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friend"));
        // Match name and tags, but does not match phone
        Map<String, Object> testMultipleParametersFail = Map.of(
                "name", new Name("Luk"),
                "phone", new Phone("9999"),
                "tags", tags
        );
        ArgumentPredicateToFail multipleParametersFailPredicate =
                new ArgumentPredicateToFail(testMultipleParametersFail);
        assertTrue(multipleParametersFailPredicate.test(new PersonBuilder().withName("Luke").withPhone("12345")
                .withTags(new String[] {"friend", "family"}).build()));
    }

    @Test
    public void toStringMethod() {
        ArgumentPredicateToFail predicate = new ArgumentPredicateToFail(mapPerson(BOB));
        String expected = ArgumentPredicateToFail.class.getCanonicalName()
                + "{parameters to fail=" + mapPerson(BOB) + "}";
        assertEquals(expected, predicate.toString());
    }

    private Map<String, Object> mapPerson(Person person) {
        return Map.of(
                "name", person.getName(),
                "phone", person.getPhone(),
                "email", person.getEmail(),
                "address", person.getAddress(),
                "project status", person.getProjectStatus(),
                "payment status", person.getPaymentStatus(),
                "client status", person.getClientStatus(),
                "deadline", person.getDeadline(),
                "tags", person.getTags()
        );
    }
}

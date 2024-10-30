package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArgumentPredicateTest {
    @Test
    public void equals() {
        ArgumentPredicate firstArgumentPredicate = new ArgumentPredicate(mapPerson(ALICE));
        ArgumentPredicate secondArgumentPredicate = new ArgumentPredicate(mapPerson(BOB));

        // same object -> returns true
        assertTrue(firstArgumentPredicate.equals(firstArgumentPredicate));

        // same values -> returns true
        ArgumentPredicate firstPredicateCopy = new ArgumentPredicate(mapPerson(ALICE));
        assertTrue(firstArgumentPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstArgumentPredicate.equals(1));

        // null -> returns false
        assertFalse(firstArgumentPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstArgumentPredicate.equals(secondArgumentPredicate));
    }

    @Test
    public void test_personContainsKeywords_returnsTrue() {
        // Only name parameter
        Map<String, Object> testNameParameter = Map.of(
                "name", new Name("Bob"),
                "tags", new HashSet<>()
        );
        ArgumentPredicate namePredicate = new ArgumentPredicate(testNameParameter);
        assertTrue(namePredicate.test(new PersonBuilder().withName(VALID_NAME_BOB).build()));

        // Only phone parameter
        Map<String, Object> testPhoneParameter = Map.of(
                "phone", new Phone("9876"),
                "tags", new HashSet<>()
        );
        ArgumentPredicate phonePredicate = new ArgumentPredicate(testPhoneParameter);
        assertTrue(phonePredicate.test(new PersonBuilder().withPhone("9876").build()));

        // Only email parameter
        Map<String, Object> testEmailParameter = Map.of(
                "email", new Email("test@gmail.com"),
                "tags", new HashSet<>()
        );
        ArgumentPredicate emailPredicate = new ArgumentPredicate(testEmailParameter);
        assertTrue(emailPredicate.test(new PersonBuilder().withEmail("test@gmail.com").build()));

        // Only address parameter
        Map<String, Object> testAddressParameter = Map.of(
                "address", new Address("Blk 17, Clown street"),
                "tags", new HashSet<>()
        );
        ArgumentPredicate addressPredicate = new ArgumentPredicate(testAddressParameter);
        assertTrue(addressPredicate.test(new PersonBuilder().withAddress("Blk 17, Clown street").build()));

        // Only project status parameter
        Map<String, Object> testProjectStatusParameter = Map.of(
                "project status", new ProjectStatus("completed"),
                "tags", new HashSet<>()
        );
        ArgumentPredicate projectStatusPredicate = new ArgumentPredicate(testProjectStatusParameter);
        assertTrue(projectStatusPredicate.test(new PersonBuilder().withProjectStatus("completed").build()));

        // Only payment status parameter
        Map<String, Object> testPaymentStatusParameter = Map.of(
                "payment status", new PaymentStatus("paid"),
                "tags", new HashSet<>());
        ArgumentPredicate paymentStatusPredicate = new ArgumentPredicate(testPaymentStatusParameter);
        assertTrue(paymentStatusPredicate.test(new PersonBuilder().withPaymentStatus("paid").build()));

        // Only client status parameter
        Map<String, Object> testClientStatusParameter = Map.of(
                "client status", new ClientStatus("active"),
                "tags", new HashSet<>()
        );
        ArgumentPredicate clientStatusPredicate = new ArgumentPredicate(testClientStatusParameter);
        assertTrue(clientStatusPredicate.test(new PersonBuilder().withClientStatus("active").build()));

        // Only deadline parameter
        Map<String, Object> testDeadlineParameter = Map.of(
                "deadline", new Deadline("20-10-2002"),
                "tags", new HashSet<>()
        );
        ArgumentPredicate deadlinePredicate = new ArgumentPredicate(testDeadlineParameter);
        assertTrue(deadlinePredicate.test(new PersonBuilder().withDeadline("20-10-2002").build()));

        // Only tag parameter
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friend"));
        Map<String, Object> testTagParameter = Map.of("tags", tags);
        ArgumentPredicate tagPredicate = new ArgumentPredicate(testTagParameter);
        assertTrue(tagPredicate.test(new PersonBuilder().withTags(new String[] {"friend", "family"}).build()));

        // Multiple parameters
        Map<String, Object> testMultipleParameters = Map.of(
                "name", new Name("Luk"),
                "tags", tags
        );
        ArgumentPredicate multipleParametersPredicate = new ArgumentPredicate(testMultipleParameters);
        assertTrue(multipleParametersPredicate.test(new PersonBuilder().withName("Luke Chambers")
                .withTags(new String[] {"friend", "family"}).build()));
    }

    @Test
    public void test_personContainWrongKeywords_returnsFalse() {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("friend"));
        // Match name and tags, but does not match phone
        Map<String, Object> testMultipleParametersFail = Map.of(
                "name", new Name("Luk"),
                "phone", new Phone("9999"),
                "tags", tags
        );
        ArgumentPredicate multipleParametersFailPredicate = new ArgumentPredicate(testMultipleParametersFail);
        assertFalse(multipleParametersFailPredicate.test(new PersonBuilder().withName("Luke").withPhone("12345")
                .withTags(new String[] {"friend", "family"}).build()));
    }

    @Test
    public void toStringMethod() {
        ArgumentPredicate predicate = new ArgumentPredicate(mapPerson(BOB));
        String expected = ArgumentPredicate.class.getCanonicalName() + "{parameters=" + mapPerson(BOB) + "}";
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

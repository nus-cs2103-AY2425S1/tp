package seedu.address.model.person;

import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import seedu.address.logic.commands.FilterCommand.FilterPersonDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class FilterPredicateTest {

    @Test
    public void equals() {
        FilterPersonDescriptor firstDescriptor = new FilterPersonDescriptor();
        firstDescriptor.setName(new Name("Alice"));
        FilterPredicate firstPredicate = preparePredicate(firstDescriptor);

        FilterPersonDescriptor secondDescriptor = new FilterPersonDescriptor();
        secondDescriptor.setName(new Name("Alice"));
        secondDescriptor.setGender(new Gender("female"));
        FilterPredicate secondPredicate = preparePredicate(secondDescriptor);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FilterPredicate firstPredicateCopy = new FilterPredicate(firstDescriptor);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_oneSpecifiedCondition_returnTrue() {

        FilterPersonDescriptor descriptor = new FilterPersonDescriptor();
        descriptor.setName(new Name("Alice"));
        FilterPredicate predicate = preparePredicate(descriptor);
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        descriptor = new FilterPersonDescriptor();
        descriptor.setGender(new Gender("male"));
        predicate = preparePredicate(descriptor);
        assertTrue(predicate.test(new PersonBuilder().withGender("male").build()));

        descriptor = new FilterPersonDescriptor();
        descriptor.setPhone(new Phone("1234"));
        predicate = preparePredicate(descriptor);
        assertTrue(predicate.test(new PersonBuilder().withPhone("1234").build()));

        descriptor = new FilterPersonDescriptor();
        descriptor.setModules(Set.of(new Module("CS1101S")));
        predicate = preparePredicate(descriptor);
        assertTrue(predicate.test(new PersonBuilder().withModules("CS1101S").build()));

        descriptor = new FilterPersonDescriptor();
        descriptor.setTags(Set.of(new Tag("student")));
        predicate = preparePredicate(descriptor);
        assertTrue(predicate.test(new PersonBuilder().withTags("student").build()));
    }

    @Test
    public void test_moreThanOneConditionSatisfied_returnTrue() {
        FilterPersonDescriptor descriptor = new FilterPersonDescriptor();
        descriptor.setName(new Name("Alice"));
        descriptor.setGender(new Gender("female"));
        FilterPredicate predicate = preparePredicate(descriptor);
        PersonBuilder filterPerson = new PersonBuilder().withName("Alice Bob").withGender("female");
        assertTrue(predicate.test(filterPerson.build()));

        descriptor.setTags(Set.of(new Tag("student")));
        predicate = preparePredicate(descriptor);
        filterPerson.withTags("student");
        assertTrue(predicate.test(filterPerson.build()));
    }

    @Test
    public void test_noConditionSatisfied_returnFalse() {
        FilterPersonDescriptor descriptor = new FilterPersonDescriptor();
        descriptor.setName(new Name("Alice"));
        FilterPredicate predicate = preparePredicate(descriptor);
        assertFalse(predicate.test(new PersonBuilder().withName("Gan").build()));

        descriptor = new FilterPersonDescriptor();
        descriptor.setGender(new Gender("male"));
        predicate = preparePredicate(descriptor);
        assertFalse(predicate.test(new PersonBuilder().withGender("female").build()));

        descriptor = new FilterPersonDescriptor();
        descriptor.setPhone(new Phone("1234"));
        predicate = preparePredicate(descriptor);
        assertFalse(predicate.test(new PersonBuilder().withPhone("3412").build()));

        descriptor = new FilterPersonDescriptor();
        descriptor.setModules(Set.of(new Module("CS1101S")));
        predicate = preparePredicate(descriptor);
        assertFalse(predicate.test(new PersonBuilder().withModules("MA1511").build()));

        descriptor = new FilterPersonDescriptor();
        descriptor.setTags(Set.of(new Tag("student")));
        predicate = preparePredicate(descriptor);
        assertFalse(predicate.test(new PersonBuilder().withTags("colleague").build()));
    }

    @Test
    public void toStringMethod() {
        FilterPersonDescriptor descriptor = new FilterPersonDescriptor();
        descriptor.setName(new Name("Alice"));
        FilterPredicate predicate = preparePredicate(descriptor);

        String expected = FilterPredicate.class.getCanonicalName()
                + "{conditions=seedu.address.logic.commands.FilterCommand.FilterPersonDescriptor"
                + "{name=Alice, phone=null, gender=null, modules=null, tags=null}}";
        assertEquals(expected, predicate.toString());
    }

    /**
     * Parses {@code FilterPersonDescription} into a {@code FilterPredicate}.
     */
    private FilterPredicate preparePredicate(FilterPersonDescriptor descriptor) {
        return new FilterPredicate(descriptor);
    }
}

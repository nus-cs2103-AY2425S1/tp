package seedu.address.logic.parser.criteria;

import seedu.address.model.person.Person;

/**
 * Represents a search criteria for a {@code Person}.
 * A search criteria is defined by a method {@code test(Person person)} that
 * takes in a {@code Person} and returns a boolean indicating whether the
 * {@code Person} satisfies the search criteria.
 */
@FunctionalInterface
public interface SearchCriteria {
    boolean test(Person person);
}

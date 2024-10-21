package seedu.address.model.person.predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents the collection of all {@code Predicate<Person>} criteria to test.
 */
public class PredicateGroup implements Predicate<Person> {
    private final Logger logger = LogsCenter.getLogger(getClass());

    private List<Predicate<Person>> predicates;

    public PredicateGroup() {
        this.predicates = new ArrayList<>();
    }

    public void add(Predicate<Person> predicate) {
        this.predicates.add(predicate);
    }

    /**
     * Checks if PredicateGroup is initialised with any predicates.
     *
     * @return true if at least one predicate exist
     */
    public boolean isAnyPredicateAdded() {
        logger.info(String.format("PredicateGroup: Initialised with %d Predicates.", predicates.size()));
        return predicates.size() > 0;
    }

    @Override
    public boolean test(Person person) {
        return predicates.stream()
                .allMatch(predicate -> predicate.test(person));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PredicateGroup)) {
            return false;
        }

        PredicateGroup otherPredicateGroup = (PredicateGroup) other;
        return predicates.stream()
                .allMatch(predicate -> otherPredicateGroup.predicates.stream()
                        .anyMatch(otherPredicate -> predicate.equals(otherPredicate)))
                && otherPredicateGroup.predicates.stream()
                        .allMatch(otherPredicate -> this.predicates.stream()
                                .anyMatch(predicate -> otherPredicate.equals(predicate)));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("predicates", predicates).toString();
    }
}

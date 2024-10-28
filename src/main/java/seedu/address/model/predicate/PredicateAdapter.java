package seedu.address.model.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;

/**
 * PredicateAdapter adapts a list of {@code Predicate<Participation>} to a {@code Predicate<Person>}.
 * It allows filtering {@code Person} objects based on participation-related conditions.
 *
 * <p>This adapter iterates over each {@code Predicate<Participation>} in {@code participationPredicates}
 * and applies it to the {@code Participation} records associated with each {@code Person}.
 * A {@code Person} passes the filter if all predicates match at least one of their {@code Participation} records.
 */
public class PredicateAdapter implements Predicate<Person> {
    private final List<Predicate<Participation>> participationPredicates;
    private final List<Participation> allParticipations;

    /**
     * Constructs an adapter that allows filtering Person objects based on a Participation predicate.
     *
     * @param participationPredicates A list of {@code Predicate<Participation>} that define the filtering criteria.
     * @param allParticipations The list of all Participation records to check.
     */
    public PredicateAdapter(List<Predicate<Participation>> participationPredicates,
                            List<Participation> allParticipations) {
        this.participationPredicates = participationPredicates;
        this.allParticipations = allParticipations;
    }

    @Override
    public boolean test(Person person) {
        return participationPredicates.stream().allMatch(predicate ->
                allParticipations.stream()
                        .filter(participation -> participation.getStudent().equals(person))
                        .anyMatch(predicate)
        );
    }
}
